package azmalent.terraincognita.proxy;

import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public void spawnParticle(World world, IParticleData data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        //NO-OP
    }

    @Override
    public void openSignEditor(BlockPos pos) {
        //NO-OP
    }

    @Override
    public void updateSignOnClient(BlockPos pos, ITextComponent[] lines, int color) {
        //NO-OP
    }
}
