package azmalent.terraincognita.common.entity.butterfly;

import azmalent.terraincognita.common.ModDamageSources;
import azmalent.terraincognita.common.entity.IBottleableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class AbstractButterfly extends PathfinderMob implements FlyingAnimal, IBottleableEntity {
    public static final EntityDataAccessor<Float> SIZE_MODIFIER = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.FLOAT);

    protected int flyingTicks = 0;
    protected int underWaterTicks = 0;

    protected float wingRotation = 0.5f;
    protected float targetWingRotation = 0.5f;

    protected AbstractButterfly(EntityType<? extends AbstractButterfly> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;

        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putFloat("SizeModifier", getSizeModifier());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("SizeModifier")) {
            setSizeModifier(compound.getFloat("SizeModifier"));
        } else {
            setSizeModifier(getRandomSizeModifier());
        }
    }

    public static AttributeSupplier.Builder bakeAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 4.0D)
            .add(Attributes.FLYING_SPEED, 2.4F)
            .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level world) {
        FlyingPathNavigation navigator = new FlyingPathNavigation(this, world);

        navigator.setCanOpenDoors(false);
        navigator.setCanFloat(false);
        navigator.setCanPassDoors(true);

        return navigator;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, LevelReader level) {
        return level.isEmptyBlock(pos) ? 10.0F : 0.0F;
    }

    protected abstract float getRandomSizeModifier();

    public float getSizeModifier() {
        return entityData.get(SIZE_MODIFIER);
    }

    protected void setSizeModifier(float sizeModifier) {
        entityData.set(SIZE_MODIFIER, sizeModifier);
    }

    public boolean isTired() {
        return false;
    }

    public boolean isLanded() {
        return false;
    }

    public abstract float getWingRotation(float ageInTicks);

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (this.isInWaterOrBubble()) {
            this.underWaterTicks++;
            if (this.underWaterTicks > 20) {
                this.hurt(DamageSource.DROWN, 1.0F);
            }
        } else {
            this.underWaterTicks = 0;
        }
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        restrictTo(this.blockPosition(), 22);
        setSizeModifier(getRandomSizeModifier());

        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public abstract ResourceLocation getTexture();

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
        return false;
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    protected void checkFallDamage(double distance, boolean onGround, @Nonnull BlockState state, @Nonnull BlockPos pos) {
        //NO-OP
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
        //NO-OP
    }

    @Override
    protected void pushEntities() {
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
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height / 2f;
    }   @Nullable

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.SILVERFISH_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }
}
