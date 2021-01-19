package azmalent.terraincognita.client.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.client.ModRenderLayers;
import azmalent.terraincognita.common.init.ModBiomes;
import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.init.ModParticles;
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

        if (TIConfig.Flora.flowerBand.get()) {
            MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::removeDyedWreathTooltip);
        }
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModRenderLayers.init();
            ModBiomes.initGrassModifiers();
            ModItems.registerPropertyOverrides();
        });
    }

    public static void removeDyedWreathTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() != ModItems.FLOWER_BAND.get() || event.getFlags().isAdvanced()) {
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

}
