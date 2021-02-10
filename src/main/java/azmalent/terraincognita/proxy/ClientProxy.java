package azmalent.terraincognita.proxy;

import azmalent.terraincognita.client.gui.ModEditSignScreen;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {
    public void spawnParticle(World world, IParticleData data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(data, alwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void openSignEditor(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();

        World world = mc.player.getEntityWorld();
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ModSignTileEntity) {
            ModSignTileEntity sign = (ModSignTileEntity) te;
            mc.displayGuiScreen(new ModEditSignScreen(sign));
        }
    }
}
