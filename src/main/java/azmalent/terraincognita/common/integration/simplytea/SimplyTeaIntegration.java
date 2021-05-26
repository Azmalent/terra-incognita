package azmalent.terraincognita.common.integration.simplytea;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import knightminer.simplytea.core.Registration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

@SuppressWarnings({"FieldCanBeLocal", "unused", "ConstantConditions"})
@ModProxyImpl("simplytea")
public class SimplyTeaIntegration implements IModIntegration {
    private RegistryObject<Item> FIREWEED_TEA_BAG;
    private RegistryObject<Item> FIREWEED_TEA_CUP;

    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Simply Tea...");
        ItemGroup group = Registration.group;

        FIREWEED_TEA_BAG = ModItems.ITEMS.register("fireweed_teabag", () -> new Item(new Item.Properties().group(group)));
        FIREWEED_TEA_CUP = ModItems.ITEMS.register("fireweed_tea_cup", FireweedTeaItem::new);
    }
}
