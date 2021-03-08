package azmalent.terraincognita.common.block.chests;

import azmalent.terraincognita.common.registry.ModTileEntities;
import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.tile.ModTrappedChestTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class ModTrappedChestBlock extends ModChestBlock {
    public ModTrappedChestBlock(ModWoodType woodType, MaterialColor color) {
        super(woodType, color, ModTileEntities.TRAPPED_CHEST::get);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return new ModTrappedChestTileEntity();
    }

    @Nonnull
    @Override
    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return MathHelper.clamp(ChestTileEntity.getPlayersUsing(world, pos), 0, 15);
    }

    @Override
    public int getStrongPower(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return direction == Direction.UP ? state.getWeakPower(world, pos, direction) : 0;
    }
}
