package azmalent.terraincognita.proxy;

import azmalent.terraincognita.client.ClientHelper;
import azmalent.terraincognita.client.gui.ModEditSignScreen;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {
    public void spawnParticle(Level world, ParticleOptions data, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(data, alwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public Level getClientWorld() {
        return ClientHelper.getWorld();
    }

    public void openSignEditor(BlockPos pos) {
        BlockEntity te = ClientHelper.getWorld().getBlockEntity(pos);
        if (!(te instanceof ModSignTileEntity)) {
            te = new ModSignTileEntity();
            te.setLevelAndPosition(ClientHelper.getWorld(), pos);
        }

        ModSignTileEntity sign = (ModSignTileEntity) te;
        ClientHelper.MC.setScreen(new ModEditSignScreen(sign));
    }

    @Override
    public void updateSignOnClient(BlockPos pos, Component[] lines, int color) {
        Level world = ClientHelper.getWorld();
        if (world.isAreaLoaded(pos, 1)) {
            BlockEntity te = world.getBlockEntity(pos);
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
