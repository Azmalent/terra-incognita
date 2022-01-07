package azmalent.terraincognita.network.message;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import azmalent.terraincognita.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class UpdateSignMessage {
    private final BlockPos pos;
    private final Component[] lines;
    private final int color;

    public UpdateSignMessage(BlockPos pos, Component[] lines, int color) {
        if (lines.length != 4) {
            throw new IllegalArgumentException("Expected 4 lines of text, got " + lines.length);
        }

        this.pos = pos;
        this.lines = lines;
        this.color = color;
    }

    public static void encode(final UpdateSignMessage message, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(message.pos);
        for (int i = 0; i < 4; i++) {
            buffer.writeComponent(message.lines[i]);
        }
        buffer.writeInt(message.color);
    }

    public static UpdateSignMessage decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        Component[] lines = new Component[4];
        for (int i = 0; i < 4; i++) {
            lines[i] = buffer.readComponent();
        }
        int color = buffer.readInt();

        return new UpdateSignMessage(pos, lines, color);
    }

    @SuppressWarnings("ConstantConditions")
    public static void handle(final UpdateSignMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                player.resetLastActionTime();

                ServerLevel world = player.getLevel();
                if (world.isAreaLoaded(message.pos, 1)) {
                    BlockEntity te = world.getBlockEntity(message.pos);
                    if (te instanceof ModSignTileEntity) {
                        ModSignTileEntity sign = (ModSignTileEntity) te;
                        for (int i = 0; i < 4; i++) {
                            sign.setText(i, message.lines[i]);
                        }

                        sign.setChanged();
                        sign.setTextColor(DyeColor.byId(message.color));
                        world.sendBlockUpdated(message.pos, sign.getBlockState(), sign.getBlockState(), 3);

                        NetworkHandler.sendToAllPlayers(message);
                    }
                }
            });
        }
        else {
            context.enqueueWork(() -> TerraIncognita.PROXY.updateSignOnClient(message.pos, message.lines, message.color));
        }

        context.setPacketHandled(true);
    }
}
