package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plant.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class FallenLogFeature extends Feature<NoneFeatureConfiguration> {
    public FallenLogFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        boolean success = false;

        int length = 5 + random.nextInt(3);
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockState log = (random.nextFloat() < 0.66f ? Blocks.SPRUCE_LOG : Blocks.BIRCH_LOG).defaultBlockState().setValue(RotatedPillarBlock.AXIS, direction.getAxis());
        boolean mossy = random.nextBoolean();

        BlockPos.MutableBlockPos nextPos = origin.mutable();
        for (int i = 0; i < length; i++) {
            if (level.isEmptyBlock(nextPos) && isGrassOrDirt(level, nextPos.below())) {
                level.setBlock(nextPos, log, 2);
                if (mossy) placeCaribouMossOnLog(level, nextPos, direction, random);
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
                BlockState moss = ModBlocks.CARIBOU_MOSS_WALL.defaultBlockState();
                reader.setBlock(sidePos, moss.setValue(CaribouMossWallBlock.FACING, direction), 2);
            }
        }
    }
}
