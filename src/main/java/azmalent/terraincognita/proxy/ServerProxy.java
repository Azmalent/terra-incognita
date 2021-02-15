package azmalent.terraincognita.proxy;

import azmalent.terraincognita.common.tile.ModSignTileEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public void spawnParticle(World world, IParticleData data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        //NO-OP
    }

    @Override
    public void openSignEditor(ModSignTileEntity sign) {
        //NO-OP
    }
}
