package azmalent.terraincognita.common.block.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

@ParametersAreNonnullByDefault
public class SmoothCactusBlock extends CactusBlock {
    public SmoothCactusBlock() {
        super(Properties.copy(Blocks.CACTUS));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (super.canSurvive(state, worldIn, pos)) return true;
        return worldIn.getBlockState(pos.below()).is(Blocks.CACTUS) && !worldIn.getBlockState(pos.above()).getMaterial().isLiquid();
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return facing == Direction.UP && (plantable == Blocks.CACTUS || plantable == this);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, true) && random.nextInt(16) == 0) {
            worldIn.setBlockAndUpdate(pos, Blocks.CACTUS.defaultBlockState().setValue(AGE, state.getValue(AGE)));
        }
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        //NO-OP
    }
}
