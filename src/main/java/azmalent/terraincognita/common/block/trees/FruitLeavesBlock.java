package azmalent.terraincognita.common.block.trees;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FruitLeavesBlock extends LeavesBlock {
    private final BlockState fruitState;
    private final int growthChance;

    public FruitLeavesBlock(BlockState fruitState, int growthChance) {
        super(Properties.copy(Blocks.OAK_LEAVES));
        this.fruitState = fruitState;
        this.growthChance = growthChance;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        BlockPos down = pos.below();

        if (worldIn.isEmptyBlock(down) && ForgeHooks.onCropsGrowPre(worldIn, down, fruitState, random.nextInt(growthChance) == 0)) {
            worldIn.setBlock(down, fruitState, 2);
            ForgeHooks.onCropsGrowPost(worldIn, down, fruitState);
        }

        if (super.isRandomlyTicking(state)) {
            super.randomTick(state, worldIn, pos, random);
        }
    }
}
