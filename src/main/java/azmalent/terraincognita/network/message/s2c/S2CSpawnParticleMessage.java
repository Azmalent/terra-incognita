package azmalent.terraincognita.network.message.s2c;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class S2CSpawnParticleMessage {
    private final ResourceLocation name;
    private final double xPos, yPos, zPos;
    private final double xSpeed, ySpeed, zSpeed;

    public S2CSpawnParticleMessage(SimpleParticleType type, Vec3 pos, Vec3 speed) {
        this(type.getRegistryName(), pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
    }

    public S2CSpawnParticleMessage(SimpleParticleType type, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
        this(type.getRegistryName(), xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
    }

    private S2CSpawnParticleMessage(ResourceLocation name, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public static void encode(final S2CSpawnParticleMessage message, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(message.name);
        buffer.writeDouble(message.xPos);
        buffer.writeDouble(message.yPos);
        buffer.writeDouble(message.zPos);
        buffer.writeDouble(message.xSpeed);
        buffer.writeDouble(message.ySpeed);
        buffer.writeDouble(message.zSpeed);
    }

    public static S2CSpawnParticleMessage decode(FriendlyByteBuf buffer) {
        ResourceLocation name = buffer.readResourceLocation();
        double posX = buffer.readDouble();
        double posY = buffer.readDouble();
        double posZ = buffer.readDouble();
        double speedX = buffer.readDouble();
        double speedY = buffer.readDouble();
        double speedZ = buffer.readDouble();

        return new S2CSpawnParticleMessage(name, posX, posY, posZ, speedX, speedY, speedZ);
    }

    public static void handle(final S2CSpawnParticleMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.enqueueWork(() -> {
                Level world = TerraIncognita.PROXY.getClientWorld();
                SimpleParticleType type = (SimpleParticleType) ForgeRegistries.PARTICLE_TYPES.getValue(message.name);
                if (type != null) {
                    world.addParticle(type, message.xPos, message.yPos, message.zPos, message.xSpeed, message.ySpeed, message.zSpeed);
                }
            });
        }

        context.setPacketHandled(true);
    }

}
