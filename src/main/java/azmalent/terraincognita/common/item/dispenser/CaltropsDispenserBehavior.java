package azmalent.terraincognita.common.item.dispenser;

import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public final class CaltropsDispenserBehavior extends DefaultDispenseItemBehavior {
    @Nonnull
    @Override
    protected ItemStack execute(BlockSource source, @NotNull ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        Position pos = DispenserBlock.getDispensePosition(source);
        Level world = source.getLevel();

        double d0 = pos.x();
        double d1 = pos.y();
        double d2 = pos.z();
        if (direction.getAxis() == Direction.Axis.Y) {
            d1 = d1 - 0.125D;
        } else {
            d1 = d1 - 0.15625D;
        }

        ItemEntity entity = new ItemEntity(world, d0, d1, d2, stack.split(1));
        double d3 = world.random.nextDouble() * 0.1D + 0.2D;
        entity.setDeltaMovement(world.random.nextGaussian() * (double)0.0075F * (double) 6 + (double) direction.getStepX() * d3, world.random.nextGaussian() * (double)0.0075F * (double) 6 + (double)0.2F, world.random.nextGaussian() * (double)0.0075F * (double) 6 + (double) direction.getStepZ() * d3);
        entity.getPersistentData().putBoolean("dispensed", true);
        world.addFreshEntity(entity);

        return stack;
    }

}
