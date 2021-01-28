package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import net.minecraft.item.ItemStack;

public interface IQuarkIntegration {
    boolean matchesItemSearch(ItemStack stack);

    @ModProxyDummy("quark")
    class Dummy implements IQuarkIntegration {
        @Override
        public boolean matchesItemSearch(ItemStack stack) {
            return false;
        }
    }
}
