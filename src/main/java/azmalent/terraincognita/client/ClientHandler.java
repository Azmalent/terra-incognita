package azmalent.terraincognita.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {
    public static final Minecraft MC = Minecraft.getInstance();

    public static ClientPlayerEntity getPlayer() {
        return MC.player;
    }

    public static ClientWorld getWorld() {
        return MC.world;
    }
}
