package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class HangingMossFeature extends Feature<NoFeatureConfig> {
    private static final int MINIMUM_Y = 50;

    private final static int X_SPREAD = 4;
    private final static int Y_SPREAD = 2;
    private final static int Z_SPREAD = 4;
    private final static int TRIES = 8;

    public HangingMossFeature() {
        super(NoFeatureConfig.field_236558_a_);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos centerPos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);
        BlockPos.Mutable nextPos = new BlockPos.Mutable();
        boolean success = false;

        BlockState moss = ModBlocks.MOSS.getBlock().getDefaultState();

        for(int i = 0; i < TRIES; i++) {
            nextPos.setAndOffset(centerPos, rand.nextInt(X_SPREAD + 1) - rand.nextInt(X_SPREAD + 1), rand.nextInt(Y_SPREAD + 1) - rand.nextInt(Y_SPREAD + 1), rand.nextInt(Z_SPREAD + 1) - rand.nextInt(Z_SPREAD + 1));
            if (nextPos.getY() < MINIMUM_Y) {
                continue;
            }

            if (reader.isAirBlock(nextPos) && moss.isValidPosition(reader, nextPos)) {
                reader.setBlockState(nextPos, moss, 2);
                success = true;

                BlockPos down = nextPos.down();
                if (reader.isAirBlock(down) && moss.isValidPosition(reader, down) && rand.nextBoolean()) {
                    reader.setBlockState(down, moss, 2);
                }
            }
        }

        return success;
    }
}
