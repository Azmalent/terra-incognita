package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.List;

public class ButterflyLandOnFlowerGoal extends MoveToBlockGoal {
    private final Butterfly butterfly;

    public ButterflyLandOnFlowerGoal(Butterfly butterfly, double speed, int searchRadius) {
        super(butterfly, speed, searchRadius);
        this.butterfly = butterfly;
    }

    @Override
    public boolean canUse() {
        return !butterfly.isLanded() && (butterfly.isTired() || butterfly.level.isNight()) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !butterfly.isLanded() && (butterfly.isTired() || butterfly.level.isNight()) && super.canContinueToUse();
    }

    @Override
    public void stop() {
        butterfly.getNavigation().stop();
    }

    @Override
    protected boolean isValidTarget(@Nonnull LevelReader world, @Nonnull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return canLandOnBlock(state) && !isBlockTaken(world, pos);
    }

    private boolean canLandOnBlock(BlockState state) {
        if (state.is(BlockTags.TALL_FLOWERS) && state.getBlock() instanceof TallFlowerBlock) {
            return state.getValue(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER;
        }

        return state.is(BlockTags.SMALL_FLOWERS);
    }

    private boolean isBlockTaken(LevelReader world, BlockPos pos) {
        VoxelShape shape = world.getBlockState(pos).getShape(world, pos);
        if (shape.isEmpty()) return true;

        List<Butterfly> entities = butterfly.level.getEntitiesOfClass(Butterfly.class, shape.bounds().expandTowards(0.5, 0.5, 0.5).move(pos));
        return entities.stream().anyMatch(e -> pos.equals(e.restGoal.restingPos));
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget() && isValidTarget(butterfly.level, blockPos)) {
            BlockState state = butterfly.level.getBlockState(blockPos);
            VoxelShape shape = state.getShape(butterfly.level, blockPos);
            if (shape.isEmpty()) return;

            butterfly.setLanded(true);

            AABB aabb = shape.bounds();
            double x = blockPos.getX() + (aabb.minX + aabb.maxX) / 2;
            double y = blockPos.getY() + aabb.maxY;
            double z = blockPos.getZ() + (aabb.minZ + aabb.maxZ) / 2;

            if (state.getBlock() instanceof WaterlilyBlock) {
                y += 0.1;
            } else if (state.getBlock() instanceof TallFlowerBlock) {
                y -= 0.25;
            }

            butterfly.teleportTo(x, y, z);
            butterfly.setDeltaMovement(Vec3.ZERO);
        }
    }
}
