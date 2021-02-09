package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IQuarkIntegration {
    void register(IEventBus bus);

    boolean matchesItemSearch(ItemStack stack);

    boolean canEditSign(ItemStack heldStack);

    @ModProxyDummy("quark")
    class Dummy implements IQuarkIntegration {
        @Override
        public void register(IEventBus bus) {

        }

        @Override
        public boolean matchesItemSearch(ItemStack stack) {
            return false;
        }

        @Override
        public boolean canEditSign(ItemStack heldStack) {
            return false;
        }
    }
}
