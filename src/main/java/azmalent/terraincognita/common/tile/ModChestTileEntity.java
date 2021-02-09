package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.init.ModTileEntities;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class ModChestTileEntity extends ChestTileEntity {
    protected ModChestTileEntity(TileEntityType type) {
        super(type);
    }

    public ModChestTileEntity() {
        this(ModTileEntities.CHEST.get());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
    }
}
