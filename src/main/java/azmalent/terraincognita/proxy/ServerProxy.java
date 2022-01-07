package azmalent.terraincognita.proxy;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class ServerProxy implements IProxy {
    @Override
    public Level getClientWorld() {
        throw new AssertionError("getClientWorld() called on server");
    }

    @Override
    public void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        //NO-OP
    }

    @Override
    public void openSignEditor(BlockPos pos) {
        //NO-OP
    }

    @Override
    public void updateSignOnClient(BlockPos pos, Component[] lines, int color) {
        //NO-OP
    }
}
