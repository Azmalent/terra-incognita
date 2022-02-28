package azmalent.terraincognita.common.block.plant;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class SaxifrageBlock extends AlpineFlowerBlock {
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);

    public SaxifrageBlock() {
        super(ModFlowerBlock.StewEffect.JUMP_BOOST);
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected boolean mayPlaceOn(@NotNull BlockState state, BlockGetter world, BlockPos pos) {
        return state.is(Tags.Blocks.STONE) || state.is(Tags.Blocks.COBBLESTONE) || super.mayPlaceOn(state, world, pos);
    }
}
