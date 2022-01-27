package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;

@ParametersAreNullableByDefault
public class CactusFlowerBlock extends BushBlock {
    public CactusFlowerBlock() {
        super(Properties.copy(Blocks.POPPY));
    }

    @Override
    @ParametersAreNonnullByDefault
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.CACTUS) || state.getBlock() == ModBlocks.SMOOTH_CACTUS.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos down = pos.below();
        return mayPlaceOn(level.getBlockState(down), level, down);
    }
}
