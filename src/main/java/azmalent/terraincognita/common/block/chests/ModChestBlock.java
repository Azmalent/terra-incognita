package azmalent.terraincognita.common.block.chests;

import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.registry.ModBlockEntities;
import azmalent.terraincognita.common.tile.ModChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModChestBlock extends ChestBlock {
    public final ModWoodType woodType;
    public final Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityType;

    protected ModChestBlock(ModWoodType woodType, Supplier<BlockEntityType<? extends ChestBlockEntity>> typeSupplier) {
        super(Properties.of(Material.WOOD, woodType.woodColor).strength(2.5F).sound(SoundType.WOOD), typeSupplier);
        this.woodType = woodType;
        this.tileEntityType = typeSupplier;
    }

    public ModChestBlock(ModWoodType woodType) {
        this(woodType, ModBlockEntities.CHEST::get);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ModChestBlockEntity(pos, state);
    }
}
