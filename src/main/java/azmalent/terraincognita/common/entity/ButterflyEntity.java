package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.common.init.ModEntities;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class ButterflyEntity extends CreatureEntity implements IFlyingAnimal {
    public static DataParameter<Boolean> LANDED = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);

    public ButterflyEntity(EntityType<ButterflyEntity> type, World world) {
        super(type, world);
        this.preventEntitySpawning = true;

        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    public ButterflyEntity(World world, double x, double y, double z) {
        this(ModEntities.BUTTERFLY.get(), world);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public ButterflyEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.BUTTERFLY.get(), world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(LANDED, false);
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.isAirBlock(pos) ? 10.0F : 0.0F;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entity) {
        //NO-OP
    }

    @Override
    protected void collideWithNearbyEntities() {
        //NO-OP
    }

    @Override
    public boolean onLivingFall(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.SWEET_BERRY_BUSH || super.isInvulnerableTo(source);
    }
}
