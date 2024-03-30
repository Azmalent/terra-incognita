package azmalent.terraincognita;

import azmalent.cuneiform.integration.ModIntegrationManager;
import azmalent.cuneiform.registry.RegistryHelper;
import azmalent.terraincognita.common.ModBiomeTags;
import azmalent.terraincognita.common.ModBlockTags;
import azmalent.terraincognita.common.ModItemTags;
import azmalent.terraincognita.common.ModTrades;
import azmalent.terraincognita.common.datagen.client.TIBlockStateProvider;
import azmalent.terraincognita.common.datagen.client.TIItemModelProvider;
import azmalent.terraincognita.common.datagen.server.TIBlockTagsProvider;
import azmalent.terraincognita.common.datagen.server.TIItemTagsProvider;
import azmalent.terraincognita.common.event.BiomeHandler;
import azmalent.terraincognita.common.event.ToolInteractionHandler;
import azmalent.terraincognita.common.registry.*;
import azmalent.terraincognita.common.world.ModSurfaceRules;
import azmalent.terraincognita.integration.ModIntegration;
import azmalent.terraincognita.integration.top.ButterflyInfoProvider;
import azmalent.terraincognita.proxy.ClientProxy;
import azmalent.terraincognita.proxy.IProxy;
import azmalent.terraincognita.proxy.ServerProxy;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TerraIncognita.MODID)
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TerraIncognita {
    public static final String MODID = "terraincognita";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public TerraIncognita() {
        initConfigs();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModIntegrationManager.initModProxies(ModIntegration.class, MODID);
        ModIntegration.QUARK.register(bus);
        ModIntegration.FARMERS_DELIGHT.register(bus);

        ModBiomes.BIOMES.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
        ModItems.ITEMS.register(bus);
        ModMenus.MENUS.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModFeatures.FEATURES.register(bus);
        ModLootModifiers.LOOT_MODIFIERS.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModPlacements.PLACEMENTS.register(bus);
        ModRecipes.RECIPES.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModTreeDecorators.TREE_DECORATORS.register(bus);

        ModBannerPatterns.register();

        ModItemTags.init();
        ModBlockTags.init();
        ModBiomeTags.init();

        //No fucking clue why I have to do this but it prevents the crashes
        //TODO: remove this hack and find a proper solution
        if (ModList.get().isLoaded("projectvibrantjourneys")) {
            MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, BiomeHandler::onEarlyLoadBiome);
            MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, BiomeHandler::onEarlyLoadBiome);
        } else {
            MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, BiomeHandler::onEarlyLoadBiome);
            MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, BiomeHandler::onEarlyLoadBiome);
        }
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
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new TIBlockStateProvider(generator, existingFileHelper));
            generator.addProvider(new TIItemModelProvider(generator, existingFileHelper));
        }

        if (event.includeServer()) {
            var blockTags = new TIBlockTagsProvider(generator, existingFileHelper);

            generator.addProvider(blockTags);
            generator.addProvider(new TIItemTagsProvider(generator, blockTags, existingFileHelper));
        }
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
