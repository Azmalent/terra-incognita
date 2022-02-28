package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plant.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class CaribouMossFeature extends Feature<NoneFeatureConfiguration> {
    public CaribouMossFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();
        
        boolean success = false;
        if (level.isEmptyBlock(origin) && ModBlocks.CARIBOU_MOSS.getBlock().defaultBlockState().canSurvive(level, origin)) {
            int x = 4 + random.nextInt(6);
            int z = 4 + random.nextInt(6);
            int count = (int) (x * z * (random.nextDouble() + random.nextDouble() + 2));

            BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();
            for (int i = 0; i < count; i++) {
                nextPos.setWithOffset(origin,
                    random.nextInt(x) - random.nextInt(x),
                    random.nextInt(2) - random.nextInt(2),
                    random.nextInt(z) - random.nextInt(z)
                );

                if (level.isEmptyBlock(nextPos)) {
                    success |= tryPlaceMoss(level, nextPos, random);
                }
            }
        }

        return success;
    }

    public static boolean tryPlaceMoss(WorldGenLevel level, BlockPos pos, Random random) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = ModBlocks.CARIBOU_MOSS_WALL.defaultBlockState().setValue(CaribouMossWallBlock.FACING, direction);
            if (random.nextBoolean() && state.canSurvive(level, pos)) {
                level.setBlock(pos, state, 2);
                return true;
            }
        }

        BlockState state = ModBlocks.CARIBOU_MOSS.getBlock().defaultBlockState();
        if (state.canSurvive(level, pos)) {
            level.setBlock(pos, state, 2);
            return true;
        }

        return false;
    }
}
