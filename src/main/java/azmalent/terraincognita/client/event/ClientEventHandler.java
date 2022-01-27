package azmalent.terraincognita.client.event;

import azmalent.terraincognita.client.gui.BasketContainerScreen;
import azmalent.terraincognita.client.renderer.entity.ButterflyRenderer;
import azmalent.terraincognita.client.renderer.entity.ModBoatRenderer;
import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import azmalent.terraincognita.common.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientEventHandler::clientSetup);
        bus.addListener(ClientEventHandler::stitchTextures);
        bus.addListener(ColorHandler::registerBlockColorHandlers);
        bus.addListener(ColorHandler::registerItemColorHandlers);
        bus.addListener(ModEntities::registerRenderers);
        bus.addListener(ModBlockEntities::registerRenderers);
        bus.addListener(ModParticles::registerParticleFactories);

        TooltipHandler.registerListeners();
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModItems::registerPropertyOverrides);
        MenuScreens.register(ModContainers.BASKET.get(), BasketContainerScreen::new);
    }

    public static void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(Sheets.SIGN_SHEET)) {
            ModWoodTypes.VALUES.forEach(woodType -> event.addSprite(woodType.SIGN_TEXTURE));
            return;
        }

        if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
            ModWoodTypes.VALUES.forEach(woodType -> {
                for (ChestType type : ChestType.BY_ID) {
                    event.addSprite(woodType.getChestTexture(type, false));
                    event.addSprite(woodType.getChestTexture(type, true));
                }
            });
        }
    }
}
