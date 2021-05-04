package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class SmallCactusBlock extends BushBlock {
    private static final VoxelShape SHAPE = makeCuboidShape(4, 0, 4, 12, 9, 12);

    public SmallCactusBlock() {
        super(Properties.from(Blocks.TALL_GRASS));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(Tags.Blocks.SAND);
    }
}
