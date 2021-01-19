package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

public class RockfoilBlock extends AlpineFlowerBlock {
    public RockfoilBlock() {
        super(Effects.JUMP_BOOST, 120, Properties.from(Blocks.DANDELION));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(Tags.Blocks.STONE) || super.isValidGround(state, world, pos);
    }
}
