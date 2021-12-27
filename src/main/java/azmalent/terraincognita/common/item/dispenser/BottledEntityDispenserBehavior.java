package azmalent.terraincognita.common.item.dispenser;

import azmalent.terraincognita.common.item.BottledEntityItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

public final class BottledEntityDispenserBehavior extends DefaultDispenseItemBehavior {
    @Nonnull
    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack bottle) {
        Item item = bottle.getItem();
        if (!(item instanceof BottledEntityItem)) {
            return bottle;
        }

        ServerWorld world = source.getWorld();
        Direction facing = source.getBlockState().get(DispenserBlock.FACING);
        BlockPos spawnPos = source.getBlockPos().offset(facing);

        Entity entity = ((BottledEntityItem<?>) item).type.get().spawn(world, bottle, null, spawnPos, SpawnReason.DISPENSER, facing != Direction.UP, false);
        if (entity != null) {
            BottledEntityItem.initReleasedEntity((LivingEntity) entity, bottle);
        }

        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
