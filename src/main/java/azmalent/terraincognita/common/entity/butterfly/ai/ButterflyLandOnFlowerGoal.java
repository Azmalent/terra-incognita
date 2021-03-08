package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class ButterflyLandOnFlowerGoal extends MoveToBlockGoal {
    private static final double RADIUS = 1.25;
    private static final EntityPredicate BUTTERFLY_PREDICATE = new EntityPredicate().setDistance(RADIUS).allowFriendlyFire().allowInvulnerable();;

    private final ButterflyEntity butterfly;

    public ButterflyLandOnFlowerGoal(ButterflyEntity butterfly, double speed, int searchRadius) {
        super(butterfly, speed, searchRadius);
        this.butterfly = butterfly;
    }

    @Override
    public boolean shouldExecute() {
        return !butterfly.isLanded() && (butterfly.isTired() || butterfly.world.isNightTime()) && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !butterfly.isLanded() && (butterfly.isTired() || butterfly.world.isNightTime()) && super.shouldContinueExecuting();
    }

    @Override
    public void resetTask() {
        butterfly.getNavigator().clearPath();
    }

    @Override
    protected boolean shouldMoveTo(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return canLandOnBlock(state) && !hasOtherButterfliesInArea(pos);
    }

    private boolean canLandOnBlock(BlockState state) {
        if (state.isIn(BlockTags.TALL_FLOWERS) && state.getBlock() instanceof TallFlowerBlock) {
            return state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER;
        }

        return state.isIn(BlockTags.SMALL_FLOWERS);
    }

    private boolean hasOtherButterfliesInArea(BlockPos pos) {
        Entity otherButterfly = butterfly.world.getClosestEntityWithinAABB(
            ButterflyEntity.class, BUTTERFLY_PREDICATE, butterfly,
            pos.getX(), pos.getY(), pos.getZ(), butterfly.getBoundingBox().grow(0.25, RADIUS, 0.25)
        );

        return otherButterfly != null;
    }

    @Override
    public void tick() {
        super.tick();
        if (getIsAboveDestination() && shouldMoveTo(butterfly.world, destinationBlock)) {
            butterfly.setLanded(true);

            BlockState state = butterfly.world.getBlockState(destinationBlock);
            AxisAlignedBB aabb = state.getShape(butterfly.world, destinationBlock).getBoundingBox();
            double x = destinationBlock.getX() + (aabb.minX + aabb.maxX) / 2;
            double y = destinationBlock.getY() + aabb.maxY;
            double z = destinationBlock.getZ() + (aabb.minZ + aabb.maxZ) / 2;

            if (state.getBlock() instanceof LilyPadBlock) {
                y += 0.1;
            } else if (state.getBlock() instanceof TallFlowerBlock) {
                y -= 0.25;
            }

            butterfly.setPositionAndUpdate(x, y, z);
            butterfly.setMotion(Vector3d.ZERO);
        }
    }
}
