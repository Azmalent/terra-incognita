package azmalent.terraincognita.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class MossyBoulderFeature extends Feature<NoFeatureConfig> {
    private static final BlockState COBBLESTONE = Blocks.COBBLESTONE.getDefaultState();
    private static final BlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.getDefaultState();

    public MossyBoulderFeature() {
        super(NoFeatureConfig.field_236558_a_);
    }

    private boolean shouldSkipBlock(ISeedReader reader, BlockPos pos) {
        if (reader.isAirBlock(pos)) return true;

        Block block = reader.getBlockState(pos).getBlock();
        return !isDirt(block) && !isStone(block);
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos centerPos, @Nonnull NoFeatureConfig config) {
        int radius = rand.nextInt(4) + 2;

        while (centerPos.getY() > radius && shouldSkipBlock(reader, centerPos.down())) {
            centerPos = centerPos.down();
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

            for (BlockPos pos : BlockPos.getAllInBoxMutable(centerPos.add(-i, -j, -k), centerPos.add(i, j, k))) {
                double distance = pos.distanceSq(centerPos);
                if (distance <= f * f) {
                    BlockState state = (distance <= g * g || rand.nextBoolean()) ? COBBLESTONE : MOSSY_COBBLESTONE;
                    reader.setBlockState(pos, state, 4);
                }
            }

            centerPos = centerPos.add(-1 + rand.nextInt(radius - 1), -rand.nextInt(radius - 1), -1 + rand.nextInt(radius - 1));
        }

        return true;
    }
}
