package azmalent.terraincognita.common.world.tree.decorator;

import azmalent.terraincognita.common.registry.ModTreeDecorators;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class MushroomDecorator extends TreeDecorator {
    public static final Codec<MushroomDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability")
        .xmap(MushroomDecorator::new, decorator -> decorator.probability)
        .codec();

    private final float probability;

    public MushroomDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    @NotNull
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.MUSHROOM.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, Random random, List<BlockPos> logPositions, List<BlockPos> leafPositions) {
        for (BlockPos pos : logPositions) {
            BlockPos up = pos.above();
            if (Feature.isAir(level, up) && random.nextFloat() < probability) {
                BlockState mushroom = (random.nextBoolean() ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM).defaultBlockState();
                setter.accept(up, mushroom);
            }
        }
    }
}
