package azmalent.terraincognita.common.item.dispenser;

import azmalent.terraincognita.common.entity.ModBoatEntity;
import azmalent.terraincognita.common.item.ModBoatItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public final class ModBoatDispenserBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

    @Nonnull
    @SuppressWarnings("deprecation")
    public ItemStack execute(BlockSource iBlockSource, @NotNull ItemStack stack) {
        Direction direction = iBlockSource.getBlockState().getValue(DispenserBlock.FACING);
        Level world = iBlockSource.getLevel();
        double x = iBlockSource.x() + (double) ((float) direction.getStepX() * 1.125f);
        double y = iBlockSource.y() + (double) ((float) direction.getStepY() * 1.125f);
        double z = iBlockSource.z() + (double) ((float) direction.getStepZ() * 1.125f);
        BlockPos pos = iBlockSource.getPos().relative(direction);
        double adjustY;
        if (world.getFluidState(pos).is(FluidTags.WATER)) {
            adjustY = 1d;
        } else {
            if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.below()).is(FluidTags.WATER)) {
                return this.defaultDispenseItemBehavior.dispense(iBlockSource, stack);
            }
            adjustY = 0d;
        }

        ModBoatItem item = (ModBoatItem) stack.getItem();
        ModBoatEntity boat = new ModBoatEntity(world, x, y + adjustY, z);
        boat.setWoodType(item.woodType);
        boat.yRot = direction.toYRot();
        world.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    protected void playSound(BlockSource iBlockSource) {
        iBlockSource.getLevel().levelEvent(1000, iBlockSource.getPos(), 0);
    }
}
