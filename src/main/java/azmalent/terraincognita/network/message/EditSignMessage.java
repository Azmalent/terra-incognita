package azmalent.terraincognita.network.message;

import azmalent.terraincognita.client.gui.ModEditSignScreen;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class EditSignMessage {
    private final BlockPos pos;

    public EditSignMessage(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(final EditSignMessage packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static EditSignMessage decode(PacketBuffer buffer) {
        return new EditSignMessage(buffer.readBlockPos());
    }

    @SuppressWarnings("ConstantConditions")
    public static void handle(final EditSignMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                Minecraft mc = Minecraft.getInstance();

                World world = mc.player.getEntityWorld();
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof ModSignTileEntity) {
                    ModSignTileEntity sign = (ModSignTileEntity) te;
                    mc.displayGuiScreen(new ModEditSignScreen(sign));
                }
            }
        });

        context.setPacketHandled(true);
    }
}
