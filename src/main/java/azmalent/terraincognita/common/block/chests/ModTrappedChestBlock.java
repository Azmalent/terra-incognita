package azmalent.terraincognita.common.block.chests;

import azmalent.terraincognita.common.registry.ModTileEntities;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.tile.ModTrappedChestTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class ModTrappedChestBlock extends ModChestBlock {
    public ModTrappedChestBlock(ModWoodType woodType) {
        super(woodType, ModTileEntities.TRAPPED_CHEST::get);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockGetter world) {
        return new ModTrappedChestTileEntity();
    }

    @Nonnull
    @Override
    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(world, pos), 0, 15);
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return direction == Direction.UP ? state.getSignal(world, pos, direction) : 0;
    }
}
