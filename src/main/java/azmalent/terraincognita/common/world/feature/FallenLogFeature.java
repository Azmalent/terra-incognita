package azmalent.terraincognita.common.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class FallenLogFeature extends Feature<FallenLogFeature.Config> {
    public FallenLogFeature() {
        super(FallenLogFeature.Config.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenLogFeature.Config> context) {
        FallenLogFeature.Config config = context.config();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int length = config.length.sample(random);

        var logPositions = tryPlace(level, origin, length, direction);
        if (logPositions.size() < config.length.getMinValue()) {
            return false;
        }

        for (BlockPos pos : logPositions) {
            BlockState log = config.log().getState(random, pos).setValue(RotatedPillarBlock.AXIS, direction.getAxis());
            level.setBlock(pos, log, 19);
        }

        config.decorators.forEach(decorator -> {
            decorator.place(level, (pos, state) -> level.setBlock(pos, state, 19), random, logPositions, List.of());
        });

        return true;
    }

    private List<BlockPos> tryPlace(WorldGenLevel level, BlockPos origin, int length, Direction direction) {
        List<BlockPos> positions = Lists.newArrayList();

        BlockPos.MutableBlockPos cursor = origin.mutable();
        for (int i = 0; i < length; i++) {
            if (!level.isEmptyBlock(cursor) || !isGrassOrDirt(level, cursor.below())) {
                break;
            }

            positions.add(cursor.immutable());
            cursor.move(direction);
        }

        return positions;
    }

    public record Config(BlockStateProvider log, IntProvider length, List<TreeDecorator> decorators) implements FeatureConfiguration {
        public static final Codec<FallenLogFeature.Config> CODEC = RecordCodecBuilder.create((builder) -> builder
            .group(BlockStateProvider.CODEC.fieldOf("log").forGetter(FallenLogFeature.Config::log),
                   IntProvider.CODEC.fieldOf("length").forGetter(FallenLogFeature.Config::length),
                   TreeDecorator.CODEC.listOf().fieldOf("decorators").forGetter(FallenLogFeature.Config::decorators))
            .apply(builder, FallenLogFeature.Config::new)
        );

        public Config(BlockStateProvider log, IntProvider length) {
            this(log, length, List.of());
        }
    }
}
