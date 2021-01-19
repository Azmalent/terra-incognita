package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.TIConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFlowersFeature;

public class EdelweissFeature extends DefaultFlowersFeature {
    public EdelweissFeature() {
        super(BlockClusterFeatureConfig.field_236587_a_);
    }

    @Override
    public boolean isValidPosition(IWorld world, BlockPos pos, BlockClusterFeatureConfig config) {
        return (pos.getY() >= TIConfig.Flora.edelweissMinimumY.get()) && super.isValidPosition(world, pos, config);
    }
}
