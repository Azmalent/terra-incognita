package azmalent.terraincognita.common.block.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class SmoothCactusBlock extends CactusBlock {
    public SmoothCactusBlock() {
        super(Properties.copy(Blocks.CACTUS));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (super.canSurvive(state, level, pos)) return true;
        return level.getBlockState(pos.below()).is(Blocks.CACTUS) && !level.getBlockState(pos.above()).getMaterial().isLiquid();
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return facing == Direction.UP && (plantable == Blocks.CACTUS || plantable == this);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if(ForgeHooks.onCropsGrowPre(level, pos, state, true) && random.nextInt(16) == 0) {
            level.setBlockAndUpdate(pos, Blocks.CACTUS.defaultBlockState().setValue(AGE, state.getValue(AGE)));
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn) {
        //NO-OP
    }
}
