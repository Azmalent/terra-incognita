package azmalent.terraincognita.proxy;

import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public interface IProxy {
    default void spawnParticle(World world, IParticleData data, boolean alwaysRender, Vector3d pos, Vector3d speed) {
        spawnParticle(world, data, alwaysRender, pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
    }

    void spawnParticle(World world, IParticleData data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed);
}