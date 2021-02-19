package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.init.ModStewEffect;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

public class SaxifrageBlock extends AlpineFlowerBlock {
    public SaxifrageBlock() {
        super(ModStewEffect.JUMP_BOOST);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(Tags.Blocks.STONE) || state.isIn(Tags.Blocks.COBBLESTONE) || super.isValidGround(state, world, pos);
    }
}
