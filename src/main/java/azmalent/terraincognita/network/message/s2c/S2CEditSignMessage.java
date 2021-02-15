package azmalent.terraincognita.network.message.s2c;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.ClientHandler;
import azmalent.terraincognita.client.gui.ModEditSignScreen;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class S2CEditSignMessage {
    private final BlockPos pos;

    public S2CEditSignMessage(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(final S2CEditSignMessage message, PacketBuffer buffer) {
        buffer.writeBlockPos(message.pos);
    }

    public static S2CEditSignMessage decode(PacketBuffer buffer) {
        return new S2CEditSignMessage(buffer.readBlockPos());
    }

    @SuppressWarnings("ConstantConditions")
    public static void handle(final S2CEditSignMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.enqueueWork(() -> {
                TileEntity te = ClientHandler.getWorld().getTileEntity(message.pos);
                if (!(te instanceof ModSignTileEntity)) {
                    te = new ModSignTileEntity();
                    te.setWorldAndPos(ClientHandler.getWorld(), message.pos);
                }

                TerraIncognita.PROXY.openSignEditor((ModSignTileEntity) te);
            });

            context.setPacketHandled(true);
        }
    }
}
