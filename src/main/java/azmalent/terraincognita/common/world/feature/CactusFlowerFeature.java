package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class CactusFlowerFeature extends Feature<NoneFeatureConfiguration> {
    public CactusFlowerFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        var cactusFlower = ModBlocks.CACTUS_FLOWER.defaultBlockState();

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for(int chunkX = 0; chunkX < 16; chunkX++) {
            for(int chunkZ = 0; chunkZ < 16; chunkZ++) {
                int x = origin.getX() + chunkX;
                int z = origin.getZ() + chunkZ;
                int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
                cursor.set(x, y, z);

                if (cactusFlower.canSurvive(level, cursor) && random.nextFloat() < 0.25f) {
                    level.setBlock(cursor, cactusFlower, 2);
                }
            }
        }

        return true;
    }
}
