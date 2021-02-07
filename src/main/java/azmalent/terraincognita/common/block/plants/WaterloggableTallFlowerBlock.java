package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class WaterloggableTallFlowerBlock extends TallFlowerBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterloggableTallFlowerBlock() {
        super(Properties.from(Blocks.ROSE_BUSH));
        setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            FluidState fluidState = context.getWorld().getFluidState(context.getPos());
            boolean waterlogged = fluidState.getFluid() == Fluids.WATER;
            state = state.with(WATERLOGGED, waterlogged);
        }

        return state;
    }

    @Override
    public void placeAt(IWorld world, BlockPos pos, int flags) {
        FluidState fluidState = world.getFluidState(pos);
        boolean waterlogged = fluidState.getFluid() == Fluids.WATER;
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, waterlogged), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), flags);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos up = pos.up();
            if (world.getFluidState(up).isTagged(FluidTags.WATER) || world.getBlockState(up).isIn(Blocks.FROSTED_ICE)) {
                return false;
            }

            BlockPos down = pos.down();
            if (state.getBlock() == this) { //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return world.getBlockState(down).canSustainPlant(world, down, Direction.UP, this);
            }

            return isValidGround(world.getBlockState(down), world, down);
        }

        return super.isValidPosition(state, world, pos);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(Blocks.CLAY) || super.isValidGround(state, world, pos);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isRemote) {
            if (player.isCreative()) {
                if (state.get(HALF) == DoubleBlockHalf.UPPER) {
                    BlockPos down = pos.down();
                    BlockState downState = world.getBlockState(down);
                    if (downState.getBlock() == this && downState.get(HALF) == DoubleBlockHalf.LOWER) {
                        BlockState newState = downState.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                        world.setBlockState(down, newState, 35);
                        world.playEvent(player, 2001, down, Block.getStateId(downState));
                    }
                }
            } else {
                spawnDrops(state, world, pos, null, player, player.getHeldItemMainhand());
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public boolean canContainFluid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(HALF) == DoubleBlockHalf.LOWER && !state.get(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
