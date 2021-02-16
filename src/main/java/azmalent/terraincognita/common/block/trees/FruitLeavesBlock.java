package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModWoodTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class FruitLeavesBlock extends LeavesBlock {
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
}
