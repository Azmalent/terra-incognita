package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plant.SedgeBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class SedgeFeature extends Feature<NoneFeatureConfiguration> {
    private final static int X_SPREAD = 4;
    private final static int Y_SPREAD = 2;
    private final static int Z_SPREAD = 4;
    private final static int TRIES = 5;

    public SedgeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        BlockPos centerPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, origin);
        BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();
        boolean success = false;

        for(int i = 0; i < TRIES; i++) {
            nextPos.setWithOffset(centerPos, random.nextInt(X_SPREAD + 1) - random.nextInt(X_SPREAD + 1), random.nextInt(Y_SPREAD + 1) - random.nextInt(Y_SPREAD + 1), random.nextInt(Z_SPREAD + 1) - random.nextInt(Z_SPREAD + 1));
            if (!level.isEmptyBlock(nextPos.above())) {
                continue;
            }

            int height = 2 + random.nextInt(random.nextInt(3) + 1);
            for (int j = 0; j < height; j++) {
                nextPos.setY(nextPos.getY() + j);

                boolean isWater = level.getFluidState(nextPos).getType() == Fluids.WATER;
                BlockState sedge = ModBlocks.SEDGE.getBlock().defaultBlockState()
                    .setValue(SedgeBlock.WATERLOGGED, isWater);

                if ((isWater || level.isEmptyBlock(nextPos) || level.getBlockState(nextPos).getMaterial().isReplaceable()) && sedge.canSurvive(level, nextPos)) {
                    level.setBlock(nextPos, sedge, 2);
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
