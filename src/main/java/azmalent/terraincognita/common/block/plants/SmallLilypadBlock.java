package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class SmallLilypadBlock extends LilyPadBlock {
    public static final IntegerProperty LILYPADS = IntegerProperty.create("lilypads", 1, 4);

    private static final VoxelShape SINGLE_LILYPAD_SHAPE = makeCuboidShape(3, 0, 3, 12, 1.5, 12);
    private static final VoxelShape MULTIPLE_LILYPAD_SHAPE = makeCuboidShape(1, 0, 1, 14, 1.5, 14);

    public SmallLilypadBlock() {
        super(Properties.from(Blocks.LILY_PAD));
        setDefaultState(getStateContainer().getBaseState().with(LILYPADS, 1));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LILYPADS);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos blockPos, ISelectionContext context) {
        return state.get(LILYPADS) == 1 ? SINGLE_LILYPAD_SHAPE : MULTIPLE_LILYPAD_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        if (blockstate.getBlock() == this) {
            return blockstate.with(LILYPADS, Math.min(4, blockstate.get(LILYPADS) + 1));
        }

        return super.getStateForPlacement(context);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        if (useContext.getItem().getItem() == this.asItem() && state.get(LILYPADS) < 4) {
            return true;
        }

        return super.isReplaceable(state, useContext);
    }
}
