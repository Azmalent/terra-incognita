package azmalent.terraincognita.common.block.plants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

public class SaxifrageBlock extends AlpineFlowerBlock {
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);

    public SaxifrageBlock() {
        super(ModFlowerBlock.StewEffect.JUMP_BOOST);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(worldIn, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        return state.is(Tags.Blocks.STONE) || state.is(Tags.Blocks.COBBLESTONE) || super.mayPlaceOn(state, world, pos);
    }
}
