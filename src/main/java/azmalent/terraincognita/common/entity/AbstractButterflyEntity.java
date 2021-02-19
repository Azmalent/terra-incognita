package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.common.entity.ai.ButterflyWanderGoal;
import azmalent.terraincognita.common.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class AbstractButterflyEntity extends CreatureEntity implements IFlyingAnimal {
    public static final DataParameter<Boolean> LANDED = EntityDataManager.createKey(AbstractButterflyEntity.class, DataSerializers.BOOLEAN);
    int flyingTicks = 0;

    protected static final EntityPredicate PLAYER_PREDICATE = new EntityPredicate().setDistance(4).setLineOfSiteRequired().allowInvulnerable().setCustomPredicate(
        entity -> entity instanceof PlayerEntity && isAfraidOfPlayer((PlayerEntity) entity)
    );

    protected static boolean isAfraidOfPlayer(PlayerEntity player) {
        if (player.isCreative()) return false;
        return ModItems.WREATH == null || player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() != ModItems.WREATH.get();
    }

    private int underWaterTicks = 0;

    protected AbstractButterflyEntity(EntityType<? extends AbstractButterflyEntity> type, World world) {
        super(type, world);
        this.preventEntitySpawning = true;

        this.moveController = new FlyingMovementController(this, 10, false);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(LANDED, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(8, new ButterflyWanderGoal(this));
        goalSelector.addGoal(9, new SwimGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 1.0D)
            .createMutableAttribute(Attributes.FLYING_SPEED, 0.6F)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);
        tag.putBoolean("Landed", dataManager.get(LANDED));
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);
        dataManager.set(LANDED, tag.getBoolean("Landed"));
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

        if (this.isLanded()) {
            flyingTicks = 0;

            if (this.world.getClosestPlayer(PLAYER_PREDICATE, this) != null || this.rand.nextInt(200) == 0) {
                this.setLanded(false);
            }
        } else {
            flyingTicks++;
            if (canSitOn(this.getPosition()) && flyingTicks > 600) {
                this.setLanded(true);
            }
        }
    }

    protected boolean canSitOn(BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isIn(BlockTags.SMALL_FLOWERS)) {
            return true;
        }

        if (state.isIn(BlockTags.TALL_FLOWERS) && state.getBlock() instanceof TallFlowerBlock) {
            return state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER;
        }

        return false;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource damageSource, float damage) {
        boolean damaged = super.attackEntityFrom(damageSource, damage);
        if (damaged && this.isLanded()) {
            this.setLanded(false);
        }

        return damaged;
    }

    public boolean isLanded() {
        return dataManager.get(LANDED);
    }

    protected void setLanded(boolean landed) {
        dataManager.set(LANDED, landed);
        if (landed) adjustLandedPosition();
    }

    private void adjustLandedPosition() {
        BlockPos pos = getPosition();
        BlockState state = world.getBlockState(pos);

        AxisAlignedBB aabb = state.getShape(world, pos).getBoundingBox();
        double x = pos.getX() + (aabb.maxX - aabb.minX) / 2;
        double y = pos.getY() + aabb.maxY;
        double z = pos.getZ() + (aabb.maxZ - aabb.minZ) / 2;
        setPosition(x, y, z);
    }

    public abstract ResourceLocation getTexture();

    protected abstract boolean isNocturnal();

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
