package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.inventory.BasketContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = TerraIncognita.REG_HELPER.getOrCreateRegistry(ForgeRegistries.CONTAINERS);

    public static final RegistryObject<MenuType<BasketContainer>> BASKET = CONTAINERS.register("basket", () -> IForgeContainerType.create(BasketContainer::createOnClientSide));
}
