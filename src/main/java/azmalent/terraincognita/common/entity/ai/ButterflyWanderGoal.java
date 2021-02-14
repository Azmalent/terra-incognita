package azmalent.terraincognita.common.entity.ai;

import azmalent.terraincognita.common.entity.ButterflyEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ButterflyWanderGoal extends Goal {
    private ButterflyEntity butterfly;

    public ButterflyWanderGoal(ButterflyEntity butterfly) {
        setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        this.butterfly = butterfly;
    }

    @Override
    public boolean shouldExecute() {
        return butterfly.getNavigator().noPath() && butterfly.getRNG().nextInt(10) == 0;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return butterfly.getNavigator().noPath();
    }

    public void startExecuting() {
        Vector3d randomLocation = getRandomLocation();
        if (randomLocation != null) {
            butterfly.getNavigator().setPath(butterfly.getNavigator().getPathToPos(new BlockPos(randomLocation), 1), 1.0D);
        }

    }

    @Nullable
    private Vector3d getRandomLocation() {
        Vector3d look = butterfly.getLook(0.0F);

        Vector3d airTarget = RandomPositionGenerator.findAirTarget(butterfly, 8, 7, look, (float) Math.PI / 2F, 2, 1);
        return airTarget != null ? airTarget : RandomPositionGenerator.findGroundTarget(butterfly, 8, 4, -2, look, (float) Math.PI / 2F);
    }
}
