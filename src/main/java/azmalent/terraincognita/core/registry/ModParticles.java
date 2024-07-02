package azmalent.terraincognita.core.registry;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.PARTICLE_TYPES);

    public static final RegistryObject<SimpleParticleType> DANDELION_FLUFF = PARTICLES.register("dandelion_fluff", () -> new SimpleParticleType(false));
}
