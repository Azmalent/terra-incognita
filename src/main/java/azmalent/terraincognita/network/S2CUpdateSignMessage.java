package azmalent.terraincognita.network;

import azmalent.cuneiform.lib.network.IMessage;
import azmalent.terraincognita.TerraIncognita;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

public record S2CUpdateSignMessage(BlockPos pos, Component[] lines, int color) implements IMessage.ServerToClient {
    public S2CUpdateSignMessage {
        if (lines.length != 4) {
            throw new IllegalArgumentException("Expected 4 lines of text, got " + lines.length);
        }
    }

    @Override
    public void onReceive(NetworkEvent.Context context) {
        TerraIncognita.PROXY.updateSignOnClient(pos, lines, color);
    }
}
