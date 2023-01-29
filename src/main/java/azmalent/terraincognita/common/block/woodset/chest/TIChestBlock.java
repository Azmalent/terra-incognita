package azmalent.terraincognita.common.block.woodset.chest;

import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.common.registry.ModBlockEntities;
import azmalent.terraincognita.common.blockentity.TIChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TIChestBlock extends ChestBlock {
    public final TIWoodType woodType;
    public final Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityType;

    protected TIChestBlock(TIWoodType woodType, Supplier<BlockEntityType<? extends ChestBlockEntity>> typeSupplier) {
        super(Properties.of(Material.WOOD, woodType.woodColor).strength(2.5F).sound(SoundType.WOOD), typeSupplier);
        this.woodType = woodType;
        this.tileEntityType = typeSupplier;
    }

    public TIChestBlock(TIWoodType woodType) {
        this(woodType, ModBlockEntities.CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TIChestBlockEntity(pos, state);
    }
}
