package azmalent.terraincognita.common.entity.butterfly.ai;

import azmalent.terraincognita.common.entity.butterfly.AbstractButterflyEntity;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ButterflyWanderGoal extends Goal {
    private final AbstractButterflyEntity butterfly;

    public ButterflyWanderGoal(AbstractButterflyEntity butterfly) {
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
        this.butterfly = butterfly;
    }

    @Override
    public boolean canUse() {
        return !butterfly.isLanded() && butterfly.getNavigation().isDone() && butterfly.getRandom().nextInt(5) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return butterfly.getNavigation().isInProgress();
    }

    public void start() {
        Vec3 randomLocation = getRandomLocation();
        if (randomLocation != null) {
            butterfly.getNavigation().moveTo(butterfly.getNavigation().createPath(new BlockPos(randomLocation), 1), 1.0D);
        }
    }

    @Nullable
    private Vec3 getRandomLocation() {
        BlockPos home = butterfly.getRestrictCenter();

        Vec3 direction;
        if (!butterfly.isWithinRestriction(home)) {
            direction = Vec3.atCenterOf(home).subtract(butterfly.position()).normalize();
        } else {
            direction = butterfly.getViewVector(0.0F);
        }

        Vec3 airTarget = RandomPos.getAboveLandPos(butterfly, 8, 7, direction, (float) Math.PI / 2F, 2, 1);
        return airTarget != null ? airTarget : RandomPos.getAirPos(butterfly, 8, 4, -2, direction, (float) Math.PI / 2F);
    }
}
