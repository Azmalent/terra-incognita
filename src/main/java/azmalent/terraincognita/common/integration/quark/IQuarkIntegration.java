package azmalent.terraincognita.common.integration.quark;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IQuarkIntegration {
    void register(IEventBus bus);

    boolean matchesItemSearch(ItemStack stack);

    boolean canEditSign(ItemStack heldStack);

    boolean canLanternConnect(BlockState state, IWorldReader worldIn, BlockPos pos);

    boolean canBurnVineTips();
}
