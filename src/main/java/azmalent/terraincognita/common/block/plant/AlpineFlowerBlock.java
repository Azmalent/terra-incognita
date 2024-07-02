package azmalent.terraincognita.common.block.plant;

import azmalent.terraincognita.core.ModBlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class AlpineFlowerBlock extends TIFlowerBlock {
    public AlpineFlowerBlock(TIFlowerBlock.StewEffect stewEffect) {
        super(stewEffect);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return state.is(ModBlockTags.ALPINE_FLOWERS_PLANTABLE_ON) || super.mayPlaceOn(state, world, pos);
    }
}
