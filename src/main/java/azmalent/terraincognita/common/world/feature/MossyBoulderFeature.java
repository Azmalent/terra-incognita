package azmalent.terraincognita.common.world.feature;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class MossyBoulderFeature extends Feature<NoneFeatureConfiguration> {
    private static final BlockState COBBLESTONE = Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.defaultBlockState();

    public MossyBoulderFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private boolean shouldSkipBlock(WorldGenLevel reader, BlockPos pos) {
        if (reader.isEmptyBlock(pos)) return true;

        BlockState block = reader.getBlockState(pos);
        return !isDirt(block) && !isStone(block);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        int radius = random.nextInt(4) + 2;

        while (origin.getY() > radius && shouldSkipBlock(level, origin.below())) {
            origin = origin.below();
        }

        if (origin.getY() <= radius) {
            return false;
        }

        for (int l = 0; l < 3; ++l) {
            int i = random.nextInt(radius - 1);
            int j = random.nextInt(radius - 1);
            int k = random.nextInt(radius - 1);
            float f = (i + j + k) / 3f + 0.5F;
            float g = f - 1;

            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-i, -j, -k), origin.offset(i, j, k))) {
                double distance = pos.distSqr(origin);
                if (distance <= f * f) {
                    BlockState state = (distance <= g * g || random.nextBoolean()) ? COBBLESTONE : MOSSY_COBBLESTONE;
                    level.setBlock(pos, state, 4);
                }
            }

            origin = origin.offset(-1 + random.nextInt(radius - 1), -random.nextInt(radius - 1), -1 + random.nextInt(radius - 1));
        }

        return true;
    }
}
