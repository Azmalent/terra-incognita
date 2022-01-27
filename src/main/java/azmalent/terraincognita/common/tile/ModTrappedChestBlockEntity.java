package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ModTrappedChestBlockEntity extends ModChestBlockEntity {
    public ModTrappedChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAPPED_CHEST.get(), pos, state);
    }

    @SuppressWarnings("ConstantConditions")
    protected void signalOpenCount() {
        super.signalOpenCount();
        this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
    }
}
