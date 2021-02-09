package azmalent.terraincognita.network;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.network.message.EditSignMessage;
import azmalent.terraincognita.network.message.UpdateSignMessage;
import azmalent.terraincognita.network.message.UpdateTEMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL = "1";
    private static final ResourceLocation CHANNEL_NAME = TerraIncognita.prefix("channel");
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(CHANNEL_NAME, () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    @SuppressWarnings("UnusedAssignment")
    public static void registerMessages() {
        int id = 0;
        CHANNEL.registerMessage(id++, EditSignMessage.class, EditSignMessage::encode, EditSignMessage::decode, EditSignMessage::handle);
        CHANNEL.registerMessage(id++, UpdateSignMessage.class, UpdateSignMessage::encode, UpdateSignMessage::decode, UpdateSignMessage::handle);
        CHANNEL.registerMessage(id++, UpdateTEMessage.class, UpdateTEMessage::encode, UpdateTEMessage::decode, UpdateTEMessage::handle);
    }

    public static void sendToServer(Object message) {
        CHANNEL.sendToServer(message);
    }

    public static void sendTo(ServerPlayerEntity player, Object message) {
        if (!(player instanceof FakePlayer)) {
            CHANNEL.sendTo(message, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
