package azmalent.terraincognita.proxy;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public interface IProxy {
    default void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, Vec3 pos, Vec3 speed) {
        spawnParticle(world, data, alwaysRender, pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
    }

    void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed);
}
