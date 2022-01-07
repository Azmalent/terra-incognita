package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.particle.DandelionFluffParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TerraIncognita.MODID);

    public static final RegistryObject<SimpleParticleType> DANDELION_FLUFF = PARTICLES.register("dandelion_fluff", () -> new SimpleParticleType(false));

    @OnlyIn(Dist.CLIENT)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleEngine particles = Minecraft.getInstance().particleEngine;

        particles.register(DANDELION_FLUFF.get(), DandelionFluffParticle.Factory::new);
    }
}
