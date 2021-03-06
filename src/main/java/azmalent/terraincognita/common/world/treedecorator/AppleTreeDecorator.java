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

public class AppleTreeDecorator extends TreeDecorator {
    public static final AppleTreeDecorator INSTANCE = new AppleTreeDecorator();
    public static final Codec<AppleTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Nonnull
    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return ModTreeDecorators.APPLES.get();
    }

    @Override
    public void func_225576_a_(@Nonnull ISeedReader seedReader, @Nonnull Random rand, @Nonnull List<BlockPos> logPositions, @Nonnull List<BlockPos> leafPositions, @Nonnull Set<BlockPos> decorationPositions, @Nonnull MutableBoundingBox boundingBox) {
        BlockState apple = ModBlocks.APPLE.getDefaultState();

        leafPositions.forEach((pos) -> {
            BlockPos down = pos.down();
            if (Feature.isAirAt(seedReader, down) && apple.isValidPosition(seedReader, down) && rand.nextFloat() < 0.25f) {
                this.func_227423_a_(seedReader, down, apple.with(AbstractFruitBlock.AGE, rand.nextInt(8)), decorationPositions, boundingBox);
            }
        });
    }
}
