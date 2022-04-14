package azmalent.terraincognita.common.blockentity;

import azmalent.terraincognita.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

public class TITrappedChestBlockEntity extends TIChestBlockEntity {
    public TITrappedChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAPPED_CHEST.get(), pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("ConstantConditions")
    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int i1, int i2) {
        super.signalOpenCount(level, pos, state, i1, i2);
        this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
    }
}
