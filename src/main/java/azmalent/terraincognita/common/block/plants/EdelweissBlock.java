package azmalent.terraincognita.common.block.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

public class EdelweissBlock extends FlowerBlock {
    public EdelweissBlock() {
        super(Effects.HASTE, 200, Properties.from(Blocks.AZURE_BLUET));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(Tags.Blocks.GRAVEL) || super.isValidGround(state, world, pos);
    }
}
