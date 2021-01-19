package azmalent.terraincognita.integration.simplytea;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModItems;
import knightminer.simplytea.core.Registration;
import knightminer.simplytea.item.TeaCupItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public interface ISimplyTeaIntegration {
    String MODID = "simplytea";

    void register(IEventBus bus);

    @ModProxyDummy(ISimplyTeaIntegration.MODID)
    class Dummy implements ISimplyTeaIntegration {
        @Override
        public void register(IEventBus bus) { }
    }

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    @ModProxyImpl(ISimplyTeaIntegration.MODID)
    class Impl implements ISimplyTeaIntegration {
        private RegistryObject<Item> FIREWEED_TEA_BAG;
        private RegistryObject<Item> FIREWEED_TEA_CUP;

        @Override
        public void register(IEventBus bus) {
            if (TIConfig.Flora.arcticFlowers.get()) {
                TerraIncognita.LOGGER.info("Integrating with Simply Tea...");
                ItemGroup group = Registration.group;

                FIREWEED_TEA_BAG = ModItems.ITEMS.register("fireweed_teabag", () -> new Item(new Item.Properties().group(group)));

                FIREWEED_TEA_CUP = ModItems.ITEMS.register("fireweed_tea_cup", () -> new TeaCupItem(
                    new Item.Properties().group(group).food(new Food.Builder().hunger(2).saturation(0.5f)
                        .effect(new EffectInstance(Registration.restful, 60 * 20, 1), 1)
                        .build()
                    )
                ));
            }
        }
    }
}
