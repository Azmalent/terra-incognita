package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.ParametersAreNullableByDefault;

@ParametersAreNullableByDefault
public class CactusFlowerBlock extends BushBlock {
    public CactusFlowerBlock() {
        super(Properties.from(Blocks.POPPY));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(Blocks.CACTUS) || state.getBlock() == ModBlocks.SMOOTH_CACTUS.getBlock();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos down = pos.down();
        return isValidGround(worldIn.getBlockState(down), worldIn, down);
    }
}
