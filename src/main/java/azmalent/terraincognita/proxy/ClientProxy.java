package azmalent.terraincognita.proxy;

import azmalent.terraincognita.client.ClientHelper;
import azmalent.terraincognita.client.gui.ModEditSignScreen;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {
    public void spawnParticle(World world, IParticleData data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(data, alwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public World getClientWorld() {
        return ClientHelper.getWorld();
    }

    public void openSignEditor(BlockPos pos) {
        TileEntity te = ClientHelper.getWorld().getTileEntity(pos);
        if (!(te instanceof ModSignTileEntity)) {
            te = new ModSignTileEntity();
            te.setWorldAndPos(ClientHelper.getWorld(), pos);
        }

        ModSignTileEntity sign = (ModSignTileEntity) te;
        ClientHelper.MC.displayGuiScreen(new ModEditSignScreen(sign));
    }

    @Override
    public void updateSignOnClient(BlockPos pos, ITextComponent[] lines, int color) {
        World world = ClientHelper.getWorld();
        if (world.isAreaLoaded(pos, 1)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ModSignTileEntity) {
                ModSignTileEntity sign = (ModSignTileEntity) te;
                for (int i = 0; i < 4; i++) {
                    sign.setText(i, lines[i]);
                }

                sign.setTextColor(DyeColor.byId(color));
            }
        }
    }
}
