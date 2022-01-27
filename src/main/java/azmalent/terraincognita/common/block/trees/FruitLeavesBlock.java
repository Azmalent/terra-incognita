package azmalent.terraincognita.common.block.trees;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;

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
    public void randomTick(@NotNull BlockState state, ServerLevel level, BlockPos pos, @NotNull Random random) {
        BlockPos down = pos.below();

        if (level.isEmptyBlock(down) && ForgeHooks.onCropsGrowPre(level, down, fruitState, random.nextInt(growthChance) == 0)) {
            level.setBlock(down, fruitState, 2);
            ForgeHooks.onCropsGrowPost(level, down, fruitState);
        }

        if (super.isRandomlyTicking(state)) {
            super.randomTick(state, level, pos, random);
        }
    }
}
