package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import knightminer.simplytea.core.Registration;
import knightminer.simplytea.item.TeaCupItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@ModProxyImpl("simplytea")
public class SimplyTeaIntegration implements IModIntegration {
    private RegistryObject<Item> FIREWEED_TEA_BAG;
    private RegistryObject<Item> FIREWEED_TEA_CUP;

    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Simply Tea...");
        ItemGroup group = Registration.group;

        FIREWEED_TEA_BAG = ModItems.ITEMS.register("fireweed_teabag", () -> new Item(new Item.Properties().group(group)));

        FIREWEED_TEA_CUP = ModItems.ITEMS.register("fireweed_tea_cup", () -> new TeaCupItem(
            new Item.Properties().group(group).maxStackSize(1).maxDamage(2).setNoRepair().containerItem(Registration.cup)
                .food(new Food.Builder().hunger(2).saturation(0.5f)
                    .effect(() -> new EffectInstance(Registration.restful, 20 * 20, 1), 1)
                    .build()
            )
        ));
    }
}
