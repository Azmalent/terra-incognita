package azmalent.terraincognita.common.block.fruit;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class FruitLeavesBlock extends LeavesBlock implements BonemealableBlock {
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

    //IGrowable implementation
    @Override
    @ParametersAreNonnullByDefault
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        return !state.getValue(PERSISTENT) && level.getBlockState(pos.below()).isAir();
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        level.setBlock(pos.below(), fruitState, 2);
    }
}
