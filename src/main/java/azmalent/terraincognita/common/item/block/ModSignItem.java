package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.network.S2CEditSignMessage;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class ModSignItem extends StandingAndWallBlockItem {
    public ModSignItem(ModWoodType woodType) {
        super(woodType.SIGN.getBlock(), woodType.WALL_SIGN.getBlock(), new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS));
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        boolean flag = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if (!flag && player instanceof ServerPlayer) {
            NetworkHandler.sendToPlayer((ServerPlayer) player, new S2CEditSignMessage(pos));
        }

        return flag;
    }
}
