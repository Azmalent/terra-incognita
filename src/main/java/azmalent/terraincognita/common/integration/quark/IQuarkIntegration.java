package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.IModIntegration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

public interface IQuarkIntegration extends IModIntegration {
    boolean matchesItemSearch(ItemStack stack);

    boolean canEditSign(ItemStack heldStack);

    boolean canLanternConnect(BlockState state, LevelReader worldIn, BlockPos pos);

    boolean canBurnVineTips();
}
