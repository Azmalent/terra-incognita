package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.world.feature.config.HugeTreeFeatureConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class HugeTreeFeature extends Feature<HugeTreeFeatureConfig> {
    public HugeTreeFeature() {
        super(HugeTreeFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, HugeTreeFeatureConfig config) {
        return false;
    }
}
