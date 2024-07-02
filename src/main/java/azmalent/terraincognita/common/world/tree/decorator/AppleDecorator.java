package azmalent.terraincognita.common.world.tree.decorator;

import azmalent.terraincognita.common.block.fruit.AbstractFruitBlock;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModTreeDecorators;
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

public class AppleDecorator extends TreeDecorator {
    public static final AppleDecorator INSTANCE = new AppleDecorator();
    public static final Codec<AppleDecorator> CODEC = Codec.unit(() -> INSTANCE);

    public AppleDecorator() {

    }

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
