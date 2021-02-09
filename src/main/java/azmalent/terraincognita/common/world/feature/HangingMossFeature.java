package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.HangingMossBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class HangingMossFeature extends Feature<NoFeatureConfig> {
    private static final int MIN_Y = 40;
    private static final int MAX_Y = 96;

    private final static int X_SPREAD = 6;
    private final static int Y_SPREAD = 4;
    private final static int Z_SPREAD = 6;
    private final static int TRIES = 20;

    public HangingMossFeature() {
        super(NoFeatureConfig.field_236558_a_);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean generate(ISeedReader reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        int y = rand.nextInt(MAX_Y - MIN_Y) + MIN_Y;
        BlockPos centerPos = new BlockPos(pos.getX(), y, pos.getZ());

        BlockPos.Mutable nextPos = new BlockPos.Mutable();
        boolean success = false;

        BlockState moss = ModBlocks.HANGING_MOSS.getBlock().getDefaultState();

        for(int i = 0; i < TRIES; i++) {
            nextPos.setAndOffset(centerPos, rand.nextInt(X_SPREAD + 1) - rand.nextInt(X_SPREAD + 1), rand.nextInt(Y_SPREAD + 1) - rand.nextInt(Y_SPREAD + 1), rand.nextInt(Z_SPREAD + 1) - rand.nextInt(Z_SPREAD + 1));
            if (nextPos.getY() < MIN_Y) {
                continue;
            }

            if (reader.isAirBlock(nextPos) && moss.isValidPosition(reader, nextPos)) {
                if (isEnoughVerticalSpace(reader, pos)) {
                    if (rand.nextBoolean()) {
                        reader.setBlockState(nextPos, moss, 2);
                    } else {
                        reader.setBlockState(nextPos, moss.with(HangingMossBlock.VARIANT, HangingMossBlock.Variant.TOP), 2);
                        reader.setBlockState(nextPos.down(), moss.with(HangingMossBlock.VARIANT, HangingMossBlock.Variant.BOTTOM), 2);
                    }

                    success = true;
                }
            }
        }

        return success;
    }

    private boolean isEnoughVerticalSpace(ISeedReader reader, BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            if (!reader.isAirBlock(pos.down(i))) {
                return false;
            }
        }

        return true;
    }
}
