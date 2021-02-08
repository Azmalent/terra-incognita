package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IQuarkIntegration {
    boolean matchesItemSearch(ItemStack stack);

    void register(IEventBus bus);

    void initRenderLayers();

    @ModProxyDummy("quark")
    class Dummy implements IQuarkIntegration {

        @Override
        public boolean matchesItemSearch(ItemStack stack) {
            return false;
        }

        @Override
        public void register(IEventBus bus) {

        }

        @Override
        public void initRenderLayers() {

        }
    }
}
