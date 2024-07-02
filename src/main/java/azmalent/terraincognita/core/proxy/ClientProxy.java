package azmalent.terraincognita.core.proxy;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {
    @Override
    public void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(data, alwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
