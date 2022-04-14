package azmalent.terraincognita.client;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.particle.DandelionFluffParticle;
import azmalent.terraincognita.client.renderer.blockentity.TIChestRenderer;
import azmalent.terraincognita.client.renderer.entity.ButterflyRenderer;
import azmalent.terraincognita.client.renderer.entity.TIBoatRenderer;
import azmalent.terraincognita.client.tooltip.TooltipHandler;
import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import azmalent.terraincognita.common.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModItems::registerPropertyOverrides);

        event.enqueueWork(() -> {
            ModWoodTypes.VALUES.forEach(Sheets::addWoodType);
        });

        TooltipHandler.registerComponentFactories();
        ModMenus.registerMenuScreens();
    }

    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
            ModWoodTypes.VALUES.forEach(woodType -> {
                for (ChestType type : ChestType.BY_ID) {
                    event.addSprite(woodType.getChestTexture(type, false));
                    event.addSprite(woodType.getChestTexture(type, true));
                }
            });
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BOAT.get(), TIBoatRenderer::new);
        event.registerEntityRenderer(ModEntities.BUTTERFLY.get(), ButterflyRenderer<Butterfly>::new);

        event.registerBlockEntityRenderer(ModBlockEntities.CHEST.get(), TIChestRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TRAPPED_CHEST.get(), TIChestRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleEngine particles = Minecraft.getInstance().particleEngine;

        particles.register(ModParticles.DANDELION_FLUFF.get(), DandelionFluffParticle.Factory::new);
    }
}
