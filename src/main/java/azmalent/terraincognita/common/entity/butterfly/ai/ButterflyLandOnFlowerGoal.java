package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;
import java.util.List;

public class ButterflyLandOnFlowerGoal extends MoveToBlockGoal {
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
        return canLandOnBlock(state) && !isBlockTaken(world, pos);
    }

    private boolean canLandOnBlock(BlockState state) {
        if (state.isIn(BlockTags.TALL_FLOWERS) && state.getBlock() instanceof TallFlowerBlock) {
            return state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER;
        }

        return state.isIn(BlockTags.SMALL_FLOWERS);
    }

    private boolean isBlockTaken(IWorldReader world, BlockPos pos) {
        VoxelShape shape = world.getBlockState(pos).getShape(world, pos);
        if (shape.isEmpty()) return true;

        List<Entity> entities = butterfly.world.getEntitiesWithinAABB(ButterflyEntity.class, shape.getBoundingBox().expand(0.5, 0.5, 0.5).offset(pos));

        return entities.stream().anyMatch(e -> pos.equals(((ButterflyEntity) e).restGoal.restingPos));
    }

    @Override
    public void tick() {
        super.tick();
        if (getIsAboveDestination() && shouldMoveTo(butterfly.world, destinationBlock)) {
            BlockState state = butterfly.world.getBlockState(destinationBlock);
            VoxelShape shape = state.getShape(butterfly.world, destinationBlock);
            if (shape.isEmpty()) return;

            butterfly.setLanded(true);

            AxisAlignedBB aabb = shape.getBoundingBox();
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
