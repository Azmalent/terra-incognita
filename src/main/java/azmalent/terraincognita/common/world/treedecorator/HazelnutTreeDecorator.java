package azmalent.terraincognita.common.world.treedecorator;

import azmalent.terraincognita.common.block.trees.AbstractFruitBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModTreeDecorators;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HazelnutTreeDecorator extends TreeDecorator {
    public static final HazelnutTreeDecorator INSTANCE = new HazelnutTreeDecorator();
    public static final Codec<HazelnutTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Nonnull
    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.HAZELNUTS.get();
    }

    @Override
    public void place(@Nonnull WorldGenLevel seedReader, @Nonnull Random rand, @Nonnull List<BlockPos> logPositions, @Nonnull List<BlockPos> leafPositions, @Nonnull Set<BlockPos> decorationPositions, @Nonnull BoundingBox boundingBox) {
        BlockState hazelnut = ModBlocks.HAZELNUT.getDefaultState();

        leafPositions.forEach((pos) -> {
            BlockPos down = pos.below();
            if (Feature.isAir(seedReader, down) && hazelnut.canSurvive(seedReader, down) && rand.nextFloat() < 0.33f) {
                this.setBlock(seedReader, down, hazelnut.setValue(AbstractFruitBlock.AGE, rand.nextInt(8)), decorationPositions, boundingBox);
            }
        });
    }
}
