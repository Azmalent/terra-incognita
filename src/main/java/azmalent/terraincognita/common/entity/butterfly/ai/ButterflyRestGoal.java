package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;

import static azmalent.terraincognita.common.entity.butterfly.ButterflyEntity.PLAYER_PREDICATE;

public class ButterflyRestGoal extends Goal {
    private final ButterflyEntity butterfly;

    private BlockState initialBlockState;
    private int ticks;

    public ButterflyRestGoal(ButterflyEntity butterfly) {
        this.butterfly = butterfly;
    }

    @Override
    public boolean shouldExecute() {
        return butterfly.isLanded();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return butterfly.isLanded();
    }

    @Override
    public void startExecuting() {
        initialBlockState = butterfly.world.getBlockState(butterfly.getPosition());
        ticks = 0;
    }

    @Override
    public void resetTask() {
        butterfly.setNotLanded();
        initialBlockState = null;
        ticks = 0;
    }

    @Override
    public void tick() {
        ticks++;

        if (ticks > 300 && butterfly.world.rand.nextInt(200) == 0) {
            butterfly.setTired(false);
            if (!butterfly.world.isNightTime()) butterfly.setNotLanded();
        }
        else if (blockStateUpdated() || butterfly.world.getClosestPlayer(PLAYER_PREDICATE, butterfly) != null) {
            butterfly.setNotLanded();
        }
        else if (butterfly.getRNG().nextInt(200) == 0) {
            butterfly.rotationYawHead = butterfly.getRNG().nextInt(360);
        }
    }

    private boolean blockStateUpdated() {
        return initialBlockState != butterfly.world.getBlockState(butterfly.getPosition());
    }
}
