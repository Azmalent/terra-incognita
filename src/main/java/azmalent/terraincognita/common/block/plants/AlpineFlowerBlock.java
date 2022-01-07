package azmalent.terraincognita.common.block.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.Tags;

public class AlpineFlowerBlock extends ModFlowerBlock {
    public AlpineFlowerBlock(ModFlowerBlock.StewEffect stewEffect) {
        super(stewEffect);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        return state.is(Tags.Blocks.GRAVEL) || super.mayPlaceOn(state, world, pos);
    }
}
