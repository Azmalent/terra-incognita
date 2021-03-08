package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.data.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class HangingMossBlock extends HangingPlantBlock implements IGrowable {
    public enum Variant implements IStringSerializable {
        SINGLE("single"), TOP("top"), BOTTOM("bottom");

        private String name;

        Variant(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getString() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static final VoxelShape TOP_SHAPE = makeCuboidShape(3, 0, 3, 13, 16, 13);
    public static final VoxelShape BOTTOM_SHAPE = makeCuboidShape(3, 6, 3, 13, 16, 13);

    public static final EnumProperty<Variant> VARIANT = EnumProperty.create("variant", Variant.class);

    public HangingMossBlock() {
        super();
        setDefaultState(getStateContainer().getBaseState().with(VARIANT, Variant.SINGLE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(VARIANT);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return state.get(VARIANT) == Variant.TOP ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, BlockState ground, IBlockReader worldIn, BlockPos groundPos) {
        if (ground.getBlock() == this) {
            return ground.get(VARIANT) != Variant.BOTTOM;
        }

        return ground.isSolidSide(worldIn, groundPos, Direction.DOWN) && ground.isIn(ModBlockTags.MOSS_PLACEABLE_ON);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState ground = context.getWorld().getBlockState(context.getPos().up());
        if (ground.getBlock() == this && ground.get(VARIANT) != Variant.BOTTOM) {
            return getDefaultState().with(VARIANT, Variant.BOTTOM);
        }

        return super.getStateForPlacement(context);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN) {
            if (stateIn.get(VARIANT) == Variant.SINGLE && facingState.getBlock() == this) {
                return super.updatePostPlacement(stateIn.with(VARIANT, Variant.TOP), facing, facingState, worldIn, currentPos, facingPos);
            }
            else if (stateIn.get(VARIANT) == Variant.TOP && facingState.getBlock() != this) {
                return super.updatePostPlacement(getDefaultState(), facing, facingState, worldIn, currentPos, facingPos);
            }
        }
        else if (facing == Direction.UP) {
            if (facingState.getBlock() == this && facingState.get(VARIANT) != Variant.BOTTOM) {
                return stateIn.with(VARIANT, Variant.BOTTOM);
            }

            if (isValidGround(stateIn, facingState, worldIn, facingPos)) {
                return stateIn.with(VARIANT, Variant.SINGLE);
            }
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canGrow(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState blockState, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World world, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void grow(@Nonnull ServerWorld world, @Nonnull Random random, @Nonnull BlockPos pos, BlockState state) {
        int numAttempts = 4;

        if (state.get(VARIANT) == Variant.SINGLE && world.isAirBlock(pos.down()) && random.nextBoolean()) {
            world.setBlockState(pos, state.with(VARIANT, Variant.TOP));
            world.setBlockState(pos.down(), state.with(VARIANT, Variant.BOTTOM));
            numAttempts = 2;
        }

        for (int i = 0; i < numAttempts; i++) {
            BlockState moss = this.getDefaultState();

            int x = random.nextInt(4) - 2;
            int y = random.nextInt(2) - 1;
            int z = random.nextInt(4) - 2;
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.add(x, y, z);
            if (world.isAirBlock(nextPos) && moss.isValidPosition(world, nextPos)) {
                world.setBlockState(nextPos, moss);
            }
        }
    }
}
