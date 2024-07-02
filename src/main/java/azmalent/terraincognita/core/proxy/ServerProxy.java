package azmalent.terraincognita.core.proxy;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;

public class ServerProxy implements IProxy {
    @Override
    public void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        //NO-OP
    }
}
