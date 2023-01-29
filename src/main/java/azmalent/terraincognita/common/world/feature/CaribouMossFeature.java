package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plant.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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
        if (level.isEmptyBlock(origin) && ModBlocks.CARIBOU_MOSS.defaultBlockState().canSurvive(level, origin)) {
            int r = 4 + random.nextInt(6);
            int count = (int) (r * r * (1 * random.nextDouble() + random.nextDouble()));

            BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
            for (int i = 0; i < count; i++) {
                double angle = random.nextFloat() * 2 * Math.PI;
                float distance = random.nextFloat() * r;

                cursor.setWithOffset(origin,
                    (int) (distance * Math.cos(angle)),
            random.nextInt(2) - random.nextInt(2),
                    (int) (distance * Math.sin(angle))
                );

                if (level.isEmptyBlock(cursor)) {
                    success |= tryPlaceMoss(level, cursor, random);
                }
            }
        }

        return success;
    }

    public static boolean tryPlaceMoss(WorldGenLevel level, BlockPos pos, Random random) {
        boolean snowy = level.getBiome(pos).value().coldEnoughToSnow(pos);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = ModBlocks.CARIBOU_MOSS_WALL.defaultBlockState().setValue(CaribouMossWallBlock.FACING, direction);
            if (random.nextBoolean() && state.canSurvive(level, pos)) {
                level.setBlock(pos, state, 2);

                BlockPos wall = pos.relative(direction.getOpposite());
                if (!snowy && level.getBlockState(wall).is(BlockTags.MOSS_REPLACEABLE) && random.nextBoolean()) {
                    level.setBlock(wall, Blocks.MOSS_BLOCK.defaultBlockState(), 2);
                }

                return true;
            }
        }

        BlockState state = ModBlocks.CARIBOU_MOSS.defaultBlockState();
        if (state.canSurvive(level, pos)) {
            level.setBlock(pos, !snowy && random.nextBoolean() ? Blocks.MOSS_CARPET.defaultBlockState() : state, 2);

            BlockPos under = pos.below();
            if (!snowy && level.getBlockState(under).is(BlockTags.MOSS_REPLACEABLE) && random.nextBoolean()) {
                level.setBlock(under, Blocks.MOSS_BLOCK.defaultBlockState(), 2);
            }

            return true;
        }

        return false;
    }
}
