package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import net.minecraft.item.ItemStack;
import vazkii.quark.content.client.module.ChestSearchingModule;

@ModProxyImpl("quark")
public class QuarkIntegration implements IQuarkIntegration {
    @Override
    public boolean matchesItemSearch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }
}
