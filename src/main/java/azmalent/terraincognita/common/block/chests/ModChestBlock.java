package azmalent.terraincognita.common.block.chests;

import azmalent.terraincognita.common.registry.ModTileEntities;
import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.tile.ModChestTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class ModChestBlock extends ChestBlock {
    public final ModWoodType woodType;
    public final Supplier<TileEntityType<? extends ChestTileEntity>> tileEntityType;

    protected ModChestBlock(ModWoodType woodType, MaterialColor color, Supplier<TileEntityType<? extends ChestTileEntity>> typeSupplier) {
        super(Properties.create(Material.WOOD, color).hardnessAndResistance(2.5F).sound(SoundType.WOOD), typeSupplier);
        this.woodType = woodType;
        this.tileEntityType = typeSupplier;
    }

    public ModChestBlock(ModWoodType woodType, MaterialColor color) {
        this(woodType, color, ModTileEntities.CHEST::get);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return new ModChestTileEntity();
    }
}
