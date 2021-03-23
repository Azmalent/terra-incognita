package azmalent.terraincognita.common.item.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public final class CaltropsDispenserBehavior extends DefaultDispenseItemBehavior {
    @Nonnull
    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().get(DispenserBlock.FACING);
        IPosition pos = DispenserBlock.getDispensePosition(source);
        World world = source.getWorld();

        double d0 = pos.getX();
        double d1 = pos.getY();
        double d2 = pos.getZ();
        if (direction.getAxis() == Direction.Axis.Y) {
            d1 = d1 - 0.125D;
        } else {
            d1 = d1 - 0.15625D;
        }

        ItemEntity entity = new ItemEntity(world, d0, d1, d2, stack.split(1));
        double d3 = world.rand.nextDouble() * 0.1D + 0.2D;
        entity.setMotion(world.rand.nextGaussian() * (double)0.0075F * (double) 6 + (double) direction.getXOffset() * d3, world.rand.nextGaussian() * (double)0.0075F * (double) 6 + (double)0.2F, world.rand.nextGaussian() * (double)0.0075F * (double) 6 + (double) direction.getZOffset() * d3);
        entity.getPersistentData().putBoolean("dispensed", true);
        world.addEntity(entity);

        return stack;
    }

}
