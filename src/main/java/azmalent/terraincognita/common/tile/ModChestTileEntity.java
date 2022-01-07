package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.registry.ModTileEntities;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;

public class ModChestTileEntity extends ChestBlockEntity {
    protected ModChestTileEntity(BlockEntityType type) {
        super(type);
    }

    public ModChestTileEntity() {
        this(ModTileEntities.CHEST.get());
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
    }
}
