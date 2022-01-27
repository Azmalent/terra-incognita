package azmalent.terraincognita.network;

import azmalent.cuneiform.lib.network.IMessage;
import azmalent.terraincognita.TerraIncognita;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;

public record S2CEditSignMessage(BlockPos pos) implements IMessage.ServerToClient {
    @Override
    public void onReceive(NetworkEvent.Context context) {
        TerraIncognita.PROXY.openSignEditor(pos);
    }
}
