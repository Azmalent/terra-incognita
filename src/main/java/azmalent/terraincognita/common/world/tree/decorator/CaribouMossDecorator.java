package azmalent.terraincognita.common.world.tree.decorator;

import azmalent.terraincognita.common.block.plant.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModTreeDecorators;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class CaribouMossDecorator extends TreeDecorator {
    public static final Codec<CaribouMossDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability")
        .xmap(CaribouMossDecorator::new, decorator -> decorator.probability)
        .codec();

    private final float probability;

    public CaribouMossDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    @NotNull
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.CARIBOU_MOSS.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, Random random, List<BlockPos> logPositions, List<BlockPos> leafPositions) {
        Direction.Axis logAxis = Direction.Axis.X;
        if (logPositions.size() > 1) {
            if (logPositions.get(0).getZ() != logPositions.get(1).getZ()) {
                logAxis = Direction.Axis.Z;
            }
        }

        for (BlockPos pos : logPositions) {
            BlockPos up = pos.above();
            if (Feature.isAir(level, up) && random.nextFloat() < probability) {
                setter.accept(up, ModBlocks.CARIBOU_MOSS.defaultBlockState());
            }

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos sidePos = pos.relative(direction);

                if (direction.getAxis() != logAxis && Feature.isAir(level, sidePos) && random.nextFloat() < probability) {
                    var moss = ModBlocks.CARIBOU_MOSS_WALL.defaultBlockState();
                    setter.accept(sidePos, moss.setValue(CaribouMossWallBlock.FACING, direction));
                }
            }
        }
    }
}
