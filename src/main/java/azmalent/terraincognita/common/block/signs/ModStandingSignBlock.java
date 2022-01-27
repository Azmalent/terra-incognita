package azmalent.terraincognita.common.block.signs;

import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class ModStandingSignBlock extends AbstractModSignBlock {
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    public ModStandingSignBlock(ModWoodType woodType) {
        super(woodType);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0).setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ROTATION);
    }

    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).getMaterial().isSolid();
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(ROTATION, Mth.floor((double) ((180.0F + context.getRotation()) * 16.0F / 360.0F) + 0.5D) & 15).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Nonnull
    public BlockState updateShape(@NotNull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        return facing == Direction.DOWN && !this.canSurvive(stateIn, level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Nonnull
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(ROTATION, rot.rotate(state.getValue(ROTATION), 16));
    }

    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.setValue(ROTATION, mirrorIn.mirror(state.getValue(ROTATION), 16));
    }
}
