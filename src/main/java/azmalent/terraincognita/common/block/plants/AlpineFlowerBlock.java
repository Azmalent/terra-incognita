package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

public class AlpineFlowerBlock extends ModFlowerBlock {
    public AlpineFlowerBlock(ModFlowerBlock.StewEffect stewEffect) {
        super(stewEffect);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(Tags.Blocks.GRAVEL) || super.isValidGround(state, world, pos);
    }
}
