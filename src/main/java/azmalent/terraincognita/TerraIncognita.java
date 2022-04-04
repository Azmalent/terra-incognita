package azmalent.terraincognita;

import azmalent.cuneiform.lib.integration.ModIntegrationManager;
import azmalent.cuneiform.lib.registry.RegistryHelper;
import azmalent.terraincognita.common.ModBlockTags;
import azmalent.terraincognita.common.ModItemTags;
import azmalent.terraincognita.common.registry.*;
import azmalent.terraincognita.common.world.ModConfiguredFeatures;
import azmalent.terraincognita.integration.ModIntegration;
import azmalent.terraincognita.integration.theoneprobe.ButterflyInfoProvider;
import azmalent.terraincognita.proxy.ClientProxy;
import azmalent.terraincognita.proxy.IProxy;
import azmalent.terraincognita.proxy.ServerProxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TerraIncognita.MODID)
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TerraIncognita {
    public static final String MODID = "terraincognita";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final RegistryHelper REG_HELPER = new RegistryHelper(MODID);

    public TerraIncognita() {
        TIConfig.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlockStateProviders.PROVIDERS.register(bus);
        ModMenus.MENUS.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModFeatures.FEATURES.register(bus);
        ModLootModifiers.LOOT_MODIFIERS.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModRecipes.RECIPES.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModTreeDecorators.TREE_DECORATORS.register(bus);
        ModBanners.register();

        ModIntegrationManager.initModProxies(ModIntegration.class, MODID);
        ModIntegration.QUARK.register(bus);
        ModIntegration.FARMERS_DELIGHT.register(bus);

        ModItemTags.init();
        ModBlockTags.init();
    }

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        ModBlocks.initToolInteractions();
        ModBlocks.initFlammability();
        ModItems.initFuelValues();
        ModEntities.registerSpawns();
        ModRecipes.initCompostables();
        //ModTweaks.modifyFlowerGradients();
    }

    @SubscribeEvent
    public static void sendIMCMessages(InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", ButterflyInfoProvider::new);
        }
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name);
    }
}
