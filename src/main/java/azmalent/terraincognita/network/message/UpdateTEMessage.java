package azmalent.terraincognita.network.message;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class UpdateTEMessage {
    private final BlockPos pos;
    private final CompoundNBT nbt;

    public UpdateTEMessage(BlockPos pos, CompoundNBT nbt) {
        this.pos = pos;
        this.nbt = nbt;
    }

    public static void encode(final UpdateTEMessage message, PacketBuffer buffer) {
        buffer.writeBlockPos(message.pos);
        buffer.writeCompoundTag(message.nbt);
    }

    public static UpdateTEMessage decode(PacketBuffer buffer) {
        return new UpdateTEMessage(buffer.readBlockPos(), buffer.readCompoundTag());
    }

    public static void handle(final UpdateTEMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                World world = context.getSender().getEntityWorld();
                TileEntity te = world.getTileEntity(message.pos);
                if (te != null && message.nbt != null) {
                    te.write(message.nbt);
                }
            }
        });

        context.setPacketHandled(true);
    }
}
