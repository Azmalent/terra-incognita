package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.network.message.s2c.S2CEditSignMessage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class ModSignItem extends WallOrFloorItem {
    public ModSignItem(ModWoodType woodType) {
        super(woodType.SIGN.getBlock(), woodType.WALL_SIGN.getBlock(), new Item.Properties().maxStackSize(16).group(ItemGroup.DECORATIONS));
    }

    @Override
    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        boolean flag = super.onBlockPlaced(pos, worldIn, player, stack, state);
        if (!flag && player instanceof ServerPlayerEntity) {
            NetworkHandler.sendToPlayer((ServerPlayerEntity) player, new S2CEditSignMessage(pos));
        }

        return flag;
    }
}
