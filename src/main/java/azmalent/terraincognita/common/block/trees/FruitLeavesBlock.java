package azmalent.terraincognita.common.block.trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class FruitLeavesBlock extends LeavesBlock implements IGrowable {
    private final BlockState fruitState;
    private final int growthChance;

    public FruitLeavesBlock(BlockState fruitState, int growthChance) {
        super(Properties.from(Blocks.OAK_LEAVES));
        this.fruitState = fruitState;
        this.growthChance = growthChance;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockPos down = pos.down();

        if (worldIn.isAirBlock(down) && ForgeHooks.onCropsGrowPre(worldIn, down, fruitState, random.nextInt(growthChance) == 0)) {
            worldIn.setBlockState(down, fruitState, 2);
            ForgeHooks.onCropsGrowPost(worldIn, down, fruitState);
        }

        if (super.ticksRandomly(state)) {
            super.randomTick(state, worldIn, pos, random);
        }
    }

    //IGrowable implementation
    @Override
    @SuppressWarnings("deprecation")
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(PERSISTENT) && worldIn.getBlockState(pos.down()).isAir();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos.down(), fruitState, 2);
    }
}
