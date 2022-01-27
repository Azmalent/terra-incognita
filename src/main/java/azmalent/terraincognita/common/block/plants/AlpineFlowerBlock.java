package azmalent.terraincognita.common.block.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class AlpineFlowerBlock extends ModFlowerBlock {
    public AlpineFlowerBlock(ModFlowerBlock.StewEffect stewEffect) {
        super(stewEffect);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return state.is(Tags.Blocks.GRAVEL) || super.mayPlaceOn(state, world, pos);
    }
}
