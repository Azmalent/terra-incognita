package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.IModIntegration;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public interface IQuarkIntegration extends IModIntegration {
    boolean matchesItemSearch(ItemStack stack);

    boolean canEditSign(ItemStack heldStack);

    boolean canLanternConnect(BlockState state, IWorldReader worldIn, BlockPos pos);

    boolean canBurnVineTips();
}
