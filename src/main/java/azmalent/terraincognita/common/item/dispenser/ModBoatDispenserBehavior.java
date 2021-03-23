package azmalent.terraincognita.common.item.dispenser;

import azmalent.terraincognita.common.entity.ModBoatEntity;
import azmalent.terraincognita.common.item.ModBoatItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public final class ModBoatDispenserBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

    @Nonnull
    @SuppressWarnings("deprecation")
    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack stack) {
        Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
        World world = iBlockSource.getWorld();
        double x = iBlockSource.getX() + (double) ((float) direction.getXOffset() * 1.125f);
        double y = iBlockSource.getY() + (double) ((float) direction.getYOffset() * 1.125f);
        double z = iBlockSource.getZ() + (double) ((float) direction.getZOffset() * 1.125f);
        BlockPos pos = iBlockSource.getBlockPos().offset(direction);
        double adjustY;
        if (world.getFluidState(pos).isTagged(FluidTags.WATER)) {
            adjustY = 1d;
        } else {
            if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.down()).isTagged(FluidTags.WATER)) {
                return this.defaultDispenseItemBehavior.dispense(iBlockSource, stack);
            }
            adjustY = 0d;
        }

        ModBoatItem item = (ModBoatItem) stack.getItem();
        ModBoatEntity boat = new ModBoatEntity(world, x, y + adjustY, z);
        boat.setWoodType(item.woodType);
        boat.rotationYaw = direction.getHorizontalAngle();
        world.addEntity(boat);
        stack.shrink(1);
        return stack;
    }

    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playEvent(1000, iBlockSource.getBlockPos(), 0);
    }
}
