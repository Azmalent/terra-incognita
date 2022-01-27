package azmalent.terraincognita;

import azmalent.cuneiform.lib.compat.ModCompatUtil;
import azmalent.cuneiform.lib.integration.ModIntegrationManager;
import azmalent.cuneiform.lib.network.CuneiformChannel;
import azmalent.cuneiform.lib.network.SerializationHandler;
import azmalent.cuneiform.lib.registry.RegistryHelper;
import azmalent.terraincognita.client.event.ClientEventHandler;
import azmalent.terraincognita.common.data.ModBlockTags;
import azmalent.terraincognita.common.data.ModItemTags;
import azmalent.terraincognita.common.event.EventHandler;
import azmalent.terraincognita.common.registry.*;
import azmalent.terraincognita.common.integration.ModIntegration;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.proxy.ClientProxy;
import azmalent.terraincognita.proxy.ServerProxy;
import azmalent.terraincognita.proxy.IProxy;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TerraIncognita.MODID)
public class TerraIncognita {
    public static final String MODID = "terraincognita";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final RegistryHelper REG_HELPER = new RegistryHelper(MODID);
    public static final CuneiformChannel NETWORK = new CuneiformChannel(prefix("channel"), 1);

    public TerraIncognita() {
        TIConfig.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlockStateProviders.PROVIDERS.register(bus);
        ModContainers.CONTAINERS.register(bus);
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
        registerMessages();

        EventHandler.registerListeners();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientEventHandler::registerListeners);
    }

    private static void registerMessages() {
        SerializationHandler.registerSerializer(Component.class, FriendlyByteBuf::readComponent, FriendlyByteBuf::writeComponent);

    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name);
    }
}
