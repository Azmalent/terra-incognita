package azmalent.terraincognita.common.block.signs;

import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class ModWallSignBlock extends AbstractModSignBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.EAST, Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D), Direction.WEST, Block.makeCuboidShape(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)));

    public ModWallSignBlock(MaterialColor color, ModWoodType woodType) {
        super(color, woodType);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING);
    }

    @Nonnull
    public String getTranslationKey() {
        return woodType.SIGN.getBlock().getTranslationKey();
    }

    @Nonnull
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(FACING));
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.offset(state.get(FACING).getOpposite())).getMaterial().isSolid();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = this.getDefaultState();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        Direction[] directions = context.getNearestLookingDirections();

        for(Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.with(FACING, direction1);
                if (blockstate.isValidPosition(iworldreader, blockpos)) {
                    return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Nonnull
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        return facing.getOpposite() == stateIn.get(FACING) && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nonnull
    @Override
    public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
        return this.woodType.SIGN.getBlock().getDrops(state, builder);
    }
}
