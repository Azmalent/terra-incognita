package azmalent.terraincognita.common.world.feature;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
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

        Block block = reader.getBlockState(pos).getBlock();
        return !isDirt(block) && !isStone(block);
    }

    @Override
    public boolean place(@Nonnull WorldGenLevel reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos centerPos, @Nonnull NoneFeatureConfiguration config) {
        int radius = rand.nextInt(4) + 2;

        while (centerPos.getY() > radius && shouldSkipBlock(reader, centerPos.below())) {
            centerPos = centerPos.below();
        }

        if (centerPos.getY() <= radius) {
            return false;
        }

        for (int l = 0; l < 3; ++l) {
            int i = rand.nextInt(radius - 1);
            int j = rand.nextInt(radius - 1);
            int k = rand.nextInt(radius - 1);
            float f = (i + j + k) / 3f + 0.5F;
            float g = f - 1;

            for (BlockPos pos : BlockPos.betweenClosed(centerPos.offset(-i, -j, -k), centerPos.offset(i, j, k))) {
                double distance = pos.distSqr(centerPos);
                if (distance <= f * f) {
                    BlockState state = (distance <= g * g || rand.nextBoolean()) ? COBBLESTONE : MOSSY_COBBLESTONE;
                    reader.setBlock(pos, state, 4);
                }
            }

            centerPos = centerPos.offset(-1 + rand.nextInt(radius - 1), -rand.nextInt(radius - 1), -1 + rand.nextInt(radius - 1));
        }

        return true;
    }
}
