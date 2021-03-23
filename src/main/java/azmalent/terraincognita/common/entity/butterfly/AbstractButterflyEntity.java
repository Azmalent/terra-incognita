package azmalent.terraincognita.common.entity.butterfly;

import azmalent.terraincognita.common.ModDamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractButterflyEntity extends CreatureEntity implements IFlyingAnimal {
    public static final DataParameter<Float> SIZE_MODIFIER = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.FLOAT);

    protected int flyingTicks = 0;
    protected int underWaterTicks = 0;

    protected float wingRotation = 0.5f;
    protected float targetWingRotation = 0.5f;

    protected AbstractButterflyEntity(EntityType<? extends AbstractButterflyEntity> type, World world) {
        super(type, world);
        this.preventEntitySpawning = true;

        this.moveController = new FlyingMovementController(this, 10, false);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putFloat("SizeModifier", getSizeModifier());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("SizeModifier")) {
            setSizeModifier(compound.getFloat("SizeModifier"));
        } else {
            setSizeModifier(getRandomSizeModifier());
        }
    }

    public static AttributeModifierMap.MutableAttribute bakeAttributes() {
        return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 4.0D)
            .createMutableAttribute(Attributes.FLYING_SPEED, 2.4F)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Nonnull
    @Override
    protected PathNavigator createNavigator(@Nonnull World world) {
        FlyingPathNavigator navigator = new FlyingPathNavigator(this, world);

        navigator.setCanOpenDoors(false);
        navigator.setCanSwim(false);
        navigator.setCanEnterDoors(true);

        return navigator;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.isAirBlock(pos) ? 10.0F : 0.0F;
    }

    protected abstract float getRandomSizeModifier();

    public float getSizeModifier() {
        return dataManager.get(SIZE_MODIFIER);
    }

    protected void setSizeModifier(float sizeModifier) {
        dataManager.set(SIZE_MODIFIER, sizeModifier);
    }

    public boolean isTired() {
        return false;
    }

    public boolean isLanded() {
        return false;
    }

    public abstract float getWingRotation(float ageInTicks);

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        if (this.isInWaterOrBubbleColumn()) {
            this.underWaterTicks++;
            if (this.underWaterTicks > 20) {
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            }
        } else {
            this.underWaterTicks = 0;
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        setHomePosAndDistance(this.getPosition(), 22);
        setSizeModifier(getRandomSizeModifier());

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public abstract ResourceLocation getTexture();

    @Override
    protected boolean makeFlySound() {
        return false;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
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
    protected void updateFallState(double distance, boolean onGround, @Nonnull BlockState state, @Nonnull BlockPos pos) {
        //NO-OP
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
    public boolean isInvulnerableTo(@Nonnull DamageSource source) {
        return source == DamageSource.SWEET_BERRY_BUSH
            || source == DamageSource.CACTUS
            || source == ModDamageSources.CALTROPS
            || super.isInvulnerableTo(source);
    }

    @Nonnull
    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return size.height / 2f;
    }   @Nullable

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SILVERFISH_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SILVERFISH_DEATH;
    }
}
