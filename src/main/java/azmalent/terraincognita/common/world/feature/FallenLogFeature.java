package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plant.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Random;

public class FallenLogFeature extends Feature<FallenLogFeature.Config> {
    private enum DecorationType {
        NONE, MUSHROOMS, CARIBOU_MOSS
    }

    public FallenLogFeature() {
        super(FallenLogFeature.Config.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenLogFeature.Config> context) {
        WorldGenLevel level = context.level();
        FallenLogFeature.Config config = context.config();
        BlockPos origin = context.origin();
        Random random = context.random();

        boolean success = false;

        int length = 5 + random.nextInt(3);
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockState log = config.log().getState(random, origin).setValue(RotatedPillarBlock.AXIS, direction.getAxis());

        DecorationType decoration = chooseDecorationType(config, random);

        BlockPos.MutableBlockPos nextPos = origin.mutable();
        for (int i = 0; i < length; i++) {
            if (level.isEmptyBlock(nextPos) && isGrassOrDirt(level, nextPos.below())) {
                level.setBlock(nextPos, log, 2);
                if (decoration == DecorationType.MUSHROOMS) {
                    placeMushroomOnLog(level, nextPos, random);
                } else if (decoration == DecorationType.CARIBOU_MOSS) {
                    placeCaribouMossOnLog(level, nextPos, direction, random);
                }

                success = true;
            }

            nextPos.move(direction);
        }

        return success;
    }

    private DecorationType chooseDecorationType(FallenLogFeature.Config config, Random random) {
        if (config.mushrooms && config.caribouMoss) {
            double n = random.nextDouble();
            if (n < 0.33) return DecorationType.MUSHROOMS;
            else if (n < 0.66) return DecorationType.CARIBOU_MOSS;
        } else if (config.mushrooms || config.caribouMoss) {
            if (random.nextDouble() < 0.5) {
                return config.mushrooms ? DecorationType.MUSHROOMS : DecorationType.CARIBOU_MOSS;
            }
        }

        return DecorationType.NONE;
    }

    private void placeMushroomOnLog(WorldGenLevel reader, BlockPos pos, Random rand) {
        BlockPos up = pos.above();
        if (reader.isEmptyBlock(up) && rand.nextFloat() < 0.66f) {
            BlockState mushroom = (rand.nextBoolean() ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM).defaultBlockState();
            reader.setBlock(up, mushroom, 2);
        }
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

    public record Config(BlockStateProvider log, boolean mushrooms, boolean caribouMoss) implements FeatureConfiguration {
        public static final Codec<FallenLogFeature.Config> CODEC = RecordCodecBuilder.create((something) -> something
            .group(BlockStateProvider.CODEC.fieldOf("log").forGetter(FallenLogFeature.Config::log),
                   Codec.BOOL.fieldOf("mushrooms").forGetter(FallenLogFeature.Config::mushrooms),
                   Codec.BOOL.fieldOf("caribouMoss").forGetter(FallenLogFeature.Config::mushrooms))
            .apply(something, FallenLogFeature.Config::new)
        );
    }
}
