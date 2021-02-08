package azmalent.terraincognita.client.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.client.ModRenderLayers;
import azmalent.terraincognita.client.gui.BasketContainerScreen;
import azmalent.terraincognita.common.init.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientEventHandler::clientSetup);
        bus.addListener(ClientEventHandler::registerModels);
        bus.addListener(ColorHandler::registerBlockColorHandlers);
        bus.addListener(ColorHandler::registerItemColorHandlers);
        bus.addListener(ModParticles::registerParticleFactories);

        TooltipHandler.registerListeners();
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModRenderLayers::init);
        event.enqueueWork(ModBiomes::initGrassModifiers);
        event.enqueueWork(ModItems::registerPropertyOverrides);

        if (TIConfig.Tools.basket.get()) {
            ScreenManager.registerFactory(ModContainers.BASKET.get(), BasketContainerScreen::new);
        }
    }

    public static void registerModels(ModelRegistryEvent event) {
        ModEntities.registerRenderers();
    }
}
