package azmalent.terraincognita.common.block.woodset.chest;

import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.core.registry.ModBlockEntities;
import azmalent.terraincognita.common.blockentity.TITrappedChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class TITrappedChestBlock extends TIChestBlock {
    public TITrappedChestBlock(TIWoodType woodType) {
        super(woodType, ModBlockEntities.TRAPPED_CHEST::get);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TITrappedChestBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getSignal(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction direction) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(world, pos), 0, 15);
    }

    @Override
    public int getDirectSignal(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction direction) {
        return direction == Direction.UP ? state.getSignal(world, pos, direction) : 0;
    }
}
