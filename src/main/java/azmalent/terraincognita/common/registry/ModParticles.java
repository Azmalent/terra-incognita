package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = TerraIncognita.REG_HELPER.getOrCreateRegistry(ForgeRegistries.PARTICLE_TYPES);

    public static final RegistryObject<SimpleParticleType> DANDELION_FLUFF = PARTICLES.register("dandelion_fluff", () -> new SimpleParticleType(false));
}
