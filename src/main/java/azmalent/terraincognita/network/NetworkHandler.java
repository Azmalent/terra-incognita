package azmalent.terraincognita.network;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.network.message.s2c.S2CEditSignMessage;
import azmalent.terraincognita.network.message.UpdateSignMessage;
import azmalent.terraincognita.network.message.s2c.S2CSpawnParticleMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL = "3";
    private static final ResourceLocation CHANNEL_NAME = TerraIncognita.prefix("channel");
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(CHANNEL_NAME, () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    @SuppressWarnings("UnusedAssignment")
    public static void registerMessages() {
        int id = 0;
        CHANNEL.registerMessage(id++, S2CEditSignMessage.class, S2CEditSignMessage::encode, S2CEditSignMessage::decode, S2CEditSignMessage::handle);
        CHANNEL.registerMessage(id++, S2CSpawnParticleMessage.class, S2CSpawnParticleMessage::encode, S2CSpawnParticleMessage::decode, S2CSpawnParticleMessage::handle);
        CHANNEL.registerMessage(id++, UpdateSignMessage.class, UpdateSignMessage::encode, UpdateSignMessage::decode, UpdateSignMessage::handle);
    }

    public static void sendToServer(Object message) {
        CHANNEL.sendToServer(message);
    }

    public static void sendToPlayer(ServerPlayerEntity player, Object message) {
        if (!(player instanceof FakePlayer)) {
            CHANNEL.sendTo(message, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToAllPlayers(Object message) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }
}
