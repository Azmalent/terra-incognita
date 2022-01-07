package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.HangingMossBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class HangingMossFeature extends Feature<NoneFeatureConfiguration> {
    private static final int MIN_VERTICAL_SPACE = 3;

    private static final int MIN_Y = 40;
    private static final int MAX_Y = 96;

    private final static int X_SPREAD = 6;
    private final static int Y_SPREAD = 4;
    private final static int Z_SPREAD = 6;
    private final static int TRIES = 20;

    public HangingMossFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public boolean place(@Nonnull WorldGenLevel world, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoneFeatureConfiguration config) {
        int y = rand.nextInt(MAX_Y - MIN_Y) + MIN_Y;
        BlockPos centerPos = new BlockPos(pos.getX(), y, pos.getZ());

        BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();
        boolean success = false;

        BlockState moss = ModBlocks.HANGING_MOSS.getBlock().defaultBlockState();

        for(int i = 0; i < TRIES; i++) {
            nextPos.setWithOffset(centerPos, rand.nextInt(X_SPREAD + 1) - rand.nextInt(X_SPREAD + 1), rand.nextInt(Y_SPREAD + 1) - rand.nextInt(Y_SPREAD + 1), rand.nextInt(Z_SPREAD + 1) - rand.nextInt(Z_SPREAD + 1));
            if (nextPos.getY() < MIN_Y) {
                continue;
            }

            if (hasEnoughVerticalSpace(world, nextPos) && moss.canSurvive(world, nextPos) && !world.getBlockState(nextPos.above()).is(ModBlocks.HANGING_MOSS.getBlock())) {
                if (rand.nextBoolean()) {
                    world.setBlock(nextPos, moss, 2);
                } else {
                    world.setBlock(nextPos, moss.setValue(HangingMossBlock.VARIANT, HangingMossBlock.Variant.TOP), 2);
                    world.setBlock(nextPos.below(), moss.setValue(HangingMossBlock.VARIANT, HangingMossBlock.Variant.BOTTOM), 2);
                }

                success = true;
            }
        }

        return success;
    }

    private boolean hasEnoughVerticalSpace(WorldGenLevel reader, BlockPos pos) {
        for (int i = 0; i < MIN_VERTICAL_SPACE; i++) {
            if (!reader.isEmptyBlock(pos.below(i))) {
                return false;
            }
        }

        return true;
    }
}
