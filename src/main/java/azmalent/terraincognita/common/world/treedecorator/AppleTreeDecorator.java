package azmalent.terraincognita.common.world.treedecorator;

import azmalent.terraincognita.common.block.trees.AbstractFruitBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModTreeDecorators;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

public class AppleTreeDecorator extends TreeDecorator {
    public static final AppleTreeDecorator INSTANCE = new AppleTreeDecorator();
    public static final Codec<AppleTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Nonnull
    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.APPLES.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, List<BlockPos> logPositions, List<BlockPos> leafPositions) {
        BlockState apple = ModBlocks.APPLE.defaultBlockState();

        leafPositions.forEach(pos -> {
            BlockPos down = pos.below();
            if (Feature.isAir(level, down) && random.nextFloat() < 0.25f) {
                blockSetter.accept(down, apple.setValue(AbstractFruitBlock.AGE, random.nextInt(8)));
            }
        });
    }
}
