package azmalent.terraincognita.common.block.plant;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;

public class SmallLilyPadBlock extends TILilyPadBlock {
    public static final IntegerProperty LILY_PADS = IntegerProperty.create("lily_pads", 1, 4);

    private static final VoxelShape SINGLE_LILYPAD_SHAPE = box(4, 0, 4, 12, 1.5, 12);
    private static final VoxelShape TWO_LILYPAD_SHAPE = box(2, 0, 2, 14, 1.5, 14);
    private static final VoxelShape MULTIPLE_LILYPAD_SHAPE = box(1, 0, 1, 15, 1.5, 15);

    public SmallLilyPadBlock() {
        super(Properties.copy(Blocks.LILY_PAD));
        registerDefaultState(getStateDefinition().any().setValue(LILY_PADS, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LILY_PADS);
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        int n = state.getValue(LILY_PADS);
       	return n == 1 ? SINGLE_LILYPAD_SHAPE : (n == 2 ? TWO_LILYPAD_SHAPE : MULTIPLE_LILYPAD_SHAPE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.getBlock() == this) {
            return blockstate.setValue(LILY_PADS, Math.min(4, blockstate.getValue(LILY_PADS) + 1));
        }

        return super.getStateForPlacement(context);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canBeReplaced(@NotNull BlockState state, BlockPlaceContext useContext) {
        if (useContext.getItemInHand().getItem() == this.asItem() && state.getValue(LILY_PADS) < 4) {
            return true;
        }

        return super.canBeReplaced(state, useContext);
    }
}
