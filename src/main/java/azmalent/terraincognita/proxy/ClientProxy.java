package azmalent.terraincognita.proxy;

import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {
    public void spawnParticle(World world, IParticleData data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(data, alwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
