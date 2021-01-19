package azmalent.terraincognita.client.event;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.client.particle.DandelionFluffParticle;
import azmalent.terraincognita.common.init.ModBiomes;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.init.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
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
        bus.addListener(ClientEventHandler::registerBlockColorHandlers);
        bus.addListener(ClientEventHandler::registerItemColorHandlers);
        bus.addListener(ClientEventHandler::registerParticleFactories);

        if (TIConfig.Flora.flowerBand.get()) {
            MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::removeDyedWreathTooltip);
        }
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModBlocks.initRenderLayers();
            ModBiomes.initGrassModifiers();
            ModItems.registerPropertyOverrides();
        });
    }

    public static void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        if (TIConfig.Flora.smallLilypad.get()) {
            colors.register((state, reader, pos, color) -> reader != null && pos != null ? 2129968 : 7455580, ModBlocks.SMALL_LILYPAD.getBlock());
        }

        if (TIConfig.Flora.lotus.get()) {
            for (BlockEntry lotus : ModBlocks.LOTUSES) {
                colors.register((state, reader, pos, color) -> reader != null && pos != null ? 2129968 : 7455580, lotus.getBlock());
            }
        }

        if (TIConfig.Flora.reeds.get()) {
            colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : -1, ModBlocks.REEDS.getBlock(), ModBlocks.REEDS.getPotted());
        }
    }

    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();

        if (TIConfig.Flora.smallLilypad.get()) {
            colors.register((stack, index) -> {
                BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
                return blockColors.getColor(blockstate, null, null, index);
            }, ModBlocks.SMALL_LILYPAD.getItem());
        }

        if (TIConfig.Flora.flowerBand.get()) {
            colors.register((stack, index) -> index > 0 ? -1 : ModItems.FLOWER_BAND.get().getColor(stack), ModItems.FLOWER_BAND.get());
        }
    }

    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleManager particles = Minecraft.getInstance().particles;

        if (TIConfig.Flora.dandelionPuff.get()) {
            particles.registerFactory(ModParticles.DANDELION_FLUFF.get(), DandelionFluffParticle.Factory::new);
        }
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
