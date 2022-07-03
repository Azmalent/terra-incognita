package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plant.HangingMossBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class HangingMossFeature extends Feature<NoneFeatureConfiguration> {
    public HangingMossFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var level = context.level();
        var random = context.random();
        var pos = context.origin();

        BlockState hangingMoss = ModBlocks.HANGING_MOSS.defaultBlockState();

        if (hasEnoughVerticalSpace(level, pos) && hangingMoss.canSurvive(level, pos)) {
            if (random.nextBoolean()) {
                level.setBlock(pos, hangingMoss, 2);
            } else {
                level.setBlock(pos, hangingMoss.setValue(HangingMossBlock.VARIANT, HangingMossBlock.Variant.TOP), 2);
                level.setBlock(pos.below(), hangingMoss.setValue(HangingMossBlock.VARIANT, HangingMossBlock.Variant.BOTTOM), 2);
            }

            return true;
        }

        return false;
    }

    private boolean hasEnoughVerticalSpace(WorldGenLevel reader, BlockPos pos) {
        var cursor = pos.mutable();
        for (int i = 0; i < 3; i++) {
            cursor.move(Direction.DOWN);
            if (!reader.isEmptyBlock(cursor)) {
                return false;
            }
        }

        return true;
    }
}
