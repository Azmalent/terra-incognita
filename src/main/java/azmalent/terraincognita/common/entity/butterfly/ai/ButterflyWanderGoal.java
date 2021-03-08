package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.AbstractButterflyEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ButterflyWanderGoal extends Goal {
    private final AbstractButterflyEntity butterfly;

    public ButterflyWanderGoal(AbstractButterflyEntity butterfly) {
        setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        this.butterfly = butterfly;
    }

    @Override
    public boolean shouldExecute() {
        return !butterfly.isLanded() && butterfly.getNavigator().noPath() && butterfly.getRNG().nextInt(5) == 0;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return butterfly.getNavigator().hasPath();
    }

    public void startExecuting() {
        Vector3d randomLocation = getRandomLocation();
        if (randomLocation != null) {
            butterfly.getNavigator().setPath(butterfly.getNavigator().getPathToPos(new BlockPos(randomLocation), 1), 1.0D);
        }
    }

    @Nullable
    private Vector3d getRandomLocation() {
        BlockPos home = butterfly.getHomePosition();

        Vector3d direction;
        if (!butterfly.isWithinHomeDistanceFromPosition(home)) {
            direction = Vector3d.copyCentered(home).subtract(butterfly.getPositionVec()).normalize();
        } else {
            direction = butterfly.getLook(0.0F);
        }

        Vector3d airTarget = RandomPositionGenerator.findAirTarget(butterfly, 8, 7, direction, (float) Math.PI / 2F, 2, 1);
        return airTarget != null ? airTarget : RandomPositionGenerator.findGroundTarget(butterfly, 8, 4, -2, direction, (float) Math.PI / 2F);
    }
}
