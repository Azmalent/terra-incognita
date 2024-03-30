package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plant.SwampReedsBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class SwampReedsFeature extends Feature<NoneFeatureConfiguration> {
    private final static int X_SPREAD = 8;
    private final static int Y_SPREAD = 2;
    private final static int Z_SPREAD = 8;
    private final static int TRIES = 25;

    public SwampReedsFeature() {
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
                nextPos.setY(nextPos.getY() + 1);

                //todo: rewrite without block updates
                boolean isWater = level.getFluidState(nextPos).getType() == Fluids.WATER;
                var variant = level.getBlockState(nextPos.below()).getBlock() == ModBlocks.SWAMP_REEDS.get() ? SwampReedsBlock.Variant.TOP : SwampReedsBlock.Variant.SINGLE;
                BlockState reeds = ModBlocks.SWAMP_REEDS.defaultBlockState().setValue(SwampReedsBlock.VARIANT, variant).setValue(SwampReedsBlock.WATERLOGGED, isWater);

                if ((isWater || level.isEmptyBlock(nextPos) || level.getBlockState(nextPos).getMaterial().isReplaceable()) && reeds.canSurvive(level, nextPos)) {
                    level.setBlock(nextPos, reeds, 3);
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
