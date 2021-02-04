package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class RootsBlock extends HangingPlantBlock implements IGrowable {
	public static final VoxelShape SHAPE = makeCuboidShape(3, 6, 3, 13, 16, 13);

    public RootsBlock() {
        super(Block.Properties.create(Material.PLANTS, MaterialColor.BROWN).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos blockPos, ISelectionContext context) {
    	return SHAPE;
    }
    

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(Tags.Blocks.DIRT);
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState blockState, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockState roots = this.getDefaultState();

        for(int i = 0; i < 4; i++) {
            int x = random.nextInt(4) - 2;
            int y = random.nextInt(2) - 1;
            int z = random.nextInt(4) - 2;
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.add(x, y, z);
            if (world.isAirBlock(nextPos) && roots.isValidPosition(world, nextPos)) {
                world.setBlockState(nextPos, roots);
            }
        }
    }
}
