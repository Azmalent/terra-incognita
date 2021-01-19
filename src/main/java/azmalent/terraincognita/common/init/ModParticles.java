package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
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
}
