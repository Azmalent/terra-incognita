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

public class AppleTreeDecorator extends TreeDecorator {
    public static final AppleTreeDecorator INSTANCE = new AppleTreeDecorator();
    public static final Codec<AppleTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Nonnull
    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.APPLES.get();
    }

    @Override
    public void place(@Nonnull WorldGenLevel seedReader, @Nonnull Random rand, @Nonnull List<BlockPos> logPositions, @Nonnull List<BlockPos> leafPositions, @Nonnull Set<BlockPos> decorationPositions, @Nonnull BoundingBox boundingBox) {
        BlockState apple = ModBlocks.APPLE.getDefaultState();

        leafPositions.forEach((pos) -> {
            BlockPos down = pos.below();
            if (Feature.isAir(seedReader, down) && apple.canSurvive(seedReader, down) && rand.nextFloat() < 0.25f) {
                this.setBlock(seedReader, down, apple.setValue(AbstractFruitBlock.AGE, rand.nextInt(8)), decorationPositions, boundingBox);
            }
        });
    }
}
