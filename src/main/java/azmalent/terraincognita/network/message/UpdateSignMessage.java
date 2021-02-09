package azmalent.terraincognita.network.message;

import azmalent.terraincognita.common.tile.ModSignTileEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class UpdateSignMessage {
    private final BlockPos pos;
    private final ITextComponent[] lines;
    private final int color;

    public UpdateSignMessage(BlockPos pos, ITextComponent[] lines, int color) {
        if (lines.length != 4) {
            throw new IllegalArgumentException("Expected 4 lines of text, got " + lines.length);
        }

        this.pos = pos;
        this.lines = lines;
        this.color = color;
    }

    public static void encode(final UpdateSignMessage packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
        for (int i = 0; i < 4; i++) {
            buffer.writeTextComponent(packet.lines[i]);
        }
        buffer.writeInt(packet.color);
    }

    public static UpdateSignMessage decode(PacketBuffer buffer) {
        BlockPos pos = buffer.readBlockPos();
        ITextComponent[] lines = new ITextComponent[4];
        for (int i = 0; i < 4; i++) {
            lines[i] = buffer.readTextComponent();
        }
        int color = buffer.readInt();

        return new UpdateSignMessage(pos, lines, color);
    }

    @SuppressWarnings("ConstantConditions")
    public static void handle(final UpdateSignMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                World world = context.getSender().getEntityWorld();
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof ModSignTileEntity) {
                    ModSignTileEntity sign = (ModSignTileEntity) te;
                    for (int i = 0; i < 4; i++) {
                        sign.setText(i, message.lines[i]);
                    }
                    sign.setTextColor(DyeColor.byId(message.color));
                }
            }
        });

        context.setPacketHandled(true);
    }
}
