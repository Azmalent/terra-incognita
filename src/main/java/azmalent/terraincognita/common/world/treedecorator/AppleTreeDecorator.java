package azmalent.terraincognita.common.world.treedecorator;

import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.block.trees.AppleFruitBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModTreeDecorators;
import azmalent.terraincognita.common.init.ModWoodTypes;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.Direction;
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

@SuppressWarnings("ConstantConditions")
public class AppleTreeDecorator extends TreeDecorator {
    public static final AppleTreeDecorator INSTANCE = new AppleTreeDecorator();
    public static final Codec<AppleTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Nonnull
    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return ModTreeDecorators.APPLES.get();
    }

    @Override
    public void func_225576_a_(@Nonnull ISeedReader seedReader, @Nonnull Random rand, @Nonnull List<BlockPos> logPositions, @Nonnull List<BlockPos> leafPositions, @Nonnull Set<BlockPos> p_225576_5_, @Nonnull MutableBoundingBox boundingBox) {
        BlockState apple = ModWoodTypes.APPLE.FRUIT.getDefaultState();

        leafPositions.forEach((pos) -> {
            BlockPos down = pos.down();
            if (Feature.isAirAt(seedReader, down) && apple.isValidPosition(seedReader, down) && rand.nextFloat() < 0.25f) {
                this.func_227423_a_(seedReader, down, apple.with(AppleFruitBlock.AGE, rand.nextInt(8)), p_225576_5_, boundingBox);
            }
        });
    }
}
