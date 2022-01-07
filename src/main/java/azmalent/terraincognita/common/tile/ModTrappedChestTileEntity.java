package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.registry.ModTileEntities;

public class ModTrappedChestTileEntity extends ModChestTileEntity {
    public ModTrappedChestTileEntity() {
        super(ModTileEntities.TRAPPED_CHEST.get());
    }

    @SuppressWarnings("ConstantConditions")
    protected void signalOpenCount() {
        super.signalOpenCount();
        this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
    }
}
