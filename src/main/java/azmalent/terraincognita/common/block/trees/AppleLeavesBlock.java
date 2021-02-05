package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class AppleLeavesBlock extends LeavesBlock {
    public AppleLeavesBlock() {
        super(Properties.from(Blocks.OAK_LEAVES));
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockPos down = pos.down();

        BlockState apple = ModBlocks.WoodTypes.APPLE.FRUIT.getBlock().getDefaultState();
        if (worldIn.isAirBlock(down) && ForgeHooks.onCropsGrowPre(worldIn, down, apple, random.nextInt(100) == 0)) {
            worldIn.setBlockState(down, apple, 2);
            ForgeHooks.onCropsGrowPost(worldIn, down, apple);
        }

        if (super.ticksRandomly(state)) {
            super.randomTick(state, worldIn, pos, random);
        }
    }
}
