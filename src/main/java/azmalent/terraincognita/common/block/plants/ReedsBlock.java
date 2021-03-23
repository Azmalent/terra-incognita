package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReedsBlock extends SugarCaneBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ReedsBlock() {
        super(Block.Properties.create(Material.PLANTS, MaterialColor.WOOD)
                .doesNotBlockMovement()
                .tickRandomly()
                .zeroHardnessAndResistance()
                .sound(SoundType.PLANT));

        setDefaultState(stateContainer.getBaseState().with(AGE, 0).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        boolean waterlogged = fluidState.getFluid() == Fluids.WATER;
        return super.getStateForPlacement(context).with(WATERLOGGED, waterlogged);
    }

    @Nonnull
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Nonnull
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos up = pos.up();
        if (world.getFluidState(up).isTagged(FluidTags.WATER) || world.getBlockState(up).isIn(Blocks.FROSTED_ICE)) {
            return false;
        }

        BlockPos down = pos.down();
        BlockState soil = world.getBlockState(down);
        if (soil.getBlock() == this || soil.canSustainPlant(world, down, Direction.UP, this)) return true;

        if (soil.isIn(Blocks.GRASS_BLOCK) || soil.isIn(Blocks.DIRT) || soil.isIn(Blocks.COARSE_DIRT) || soil.isIn(Blocks.PODZOL) || soil.isIn(Blocks.SAND) || soil.isIn(Blocks.RED_SAND) || TIConfig.Misc.peat.get() && soil.isIn(ModBlocks.PEAT.getBlock())) {
            if (state.get(WATERLOGGED)) {
                return true;
            }

            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState neighbor = world.getBlockState(down.offset(direction));
                FluidState fluidState = world.getFluidState(down.offset(direction));
                if (fluidState.isTagged(FluidTags.WATER) || neighbor.isIn(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return false;
    }
}
