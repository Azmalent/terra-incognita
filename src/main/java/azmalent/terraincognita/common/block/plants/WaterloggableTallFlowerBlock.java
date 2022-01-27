package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WaterloggableTallFlowerBlock extends TallFlowerBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterloggableTallFlowerBlock() {
        super(Properties.copy(Blocks.ROSE_BUSH));
        registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
            boolean waterlogged = fluidState.getType() == Fluids.WATER;
            state = state.setValue(WATERLOGGED, waterlogged);
        }

        return state;
    }

    @Override
    public void placeAt(LevelAccessor world, BlockPos pos, int flags) {
        FluidState fluidState = world.getFluidState(pos);
        boolean waterlogged = fluidState.getType() == Fluids.WATER;
        world.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, waterlogged), flags);
        world.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), flags);
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos up = pos.above();
            if (world.getFluidState(up).is(FluidTags.WATER) || world.getBlockState(up).is(Blocks.FROSTED_ICE)) {
                return false;
            }

            BlockPos down = pos.below();
            if (state.getBlock() == this) { //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return world.getBlockState(down).canSustainPlant(world, down, Direction.UP, this);
            }

            return mayPlaceOn(world.getBlockState(down), world, down);
        }

        return super.canSurvive(state, world, pos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return state.is(Blocks.CLAY) || super.mayPlaceOn(state, world, pos);
    }

    @Override
    public void playerWillDestroy(Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        if (!world.isClientSide) {
            if (player.isCreative()) {
                if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    BlockPos down = pos.below();
                    BlockState downState = world.getBlockState(down);
                    if (downState.getBlock() == this && downState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                        BlockState newState = downState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                        world.setBlock(down, newState, 35);
                        world.levelEvent(player, 2001, down, Block.getId(downState));
                    }
                }
            } else {
                dropResources(state, world, pos, null, player, player.getMainHandItem());
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public boolean canPlaceLiquid(@NotNull BlockGetter world, @NotNull BlockPos pos, BlockState state, @NotNull Fluid fluid) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && !state.getValue(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nonnull
    @Override
    public BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }
}
