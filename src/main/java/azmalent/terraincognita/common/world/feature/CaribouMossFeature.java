package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class CaribouMossFeature extends Feature<NoneFeatureConfiguration> {
    public CaribouMossFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull WorldGenLevel reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoneFeatureConfiguration config) {
        boolean success = false;
        if (reader.isEmptyBlock(pos) && ModBlocks.CARIBOU_MOSS.getBlock().defaultBlockState().canSurvive(reader, pos)) {
            int x = 4 + rand.nextInt(6);
            int z = 4 + rand.nextInt(6);
            int count = (int) (x * z * (rand.nextDouble() + rand.nextDouble() + 2));

            BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();
            for (int i = 0; i < count; i++) {
                nextPos.setWithOffset(pos,
                    rand.nextInt(x) - rand.nextInt(x),
                    rand.nextInt(2) - rand.nextInt(2),
                    rand.nextInt(z) - rand.nextInt(z)
                );

                if (reader.isEmptyBlock(nextPos)) {
                    success |= tryPlaceMoss(reader, nextPos, rand);
                }
            }
        }

        return success;
    }

    public static boolean tryPlaceMoss(WorldGenLevel reader, BlockPos pos, Random rand) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = ModBlocks.CARIBOU_MOSS_WALL.getDefaultState().setValue(CaribouMossWallBlock.FACING, direction);
            if (rand.nextBoolean() && state.canSurvive(reader, pos)) {
                reader.setBlock(pos, state, 2);
                return true;
            }
        }

        BlockState state = ModBlocks.CARIBOU_MOSS.getBlock().defaultBlockState();
        if (state.canSurvive(reader, pos)) {
            reader.setBlock(pos, state, 2);
            return true;
        }

        return false;
    }
}
