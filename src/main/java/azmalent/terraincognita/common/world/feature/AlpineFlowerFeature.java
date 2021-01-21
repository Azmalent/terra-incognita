package azmalent.terraincognita.common.world.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFlowersFeature;

public class AlpineFlowerFeature extends DefaultFlowersFeature {
    private int minHeight;

    public AlpineFlowerFeature(int minHeight) {
        super(BlockClusterFeatureConfig.field_236587_a_);
        this.minHeight = minHeight;
    }

    @Override
    public boolean isValidPosition(IWorld world, BlockPos pos, BlockClusterFeatureConfig config) {
        return world.canBlockSeeSky(pos) && (pos.getY() >= minHeight) && super.isValidPosition(world, pos, config);
    }
}
