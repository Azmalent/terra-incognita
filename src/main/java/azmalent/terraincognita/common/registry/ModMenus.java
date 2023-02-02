package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.client.gui.BasketMenuScreen;
import azmalent.terraincognita.common.inventory.BasketMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = REG_HELPER.getOrCreateRegistry(ForgeRegistries.CONTAINERS);

    public static final RegistryObject<MenuType<BasketMenu>> BASKET = MENUS.register("basket", () -> IForgeMenuType.create(BasketMenu::fromNetwork));

    @OnlyIn(Dist.CLIENT)
    public static void registerMenuScreens() {
        MenuScreens.register(ModMenus.BASKET.get(), BasketMenuScreen::new);
    }
}
