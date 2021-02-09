package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.init.ModTileEntities;

public class ModTrappedChestTileEntity extends ModChestTileEntity {
    public ModTrappedChestTileEntity() {
        super(ModTileEntities.TRAPPED_CHEST.get());
    }

    @SuppressWarnings("ConstantConditions")
    protected void onOpenOrClose() {
        super.onOpenOrClose();
        this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockState().getBlock());
    }
}
