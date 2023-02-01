package azmalent.terraincognita;

import azmalent.cuneiform.integration.ModIntegrationManager;
import azmalent.cuneiform.registry.RegistryHelper;
import azmalent.terraincognita.common.ModBiomeTags;
import azmalent.terraincognita.common.ModBlockTags;
import azmalent.terraincognita.common.ModItemTags;
import azmalent.terraincognita.common.registry.*;
import azmalent.terraincognita.common.event.ToolInteractionHandler;
import azmalent.terraincognita.common.ModTrades;
import azmalent.terraincognita.common.world.ModSurfaceRules;
import azmalent.terraincognita.datagen.TIBlockStateProvider;
import azmalent.terraincognita.datagen.TIItemModelProvider;
import azmalent.terraincognita.integration.ModIntegration;
import azmalent.terraincognita.integration.top.ButterflyInfoProvider;
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
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TerraIncognita.MODID)
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TerraIncognita {
    public static final String MODID = "terraincognita";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public TerraIncognita() {
        initConfigs();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBiomes.BIOMES.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);
        ModMenus.MENUS.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModFeatures.FEATURES.register(bus);
        ModLootModifiers.LOOT_MODIFIERS.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModRecipes.RECIPES.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModTreeDecorators.TREE_DECORATORS.register(bus);

        ModBanners.register();

        ModIntegrationManager.initModProxies(ModIntegration.class, MODID);
        ModIntegration.QUARK.register(bus);
        ModIntegration.FARMERS_DELIGHT.register(bus);

        ModItemTags.init();
        ModBlockTags.init();
        ModBiomeTags.init();
    }

    private static void initConfigs() {
        TIConfig.INSTANCE.buildSpec();
        TIConfig.INSTANCE.register();
        TIConfig.INSTANCE.sync();

        TIServerConfig.INSTANCE.buildSpec();
        TIServerConfig.INSTANCE.register();
    }

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModBiomes::initBiomes);
        event.enqueueWork(ModSurfaceRules::initRules);
        event.enqueueWork(ModWoodTypes::registerBeehivesToPOI);

        ModBlocks.initFlammability();
        ModItems.initFuelValues();
        ModRecipes.initCompostables();
        ToolInteractionHandler.initToolInteractions();
        ModTrades.initWandererTrades();
    }

    @SubscribeEvent
    public static void sendIMCMessages(InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", ButterflyInfoProvider::new);
        }
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var exFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new TIBlockStateProvider(generator, exFileHelper));
            generator.addProvider(new TIItemModelProvider(generator, exFileHelper));
        }

        if (event.includeServer()) {

        }
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name);
    }
}
