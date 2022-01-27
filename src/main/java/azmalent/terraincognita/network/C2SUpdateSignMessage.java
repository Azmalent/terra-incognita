package azmalent.terraincognita.network;

import azmalent.cuneiform.lib.network.IMessage;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.tile.ModSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.network.NetworkEvent;

public record C2SUpdateSignMessage(BlockPos pos, Component[] lines, int color) implements IMessage.ClientToServer {
    public C2SUpdateSignMessage {
        if (lines.length != 4) {
            throw new IllegalArgumentException("Expected 4 lines of text, got " + lines.length);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null) return;

        player.resetLastActionTime();

        ServerLevel world = player.getLevel();
        if (world.isAreaLoaded(pos, 1) && world.getBlockEntity(pos) instanceof ModSignBlockEntity sign) {
            for (int i = 0; i < 4; i++) {
                sign.setText(i, lines[i]);
            }

            sign.setChanged();
            sign.setTextColor(DyeColor.byId(color));
            world.sendBlockUpdated(pos, sign.getBlockState(), sign.getBlockState(), 3);

            S2CUpdateSignMessage message = new S2CUpdateSignMessage(pos, lines, color);
            TerraIncognita.NETWORK.sendToAllPlayers(message);
        }
    }
}
