package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;

public class ButterflyRestGoal extends Goal {
    private final ButterflyEntity butterfly;

    public BlockPos restingPos;
    private BlockState initialBlockState;
    private int ticks;

    public ButterflyRestGoal(ButterflyEntity butterfly) {
        this.butterfly = butterfly;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (butterfly.isLanded() && !butterfly.hasPlayersNearby()) {
            restingPos = butterfly.blockPosition();
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return butterfly.isLanded() && (butterfly.isTired() || butterfly.level.isNight()) && !butterfly.hasPlayersNearby();
    }

    @Override
    public void start() {
        initialBlockState = butterfly.level.getBlockState(restingPos);
        ticks = 0;
    }

    @Override
    public void stop() {
        butterfly.setNotLanded();
        restingPos = null;
        initialBlockState = null;
        ticks = 0;
    }

    @Override
    public void tick() {
        if (restingPos == null) restingPos = butterfly.blockPosition();
        ticks++;

        if (blockStateUpdated() || isBlockTaken(butterfly.level, restingPos)) {
            butterfly.setNotLanded();
        }
        else if (butterfly.getRandom().nextInt(200) == 0) {
            if (ticks > 300) {
                butterfly.setTired(false);
                if (!butterfly.level.isNight()) butterfly.setNotLanded();
            } else {
                butterfly.yHeadRot = butterfly.getRandom().nextInt(360);
            }
        }
    }

    private boolean isBlockTaken(Level world, BlockPos pos) {
        VoxelShape shape = world.getBlockState(pos).getShape(world, pos);
        if (shape.isEmpty()) return true;

        List<Entity> entities = world.getEntitiesOfClass(ButterflyEntity.class, shape.bounds().expandTowards(0.5, 0.5, 0.5).move(pos));
        return entities.stream().anyMatch(e -> e != butterfly && pos.equals(((ButterflyEntity) e).restGoal.restingPos));
    }

    private boolean blockStateUpdated() {
        return initialBlockState != butterfly.level.getBlockState(butterfly.blockPosition());
    }
}
