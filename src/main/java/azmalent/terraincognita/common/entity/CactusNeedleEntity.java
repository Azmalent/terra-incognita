package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.common.registry.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CactusNeedleEntity extends ProjectileEntity {
    private double damage = 1.5D;
    private int knockbackStrength;

    protected BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    protected int ticksInGround;
    public int arrowShake;

    public CactusNeedleEntity(EntityType<? extends CactusNeedleEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public CactusNeedleEntity(double x, double y, double z, World worldIn) {
        this(ModEntities.CACTUS_NEEDLE.get(), worldIn);
        this.setPosition(x, y, z);
    }

    public CactusNeedleEntity(LivingEntity shooter, World worldIn) {
        this(shooter.getPosX(), shooter.getPosYEye() - (double)0.1F, shooter.getPosZ(), worldIn);
        this.setShooter(shooter);
    }

    public CactusNeedleEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.CACTUS_NEEDLE.get(), world);
    }


    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    @Override
    protected void registerData() {
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
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z) {
        super.setVelocity(x, y, z);
        this.ticksInGround = 0;
    }

    @SuppressWarnings("ConstantConditions")
    public void tick() {
        super.tick();
        Vector3d vector3d = this.getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(horizontalMag(vector3d));
            this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, f) * (double)(180F / (float)Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = this.getPosition();
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (!blockstate.isAir(this.world, blockpos)) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                Vector3d vector3d1 = this.getPositionVec();

                for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
                    if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.isWet()) {
            this.extinguish();
        }

        if (this.inGround) {
            if (this.inBlockState != blockstate && this.func_234593_u_()) {
                this.func_234594_z_();
            } else if (!this.world.isRemote) {
                ++this.ticksInGround;
                if (this.ticksInGround >= 1200) {
                    this.remove();
                }
            }

            ++this.timeInGround;
        } else {
            this.timeInGround = 0;
            Vector3d vector3d2 = this.getPositionVec();
            Vector3d vector3d3 = vector3d2.add(vector3d);
            RayTraceResult hit = this.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (hit.getType() != RayTraceResult.Type.MISS) {
                vector3d3 = hit.getHitVec();
            }

            while(!this.removed) {
                EntityRayTraceResult entityHit = this.rayTraceEntities(vector3d2, vector3d3);
                if (entityHit != null) {
                    hit = entityHit;
                }

                if (hit != null && hit.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)hit).getEntity();
                    Entity entity1 = this.func_234616_v_();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
                        hit = null;
                    }
                }

                if (hit != null && hit.getType() != RayTraceResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hit)) {
                    this.onImpact(hit);
                    this.isAirBorne = true;
                }
            }

            vector3d = this.getMotion();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;
            float f1 = MathHelper.sqrt(horizontalMag(vector3d));
            this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(d4, f1) * (double)(180F / (float)Math.PI));
            this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
            this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);

            float f2 = 0.99F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = this.getWaterDrag();
            }

            this.setMotion(vector3d.scale(f2));
            if (!this.hasNoGravity()) {
                Vector3d motion = this.getMotion();
                this.setMotion(motion.x, motion.y - (double)0.03F, motion.z);
            }

            this.setPosition(d5, d1, d2);
            this.doBlockCollisions();
        }
    }

    private boolean func_234593_u_() {
        return this.inGround && this.world.hasNoCollisions((new AxisAlignedBB(this.getPositionVec(), this.getPositionVec())).grow(0.06D));
    }

    private void func_234594_z_() {
        this.inGround = false;
        this.ticksInGround = 0;
        this.setMotion(getMotion().mul(this.rand.nextFloat() * 0.2F, this.rand.nextFloat() * 0.2F, this.rand.nextFloat() * 0.2F));
    }

    protected void func_230299_a_(BlockRayTraceResult hit) {
        this.inBlockState = this.world.getBlockState(hit.getPos());
        super.func_230299_a_(hit);
        Vector3d vector3d = hit.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setMotion(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
        this.setRawPosition(this.getPosX() - vector3d1.x, this.getPosY() - vector3d1.y, this.getPosZ() - vector3d1.z);
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.arrowShake = 7;
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), this::func_230298_a_);
    }

    public void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
            compound.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }

        compound.putByte("shake", (byte)this.arrowShake);
        compound.putBoolean("inGround", this.inGround);
        compound.putDouble("damage", this.damage);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.ticksInGround = compound.getShort("life");
        if (compound.contains("inBlockState", 10)) {
            this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
        }

        this.arrowShake = compound.getByte("shake") & 255;
        this.inGround = compound.getBoolean("inGround");
        if (compound.contains("damage", 99)) {
            this.damage = compound.getDouble("damage");
        }
    }

    protected boolean canTriggerWalking() {
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
    public boolean canBeAttackedWithItem() {
        return false;
    }

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.13F;
    }

    public void setEnchantmentEffectsFromEntity(LivingEntity living, float p_190547_2_) {
        int power = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, living);
        int punch = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, living);
        //TODO
        this.setDamage((double)(p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getId() * 0.11F));
        if (power > 0) {
            this.setDamage(this.damage + (double)power * 0.5D + 0.5D);
        }

        if (punch > 0) {
            this.setKnockbackStrength(punch);
        }

        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, living) > 0) {
            this.setFire(100);
        }

    }

    protected float getWaterDrag() {
        return 0.6F;
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }
}
