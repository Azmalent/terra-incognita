package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.particle.DandelionFluffParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TerraIncognita.MODID);

    public static RegistryObject<BasicParticleType> DANDELION_FLUFF;

    static {
        if (TIConfig.Flora.dandelionPuff.get()) {
            DANDELION_FLUFF = PARTICLES.register("dandelion_fluff", () -> new BasicParticleType(false));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleManager particles = Minecraft.getInstance().particles;

        if (TIConfig.Flora.dandelionPuff.get()) {
            particles.registerFactory(ModParticles.DANDELION_FLUFF.get(), DandelionFluffParticle.Factory::new);
        }
    }
}
