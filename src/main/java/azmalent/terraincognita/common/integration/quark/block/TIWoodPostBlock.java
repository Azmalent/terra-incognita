package azmalent.terraincognita.common.integration.quark.block;

import net.minecraft.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;

//Copied from Quark WoodPostBlock with minor edits
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.Lantern;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class TIWoodPostBlock extends Block implements SimpleWaterloggedBlock {
    private static final VoxelShape SHAPE_X = Block.box(0F, 6F, 6F, 16F, 10F, 10F);
    private static final VoxelShape SHAPE_Y = Block.box(6F, 0F, 6F, 10F, 16F, 10F);
    private static final VoxelShape SHAPE_Z = Block.box(6F, 6F, 0F, 10F, 10F, 16F);

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final BooleanProperty[] CHAINED = new BooleanProperty[] {
        BooleanProperty.create("chain_down"),
        BooleanProperty.create("chain_up"),
        BooleanProperty.create("chain_north"),
        BooleanProperty.create("chain_south"),
        BooleanProperty.create("chain_west"),
        BooleanProperty.create("chain_east")
    };

	public Block strippedBlock = null;

    public TIWoodPostBlock(MaterialColor color) {
        super(Block.Properties.of(Material.WOOD, color).strength(2.0F, 3.0F).sound(SoundType.WOOD));

        BlockState state = getStateDefinition().any().setValue(WATERLOGGED, false).setValue(AXIS, Direction.Axis.Y);
        for(BooleanProperty prop : CHAINED) {
            state = state.setValue(prop, false);
        }

        registerDefaultState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS);
        for(BooleanProperty prop : CHAINED) {
            builder.add(prop);
        }
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch(state.getValue(AXIS)) {
            case X: return SHAPE_X;
            case Y: return SHAPE_Y;
            default: return SHAPE_Z;
        }
    }

    @Override
   	@SuppressWarnings({ "rawtypes", "unchecked" })
   	public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolType toolType) {
   		if(strippedBlock == null || toolType != ToolType.AXE) {
   			return super.getToolModifiedState(state, world, pos, player, stack, toolType);
  		}
   		
   		BlockState newState = strippedBlock.defaultBlockState();
   		for(Property p : state.getProperties()) {
   			newState = newState.setValue(p, state.getValue(p));
   		}
   		
   		return newState;
   	}

    @Override
    public boolean propagatesSkylightDown(BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    private BlockState getState(LevelAccessor world, BlockPos pos, Direction.Axis axis) {
        BlockState state = defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER).setValue(AXIS, axis);

        for(Direction d : Direction.values()) {
            if(d.getAxis() == axis) {
                continue;
            }

            BlockState sideState = world.getBlockState(pos.relative(d));
            if((sideState.getBlock() instanceof ChainBlock && sideState.getValue(BlockStateProperties.AXIS) == d.getAxis())
                    || (d == Direction.DOWN && sideState.getBlock() instanceof Lantern && sideState.getValue(Lantern.HANGING))) {
                BooleanProperty prop = CHAINED[d.ordinal()];
                state = state.setValue(prop, true);
            }
        }

        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(context.getLevel(), context.getClickedPos(), context.getClickedFace().getAxis());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);

        BlockState newState = getState(worldIn, pos, state.getValue(AXIS));
        if(!newState.equals(state)) {
            worldIn.setBlockAndUpdate(pos, newState);
        }
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}
