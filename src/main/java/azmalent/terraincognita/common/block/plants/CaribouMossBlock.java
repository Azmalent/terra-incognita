package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.world.feature.CaribouMossFeature;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("deprecation")
public class CaribouMossBlock extends BushBlock implements IGrowable {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public CaribouMossBlock() {
        super(Properties.from(Blocks.GRASS));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(world, pos);
        return SHAPE.withOffset(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isSolidSide(worldIn, pos.down(), Direction.UP);
    }

    //IGrowable implementation
    @Override
    public boolean canGrow(@Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void grow(@Nonnull ServerWorld worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        for(int i = 0; i < 3; i++) {
            int x = rand.nextInt(3) - rand.nextInt(3);
            int y = rand.nextInt(2) - rand.nextInt(2);
            int z = rand.nextInt(3) - rand.nextInt(3);
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.add(x, y, z);
            if (worldIn.isAirBlock(nextPos) || worldIn.getBlockState(nextPos).isIn(Blocks.SNOW)) {
                CaribouMossFeature.tryPlaceMoss(worldIn, pos.add(x, y, z), rand);
            }
        }
    }
}
