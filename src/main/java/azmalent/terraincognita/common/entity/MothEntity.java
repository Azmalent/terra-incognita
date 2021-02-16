package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

@SuppressWarnings("ConstantConditions")
public class MothEntity extends AbstractButterflyEntity {
    private static final ResourceLocation TEXTURE = TerraIncognita.prefix("textures/entity/butterfly/moth.png");

    public MothEntity(EntityType<MothEntity> type, World world) {
        super(type, world);
    }

    public MothEntity(World world, double x, double y, double z) {
        super(ModEntities.MOTH.get(), world);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public MothEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(ModEntities.MOTH.get(), world);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected boolean isNocturnal() {
        return true;
    }
}
