package azmalent.terraincognita.network.message.s2c;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class S2CEditSignMessage {
    private final BlockPos pos;

    public S2CEditSignMessage(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(final S2CEditSignMessage message, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(message.pos);
    }

    public static S2CEditSignMessage decode(FriendlyByteBuf buffer) {
        return new S2CEditSignMessage(buffer.readBlockPos());
    }

    public static void handle(final S2CEditSignMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.enqueueWork(() -> TerraIncognita.PROXY.openSignEditor(message.pos));
        }

        context.setPacketHandled(true);
    }
}
