package azmalent.terraincognita.client.event;

import azmalent.terraincognita.client.gui.BasketContainerScreen;
import azmalent.terraincognita.common.data.ModBlockTags;
import azmalent.terraincognita.common.registry.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vazkii.quark.base.handler.RenderLayerHandler;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientEventHandler::clientSetup);
        bus.addListener(ClientEventHandler::onRegisterModels);
        bus.addListener(ClientEventHandler::onStitch);
        bus.addListener(ColorHandler::registerBlockColorHandlers);
        bus.addListener(ColorHandler::registerItemColorHandlers);
        bus.addListener(ModParticles::registerParticleFactories);

        TooltipHandler.registerListeners();
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModBlocks.HELPER::initRenderTypes);
        event.enqueueWork(ModItems::registerPropertyOverrides);

        ScreenManager.registerFactory(ModContainers.BASKET.get(), BasketContainerScreen::new);
    }

    public static void onRegisterModels(ModelRegistryEvent event) {
        ModEntities.registerRenderers();
        ModTileEntities.registerRenderers();
    }

    public static void onStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(Atlases.SIGN_ATLAS)) {
            ModWoodTypes.VALUES.forEach(woodType -> event.addSprite(woodType.SIGN_TEXTURE));
            return;
        }

        if (event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
            ModWoodTypes.VALUES.forEach(woodType -> {
                for (ChestType type : ChestType.VALUES) {
                    event.addSprite(woodType.getChestTexture(type, false));
                    event.addSprite(woodType.getChestTexture(type, true));
                }
            });
        }
    }
}
