package azmalent.terraincognita.client.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.client.ModRenderTypes;
import azmalent.terraincognita.client.gui.BasketContainerScreen;
import azmalent.terraincognita.common.init.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientEventHandler::clientSetup);
        bus.addListener(ColorHandler::registerBlockColorHandlers);
        bus.addListener(ColorHandler::registerItemColorHandlers);
        bus.addListener(ModParticles::registerParticleFactories);

        if (TIConfig.Flora.wreath.get()) {
            MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::removeDyedWreathTooltip);
        }

        if (TIConfig.Flora.reeds.get()) {
            MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::renderBasketTooltip);
        }
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModRenderTypes.init();
            ModBiomes.initGrassModifiers();
            ModItems.registerPropertyOverrides();
        });

        ScreenManager.registerFactory(ModContainers.BASKET.get(), BasketContainerScreen::new);
    }

    public static void removeDyedWreathTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() != ModItems.WREATH.get() || event.getFlags().isAdvanced()) {
            return;
        }

        List<ITextComponent> tooltip = event.getToolTip();
        for (ITextComponent line : tooltip) {
            if (line instanceof TranslationTextComponent) {
                String key = ((TranslationTextComponent) line).getKey();
                if (key.equals("item.dyed")) {
                    tooltip.remove(line);
                    return;
                }
            }
        }
    }

    public static void renderBasketTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() != ModBlocks.BASKET.getItem()) {
            return;
        }

        //TODO
    }
}
