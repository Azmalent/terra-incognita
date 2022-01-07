package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.ReedsBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class ReedsFeature extends Feature<NoneFeatureConfiguration> {
    private final static int X_SPREAD = 4;
    private final static int Y_SPREAD = 2;
    private final static int Z_SPREAD = 4;
    private final static int TRIES = 5;

    public ReedsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public boolean place(WorldGenLevel reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoneFeatureConfiguration config) {
        BlockPos centerPos = reader.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos);
        BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();
        boolean success = false;

        for(int i = 0; i < TRIES; i++) {
            nextPos.setWithOffset(centerPos, rand.nextInt(X_SPREAD + 1) - rand.nextInt(X_SPREAD + 1), rand.nextInt(Y_SPREAD + 1) - rand.nextInt(Y_SPREAD + 1), rand.nextInt(Z_SPREAD + 1) - rand.nextInt(Z_SPREAD + 1));
            if (!reader.isEmptyBlock(nextPos.above())) {
                continue;
            }

            int height = 2 + rand.nextInt(rand.nextInt(3) + 1);
            for (int j = 0; j < height; j++) {
                nextPos.setY(nextPos.getY() + j);

                boolean isWater = reader.getFluidState(nextPos).getType() == Fluids.WATER;
                BlockState reeds = ModBlocks.REEDS.getBlock().defaultBlockState()
                    .setValue(ReedsBlock.WATERLOGGED, isWater);

                if ((isWater || reader.isEmptyBlock(nextPos) || reader.getBlockState(nextPos).getMaterial().isReplaceable()) && reeds.canSurvive(reader, nextPos)) {
                    reader.setBlock(nextPos, reeds, 2);
                    success = true;
                }
                else {
                    break;
                }
            }
        }

        return success;
    }
}
