package azmalent.terraincognita.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHelper {
    public static final Minecraft MC = Minecraft.getInstance();

    public static World getWorld() {
        return MC.world;
    }
}
