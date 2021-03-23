package azmalent.terraincognita.common.world.treedecorator;

import azmalent.terraincognita.common.block.trees.AbstractFruitBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModTreeDecorators;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HazelnutTreeDecorator extends TreeDecorator {
    public static final HazelnutTreeDecorator INSTANCE = new HazelnutTreeDecorator();
    public static final Codec<HazelnutTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Nonnull
    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return ModTreeDecorators.HAZELNUTS.get();
    }

    @Override
    public void func_225576_a_(@Nonnull ISeedReader seedReader, @Nonnull Random rand, @Nonnull List<BlockPos> logPositions, @Nonnull List<BlockPos> leafPositions, @Nonnull Set<BlockPos> decorationPositions, @Nonnull MutableBoundingBox boundingBox) {
        BlockState hazelnut = ModBlocks.HAZELNUT.getDefaultState();

        leafPositions.forEach((pos) -> {
            BlockPos down = pos.down();
            if (Feature.isAirAt(seedReader, down) && hazelnut.isValidPosition(seedReader, down) && rand.nextFloat() < 0.33f) {
                this.func_227423_a_(seedReader, down, hazelnut.with(AbstractFruitBlock.AGE, rand.nextInt(8)), decorationPositions, boundingBox);
            }
        });
    }
}
