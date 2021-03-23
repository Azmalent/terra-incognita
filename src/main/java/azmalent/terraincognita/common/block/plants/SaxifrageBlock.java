package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

public class SaxifrageBlock extends AlpineFlowerBlock {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);

    public SaxifrageBlock() {
        super(ModFlowerBlock.StewEffect.JUMP_BOOST);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(offset.x, offset.y, offset.z);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(Tags.Blocks.STONE) || state.isIn(Tags.Blocks.COBBLESTONE) || super.isValidGround(state, world, pos);
    }
}
