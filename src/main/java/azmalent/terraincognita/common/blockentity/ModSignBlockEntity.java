package azmalent.terraincognita.common.blockentity;

import azmalent.terraincognita.common.registry.ModBlockEntities;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("ConstantConditions")
public class ModSignBlockEntity extends SignBlockEntity {
    public ModSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    @Nonnull
    public BlockEntityType<?> getType() {
        return ModBlockEntities.SIGN.get();
    }
}
