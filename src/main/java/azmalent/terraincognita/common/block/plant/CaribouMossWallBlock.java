package azmalent.terraincognita.common.block.plant;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.world.feature.CaribouMossFeature;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("deprecation")
public class CaribouMossWallBlock extends Block implements BonemealableBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0D, 4.0D, 5.0D, 16.0D, 12.0D, 16.0D), Direction.SOUTH, Block.box(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 11.0D), Direction.WEST, Block.box(5.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D), Direction.EAST, Block.box(0.0D, 4.0D, 0.0D, 11.0D, 12.0D, 16.0D)));

    public CaribouMossWallBlock() {
        super(Properties.copy(Blocks.GRASS));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nonnull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Nonnull
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos offset = pos.relative(direction.getOpposite());
        return level.getBlockState(offset).isFaceSturdy(level, offset, direction);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                BlockState state = defaultBlockState().setValue(FACING, direction.getOpposite());
                if (state.canSurvive(world, pos)) {
                    return state;
                }
            }
        }

        return null;
    }

    @Nonnull
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
        return ModBlocks.CARIBOU_MOSS.defaultBlockState().getDrops(builder);
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return ModBlocks.CARIBOU_MOSS.get().getDescriptionId();
    }

    //IGrowable implementation
    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@Nonnull Level level, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel level, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        for(int i = 0; i < 3; i++) {
            int x = rand.nextInt(3) - rand.nextInt(3);
            int y = rand.nextInt(2) - rand.nextInt(2);
            int z = rand.nextInt(3) - rand.nextInt(3);
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.offset(x, y, z);
            if (level.isEmptyBlock(nextPos) || level.getBlockState(nextPos).is(Blocks.SNOW)) {
                CaribouMossFeature.tryPlaceMoss(level, pos.offset(x, y, z), rand);
            }
        }
    }
}
