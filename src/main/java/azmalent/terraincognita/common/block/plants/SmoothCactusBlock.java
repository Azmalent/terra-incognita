package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class SmoothCactusBlock extends CactusBlock {
    public SmoothCactusBlock() {
        super(Properties.from(Blocks.CACTUS));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (super.isValidPosition(state, worldIn, pos)) return true;
        return worldIn.getBlockState(pos.down()).isIn(Blocks.CACTUS) && !worldIn.getBlockState(pos.up()).getMaterial().isLiquid();
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return facing == Direction.UP && (plantable == Blocks.CACTUS || plantable == this);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, true) && random.nextInt(16) == 0) {
            worldIn.setBlockState(pos, Blocks.CACTUS.getDefaultState().with(AGE, state.get(AGE)));
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        //NO-OP
    }
}
