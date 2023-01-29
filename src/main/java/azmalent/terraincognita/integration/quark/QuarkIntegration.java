package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.integration.IntegrationImpl;
import azmalent.terraincognita.TerraIncognita;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.building.module.ShearVinesModule;
import vazkii.quark.content.client.module.ChestSearchingModule;

@IntegrationImpl("quark")
public final class QuarkIntegration implements IQuarkProxy {
    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Quark...");
    }

    @Override
    public boolean namesMatch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }

    @Override
    public boolean canCutVines() {
        return ModuleLoader.INSTANCE.isModuleEnabled(ShearVinesModule.class);
    }
}
