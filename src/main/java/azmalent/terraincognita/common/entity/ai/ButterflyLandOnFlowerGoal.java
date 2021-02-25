package azmalent.terraincognita.common.entity.ai;

import azmalent.terraincognita.common.entity.insect.ButterflyEntity;
import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class ButterflySitOnFlowerGoal extends MoveToBlockGoal {
    private final ButterflyEntity butterfly;

    private int landedTicks;

    public ButterflySitOnFlowerGoal(ButterflyEntity butterfly, double speed, int searchRadius) {
        super(butterfly, speed, searchRadius);
        this.butterfly = butterfly;
    }

    @Override
    public boolean shouldExecute() {
        return (butterfly.isTired() || butterfly.world.isNightTime()) && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (butterfly.isTired() || butterfly.world.isNightTime()) && super.shouldContinueExecuting();
    }

    @Override
    protected boolean shouldMoveTo(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isIn(BlockTags.TALL_FLOWERS) && state.getBlock() instanceof TallFlowerBlock) {
            return state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER;
        }

        return state.isIn(BlockTags.SMALL_FLOWERS);
    }

    @Override
    public void startExecuting() {
        super.startExecuting();

        landedTicks = 0;
    }

    @Override
    public void resetTask() {
        super.resetTask();

        landedTicks = 0;
        butterfly.setNotLanded();
        butterfly.getNavigator().clearPath();
    }

    @Override
    public double getTargetDistanceSq() {
        return 1.5;
    }

    @Override
    public void tick() {
        if (!butterfly.isLanded()) {
            super.tick();
            if (getIsAboveDestination()) land();
        } else if (butterfly.isTired()){
            landedTicks++;
            if (landedTicks > 300 && butterfly.world.rand.nextInt(200) == 0) {
                butterfly.setTired(false);
            }
        }
    }

    private void land() {
        if (shouldMoveTo(butterfly.world, destinationBlock)) {
            butterfly.setLanded(true);

            BlockState state = butterfly.world.getBlockState(destinationBlock);
            AxisAlignedBB aabb = state.getShape(butterfly.world, destinationBlock).getBoundingBox();
            double x = destinationBlock.getX() + (aabb.maxX - aabb.minX) / 2;
            double y = destinationBlock.getY() + aabb.maxY * 0.8;
            double z = destinationBlock.getZ() + (aabb.maxZ - aabb.minZ) / 2;
            butterfly.setPositionAndUpdate(x, y, z);
            butterfly.setMotion(Vector3d.ZERO);
        }
    }
}
