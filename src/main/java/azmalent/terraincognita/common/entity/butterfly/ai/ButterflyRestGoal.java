package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class ButterflyRestGoal extends Goal {
    private final ButterflyEntity butterfly;

    public BlockPos restingPos;
    private BlockState initialBlockState;
    private int ticks;

    public ButterflyRestGoal(ButterflyEntity butterfly) {
        this.butterfly = butterfly;
        setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        if (butterfly.isLanded() && !butterfly.hasPlayersNearby()) {
            restingPos = butterfly.getPosition();
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return butterfly.isLanded() && (butterfly.isTired() || butterfly.world.isNightTime()) && !butterfly.hasPlayersNearby();
    }

    @Override
    public void startExecuting() {
        initialBlockState = butterfly.world.getBlockState(restingPos);
        ticks = 0;
    }

    @Override
    public void resetTask() {
        butterfly.setNotLanded();
        restingPos = null;
        initialBlockState = null;
        ticks = 0;
    }

    @Override
    public void tick() {
        if (restingPos == null) restingPos = butterfly.getPosition();
        ticks++;

        if (blockStateUpdated() || isBlockTaken(restingPos)) {
            butterfly.setNotLanded();
        }
        else if (butterfly.getRNG().nextInt(200) == 0) {
            if (ticks > 300) {
                butterfly.setTired(false);
                if (!butterfly.world.isNightTime()) butterfly.setNotLanded();
            } else {
                butterfly.rotationYawHead = butterfly.getRNG().nextInt(360);
            }
        }
    }

    private boolean isBlockTaken(BlockPos pos) {
        World world = butterfly.world;
        List<Entity> entities = world.getEntitiesWithinAABB(
            ButterflyEntity.class, world.getBlockState(pos).getShape(world, pos).getBoundingBox().expand(0.5, 0.5, 0.5).offset(pos)
        );

        return entities.stream().anyMatch(e -> e != butterfly && pos.equals(((ButterflyEntity) e).restGoal.restingPos));
    }

    private boolean blockStateUpdated() {
        return initialBlockState != butterfly.world.getBlockState(butterfly.getPosition());
    }
}
