package azmalent.terraincognita.common.block.plant;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SedgeBlock extends SugarCaneBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SedgeBlock() {
        super(Block.Properties.of(Material.PLANT, MaterialColor.WOOD)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS));

        registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    @Nullable
    @SuppressWarnings("ConstantConditions")
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean waterlogged = fluidState.getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(WATERLOGGED, waterlogged);
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nonnull
    @Override
    public BlockState updateShape(BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader world, BlockPos pos) {
        BlockPos up = pos.above();
        if (world.getFluidState(up).is(FluidTags.WATER) || world.getBlockState(up).is(Blocks.FROSTED_ICE)) {
            return false;
        }

        BlockPos down = pos.below();
        BlockState soil = world.getBlockState(down);
        if (soil.getBlock() == this || soil.canSustainPlant(world, down, Direction.UP, this)) return true;

        if (soil.is(Blocks.GRASS_BLOCK) || soil.is(Blocks.DIRT) || soil.is(Blocks.COARSE_DIRT) || soil.is(Blocks.PODZOL) || soil.is(Blocks.SAND) || soil.is(Blocks.RED_SAND) || soil.is(ModBlocks.PEAT.get())) {
            if (state.getValue(WATERLOGGED)) {
                return true;
            }

            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState neighbor = world.getBlockState(down.relative(direction));
                FluidState fluidState = world.getFluidState(down.relative(direction));
                if (fluidState.is(FluidTags.WATER) || neighbor.is(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return false;
    }
}
