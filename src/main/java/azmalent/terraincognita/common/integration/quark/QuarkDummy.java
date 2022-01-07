package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.eventbus.api.IEventBus;

@ModProxyDummy("quark")
public class QuarkDummy implements IQuarkIntegration {
    @Override
    public void register(IEventBus iEventBus) {
        //NO-OP
    }

    @Override
    public boolean matchesItemSearch(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canEditSign(ItemStack heldStack) {
        return false;
    }

    @Override
    public boolean canLanternConnect(BlockState state, LevelReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canBurnVineTips() {
        return false;
    }
}
