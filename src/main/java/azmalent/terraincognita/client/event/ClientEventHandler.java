package azmalent.terraincognita.client.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.particle.DandelionFluffParticle;
import azmalent.terraincognita.client.renderer.blockentity.ModChestRenderer;
import azmalent.terraincognita.client.renderer.entity.ButterflyRenderer;
import azmalent.terraincognita.client.renderer.entity.ModBoatRenderer;
import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import azmalent.terraincognita.common.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
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
        ModMenus.registerMenuScreens();
    }

    @SubscribeEvent
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

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BOAT.get(), ModBoatRenderer::new);
        event.registerEntityRenderer(ModEntities.BUTTERFLY.get(), ButterflyRenderer<Butterfly>::new);

        event.registerBlockEntityRenderer(ModBlockEntities.CHEST.get(), ModChestRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TRAPPED_CHEST.get(), ModChestRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SIGN.get(), SignRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleEngine particles = Minecraft.getInstance().particleEngine;

        particles.register(ModParticles.DANDELION_FLUFF.get(), DandelionFluffParticle.Factory::new);
    }
}
