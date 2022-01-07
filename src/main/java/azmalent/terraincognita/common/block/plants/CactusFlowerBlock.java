package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;

import javax.annotation.ParametersAreNullableByDefault;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

@ParametersAreNullableByDefault
public class CactusFlowerBlock extends BushBlock {
    public CactusFlowerBlock() {
        super(Properties.copy(Blocks.POPPY));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(Blocks.CACTUS) || state.getBlock() == ModBlocks.SMOOTH_CACTUS.getBlock();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos down = pos.below();
        return mayPlaceOn(worldIn.getBlockState(down), worldIn, down);
    }
}
