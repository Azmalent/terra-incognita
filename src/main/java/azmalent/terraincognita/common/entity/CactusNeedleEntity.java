package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.common.registry.ModEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CactusNeedleEntity extends Projectile {
    private double damage = 1.5D;
    private int knockbackStrength;

    protected BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    protected int ticksInGround;
    public int arrowShake;

    public CactusNeedleEntity(EntityType<? extends CactusNeedleEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public CactusNeedleEntity(double x, double y, double z, Level worldIn) {
        this(ModEntities.CACTUS_NEEDLE.get(), worldIn);
        this.setPos(x, y, z);
    }

    public CactusNeedleEntity(LivingEntity shooter, Level worldIn) {
        this(shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ(), worldIn);
        this.setOwner(shooter);
    }

    public CactusNeedleEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) {
        this(ModEntities.CACTUS_NEEDLE.get(), world);
    }


    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getViewScale();
        return distance < d0 * d0;
    }

    @Override
    protected void defineSynchedData() {
        //NO-OP
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        this.ticksInGround = 0;
    }

    /**
     * Sets a target for the client to interpolate towards over the next few ticks
     */
    @OnlyIn(Dist.CLIENT)
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPos(x, y, z);
        this.setRot(yaw, pitch);
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @OnlyIn(Dist.CLIENT)
    public void lerpMotion(double x, double y, double z) {
        super.lerpMotion(x, y, z);
        this.ticksInGround = 0;
    }

    @SuppressWarnings("ConstantConditions")
    public void tick() {
        super.tick();
        Vec3 vector3d = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = Mth.sqrt(getHorizontalDistanceSqr(vector3d));
            this.yRot = (float)(Mth.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
            this.xRot = (float)(Mth.atan2(vector3d.y, f) * (double)(180F / (float)Math.PI));
            this.yRotO = this.yRot;
            this.xRotO = this.xRot;
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir(this.level, blockpos)) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vector3d1 = this.position();

                for(AABB axisalignedbb : voxelshape.toAabbs()) {
                    if (axisalignedbb.move(blockpos).contains(vector3d1)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.isInWaterOrRain()) {
            this.clearFire();
        }

        if (this.inGround) {
            if (this.inBlockState != blockstate && this.shouldFall()) {
                this.startFalling();
            } else if (!this.level.isClientSide) {
                ++this.ticksInGround;
                if (this.ticksInGround >= 1200) {
                    this.remove();
                }
            }

            ++this.timeInGround;
        } else {
            this.timeInGround = 0;
            Vec3 vector3d2 = this.position();
            Vec3 vector3d3 = vector3d2.add(vector3d);
            HitResult hit = this.level.clip(new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hit.getType() != HitResult.Type.MISS) {
                vector3d3 = hit.getLocation();
            }

            while(!this.removed) {
                EntityHitResult entityHit = this.rayTraceEntities(vector3d2, vector3d3);
                if (entityHit != null) {
                    hit = entityHit;
                }

                if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)hit).getEntity();
                    Entity entity1 = this.getOwner();
                    if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
                        hit = null;
                    }
                }

                if (hit != null && hit.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hit)) {
                    this.onHit(hit);
                    this.hasImpulse = true;
                }
            }

            vector3d = this.getDeltaMovement();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getX() + d3;
            double d1 = this.getY() + d4;
            double d2 = this.getZ() + d0;
            float f1 = Mth.sqrt(getHorizontalDistanceSqr(vector3d));
            this.yRot = (float)(Mth.atan2(d3, d0) * (double)(180F / (float)Math.PI));
            this.xRot = (float)(Mth.atan2(d4, f1) * (double)(180F / (float)Math.PI));
            this.yRot = lerpRotation(this.yRotO, this.yRot);
            this.xRot = lerpRotation(this.xRotO, this.xRot);

            float f2 = 0.99F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    this.level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = this.getWaterDrag();
            }

            this.setDeltaMovement(vector3d.scale(f2));
            if (!this.isNoGravity()) {
                Vec3 motion = this.getDeltaMovement();
                this.setDeltaMovement(motion.x, motion.y - (double)0.03F, motion.z);
            }

            this.setPos(d5, d1, d2);
            this.checkInsideBlocks();
        }
    }

    private boolean shouldFall() {
        return this.inGround && this.level.noCollision((new AABB(this.position(), this.position())).inflate(0.06D));
    }

    private void startFalling() {
        this.inGround = false;
        this.ticksInGround = 0;
        this.setDeltaMovement(getDeltaMovement().multiply(this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F));
    }

    protected void onHitBlock(BlockHitResult hit) {
        this.inBlockState = this.level.getBlockState(hit.getBlockPos());
        super.onHitBlock(hit);
        Vec3 vector3d = hit.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vec3 vector3d1 = vector3d.normalize().scale(0.05F);
        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.playSound(SoundEvents.ARROW_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.arrowShake = 7;
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityHitResult rayTraceEntities(Vec3 startVec, Vec3 endVec) {
        return ProjectileUtil.getEntityHitResult(this.level, this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    public void addAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
            compound.put("inBlockState", NbtUtils.writeBlockState(this.inBlockState));
        }

        compound.putByte("shake", (byte)this.arrowShake);
        compound.putBoolean("inGround", this.inGround);
        compound.putDouble("damage", this.damage);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.ticksInGround = compound.getShort("life");
        if (compound.contains("inBlockState", 10)) {
            this.inBlockState = NbtUtils.readBlockState(compound.getCompound("inBlockState"));
        }

        this.arrowShake = compound.getByte("shake") & 255;
        this.inGround = compound.getBoolean("inGround");
        if (compound.contains("damage", 99)) {
            this.damage = compound.getDouble("damage");
        }
    }

    protected boolean isMovementNoisy() {
        return false;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damageIn) {
        this.damage = damageIn;
    }

    public void setKnockbackStrength(int knockbackStrengthIn) {
        this.knockbackStrength = knockbackStrengthIn;
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean isAttackable() {
        return false;
    }

    protected float getEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.13F;
    }

    public void setEnchantmentEffectsFromEntity(LivingEntity living, float p_190547_2_) {
        int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, living);
        int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, living);
        //TODO
        this.setDamage((double)(p_190547_2_ * 2.0F) + this.random.nextGaussian() * 0.25D + (double)((float)this.level.getDifficulty().getId() * 0.11F));
        if (power > 0) {
            this.setDamage(this.damage + (double)power * 0.5D + 0.5D);
        }

        if (punch > 0) {
            this.setKnockbackStrength(punch);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, living) > 0) {
            this.setSecondsOnFire(100);
        }

    }

    protected float getWaterDrag() {
        return 0.6F;
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return null;
    }
}
