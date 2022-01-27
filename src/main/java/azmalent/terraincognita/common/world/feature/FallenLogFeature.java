package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class FallenLogFeature extends Feature<NoneFeatureConfiguration> {
    public FallenLogFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull WorldGenLevel reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoneFeatureConfiguration config) {
        boolean success = false;

        int length = 5 + rand.nextInt(3);
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
        BlockState log = (rand.nextFloat() < 0.66f ? Blocks.SPRUCE_LOG : Blocks.BIRCH_LOG).defaultBlockState().setValue(RotatedPillarBlock.AXIS, direction.getAxis());
        boolean mossy = rand.nextBoolean();

        BlockPos.MutableBlockPos nextPos = pos.mutable();
        for (int i = 0; i < length; i++) {
            if (reader.isEmptyBlock(nextPos) && isGrassOrDirt(reader, nextPos.below())) {
                reader.setBlock(nextPos, log, 2);
                if (mossy) placeCaribouMossOnLog(reader, nextPos, direction, rand);
                success = true;
            }

            nextPos.move(direction);
        }

        return success;
    }

    private void placeCaribouMossOnLog(WorldGenLevel reader, BlockPos pos, Direction logDirection, Random rand) {
        BlockPos up = pos.above();
        if (reader.isEmptyBlock(up) && rand.nextFloat() < 0.66f) {
            BlockState moss = ModBlocks.CARIBOU_MOSS.getBlock().defaultBlockState();
            reader.setBlock(up, moss, 2);
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos sidePos = pos.relative(direction);
            if (direction.getAxis() != logDirection.getAxis() && reader.isEmptyBlock(sidePos) && rand.nextFloat() < 0.66) {
                BlockState moss = ModBlocks.CARIBOU_MOSS_WALL.getDefaultState();
                reader.setBlock(sidePos, moss.setValue(CaribouMossWallBlock.FACING, direction), 2);
            }
        }
    }
}
