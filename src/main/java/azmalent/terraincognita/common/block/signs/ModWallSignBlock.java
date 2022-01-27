package azmalent.terraincognita.common.block.signs;

import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class ModWallSignBlock extends AbstractModSignBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, Block.box(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.EAST, Block.box(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D), Direction.WEST, Block.box(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)));

    public ModWallSignBlock(ModWoodType woodType) {
        super(woodType);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Nonnull
    public String getDescriptionId() {
        return woodType.SIGN.get().getDescriptionId();
    }

    @Nonnull
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).getMaterial().isSolid();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        LevelReader iworldreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();

        for(Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(iworldreader, blockpos)) {
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Nonnull
    public BlockState updateShape(BlockState stateIn, Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Nonnull
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nonnull
    @Override
    public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
        return this.woodType.SIGN.defaultBlockState().getDrops(builder);
    }
}
