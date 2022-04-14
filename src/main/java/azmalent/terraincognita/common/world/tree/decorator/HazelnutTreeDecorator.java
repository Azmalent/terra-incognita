package azmalent.terraincognita.common.world.tree.decorator;

import azmalent.terraincognita.common.block.fruit.AbstractFruitBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModTreeDecorators;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class HazelnutTreeDecorator extends TreeDecorator {
    public static final HazelnutTreeDecorator INSTANCE = new HazelnutTreeDecorator();
    public static final Codec<HazelnutTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Nonnull
    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.HAZELNUTS.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, List<BlockPos> logPositions, List<BlockPos> leafPositions) {
        BlockState hazelnut = ModBlocks.HAZELNUT.defaultBlockState();

        leafPositions.forEach((pos) -> {
            BlockPos down = pos.below();
            if (Feature.isAir(level, down) && random.nextFloat() < 0.33f) {
                blockSetter.accept(down, hazelnut.setValue(AbstractFruitBlock.AGE, random.nextInt(8)));
            }
        });
    }
}
