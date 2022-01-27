package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ModChestBlockEntity extends ChestBlockEntity {
    protected ModChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ModChestBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.CHEST.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
    }
}
