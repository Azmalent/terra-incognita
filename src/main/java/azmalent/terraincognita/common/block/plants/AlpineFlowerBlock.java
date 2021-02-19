package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.init.ModStewEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

public class AlpineFlowerBlock extends FlowerBlock {
    public AlpineFlowerBlock(ModStewEffect stewEffect) {
        super(stewEffect.effect, stewEffect.duration, Block.Properties.from(Blocks.POPPY));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(Tags.Blocks.GRAVEL) || super.isValidGround(state, world, pos);
    }
}
