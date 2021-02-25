package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.common.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractButterflyEntity extends CreatureEntity implements IFlyingAnimal {
    protected int flyingTicks = 0;
    protected int underWaterTicks = 0;

    protected static final EntityPredicate PLAYER_PREDICATE = new EntityPredicate().setDistance(4).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks().setCustomPredicate(
        entity -> entity instanceof PlayerEntity && shouldAvoidPlayer((PlayerEntity) entity)
    );

    public static boolean shouldAvoidPlayer(PlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) return false;
        return ModItems.WREATH == null || player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() != ModItems.WREATH.get();
    }

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

    public static AttributeModifierMap.MutableAttribute bakeAttributes() {
        return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 4.0D)
            .createMutableAttribute(Attributes.FLYING_SPEED, 0.6F)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Nonnull
    @Override
    protected PathNavigator createNavigator(@Nonnull World world) {
        FlyingPathNavigator navigator = new FlyingPathNavigator(this, world) {
            @Override
            public boolean canEntityStandOnPos(@Nonnull BlockPos pos) {
                return world.getBlockState(pos).isIn(BlockTags.FLOWERS);
            }
        };

        navigator.setCanOpenDoors(false);
        navigator.setCanSwim(false);
        navigator.setCanEnterDoors(true);

        return navigator;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.isAirBlock(pos) ? 10.0F : 0.0F;
    }

    public boolean isLanded() {
        return false;
    }

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
        return source == DamageSource.SWEET_BERRY_BUSH || source == DamageSource.CACTUS || super.isInvulnerableTo(source);
    }

    @Nonnull
    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return size.height / 2f;
    }
}
