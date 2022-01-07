package azmalent.terraincognita.proxy;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public interface IProxy {
    default void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, Vec3 pos, Vec3 speed) {
        spawnParticle(world, data, alwaysRender, pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
    }

    Level getClientWorld();

    void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed);

    void openSignEditor(BlockPos pos);

    void updateSignOnClient(BlockPos pos, Component[] lines, int color);
}
