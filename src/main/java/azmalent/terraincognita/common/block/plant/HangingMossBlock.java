package azmalent.terraincognita.common.block.plant;

import azmalent.terraincognita.common.ModBlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@SuppressWarnings("deprecation")
public class HangingMossBlock extends HangingPlantBlock implements BonemealableBlock {
    public enum Variant implements StringRepresentable {
        SINGLE("single"), TOP("top"), BOTTOM("bottom");

        private final String name;

        Variant(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static final VoxelShape TOP_SHAPE = box(3, 0, 3, 13, 16, 13);
    public static final VoxelShape BOTTOM_SHAPE = box(3, 6, 3, 13, 16, 13);

    public static final EnumProperty<Variant> VARIANT = EnumProperty.create("variant", Variant.class);

    public HangingMossBlock() {
        super();
        registerDefaultState(getStateDefinition().any().setValue(VARIANT, Variant.SINGLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(VARIANT) == Variant.TOP ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, BlockState ground, BlockGetter level, BlockPos groundPos) {
        if (ground.getBlock() == this) {
            return ground.getValue(VARIANT) != Variant.BOTTOM;
        }

        return ground.isFaceSturdy(level, groundPos, Direction.DOWN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState ground = context.getLevel().getBlockState(context.getClickedPos().above());
        if (ground.getBlock() == this && ground.getValue(VARIANT) != Variant.BOTTOM) {
            return defaultBlockState().setValue(VARIANT, Variant.BOTTOM);
        }

        return super.getStateForPlacement(context);
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN) {
            if (stateIn.getValue(VARIANT) == Variant.SINGLE && facingState.getBlock() == this) {
                return super.updateShape(stateIn.setValue(VARIANT, Variant.TOP), facing, facingState, level, currentPos, facingPos);
            }
            else if (stateIn.getValue(VARIANT) == Variant.TOP && facingState.getBlock() != this) {
                return super.updateShape(defaultBlockState(), facing, facingState, level, currentPos, facingPos);
            }
        }
        else if (facing == Direction.UP) {
            if (facingState.getBlock() == this && facingState.getValue(VARIANT) != Variant.BOTTOM) {
                return stateIn.setValue(VARIANT, Variant.BOTTOM);
            }

            if (isValidGround(stateIn, facingState, level, facingPos)) {
                return stateIn.setValue(VARIANT, Variant.SINGLE);
            }
        }

        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull BlockState blockState, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@Nonnull Level world, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel world, @Nonnull Random random, @Nonnull BlockPos pos, BlockState state) {
        int numAttempts = 4;

        if (state.getValue(VARIANT) == Variant.SINGLE && world.isEmptyBlock(pos.below()) && random.nextBoolean()) {
            world.setBlockAndUpdate(pos, state.setValue(VARIANT, Variant.TOP));
            world.setBlockAndUpdate(pos.below(), state.setValue(VARIANT, Variant.BOTTOM));
            numAttempts = 2;
        }

        for (int i = 0; i < numAttempts; i++) {
            BlockState moss = this.defaultBlockState();

            int x = random.nextInt(4) - random.nextInt(4);
            int y = random.nextInt(2) - random.nextInt(2);
            int z = random.nextInt(4) - random.nextInt(4);
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.offset(x, y, z);
            if (world.isEmptyBlock(nextPos) && moss.canSurvive(world, nextPos)) {
                world.setBlockAndUpdate(nextPos, moss);
            }
        }
    }
}
