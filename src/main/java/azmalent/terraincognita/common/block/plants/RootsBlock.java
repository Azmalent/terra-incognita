package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class RootsBlock extends HangingPlantBlock implements IGrowable {
	public static final VoxelShape SHAPE = makeCuboidShape(3, 6, 3, 13, 16, 13);

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos blockPos, ISelectionContext context) {
        Vector3d offset = state.getOffset(reader, blockPos);
        return SHAPE.withOffset(offset.x, offset.y, offset.z);
    }

    @Override
    protected boolean isValidGround(BlockState state, BlockState ground, IBlockReader worldIn, BlockPos groundPos) {
        return ground.isIn(Tags.Blocks.DIRT);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
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
    public void grow(@Nonnull ServerWorld world, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        BlockState roots = this.getDefaultState();

        for(int i = 0; i < 4; i++) {
            int x = random.nextInt(4) - random.nextInt(4);
            int y = random.nextInt(2) - random.nextInt(2);
            int z = random.nextInt(4) - random.nextInt(4);
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.add(x, y, z);
            if (world.isAirBlock(nextPos) && roots.isValidPosition(world, nextPos)) {
                world.setBlockState(nextPos, roots);
            }
        }
    }
}
