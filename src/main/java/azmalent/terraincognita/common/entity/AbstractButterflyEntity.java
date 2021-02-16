package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.entity.ai.ButterflyWanderGoal;
import azmalent.terraincognita.common.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.SwimGoal;
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
import net.minecraft.state.properties.Half;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractButterflyEntity extends CreatureEntity implements IFlyingAnimal {
    public static final DataParameter<Integer> LANDED_DIRECTION = EntityDataManager.createKey(AbstractButterflyEntity.class, DataSerializers.VARINT);
    protected static final EntityPredicate PLAYER_PREDICATE = new EntityPredicate().setDistance(4).setLineOfSiteRequired().allowInvulnerable().setCustomPredicate(
        entity -> entity instanceof PlayerEntity && isScaredOfPlayer((PlayerEntity) entity)
    );

    protected static boolean isScaredOfPlayer(PlayerEntity player) {
        if (player.isCreative()) return false;
        if (ModItems.WREATH != null && player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == ModItems.WREATH.get()) {
            return false;
        }

        return true;
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
        dataManager.register(LANDED_DIRECTION, -1);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(8, new ButterflyWanderGoal(this));
        goalSelector.addGoal(9, new SwimGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 3.0D)
            .createMutableAttribute(Attributes.FLYING_SPEED, 0.6F)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);
        tag.putInt("LandedDirection", dataManager.get(LANDED_DIRECTION));
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);
        dataManager.set(LANDED_DIRECTION, tag.getInt("LandedDirection"));
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
            if (this.world.getClosestPlayer(PLAYER_PREDICATE, this) != null || !this.canSit()) {
                this.setNotLanded();
            }
        }
    }

    protected BlockPos getSittingPosition() {
        BlockPos pos = getPosition();
        Direction direction = getLandedDirection();
        if (direction == Direction.DOWN) {
            BlockState state = world.getBlockState(pos);
            if (state.isIn(BlockTags.FLOWERS)) return pos;
        }

        return pos.offset(direction);
    }

    protected boolean canSit() {
        return this.canSitOn(getSittingPosition(), getLandedDirection());
    }

    protected boolean canSitOn(BlockPos pos, Direction direction) {
        if (direction == Direction.UP) return false;

        BlockState state = world.getBlockState(pos);
        if (direction == Direction.DOWN) {
            if (state.isIn(BlockTags.FENCES)) {
                return world.isAirBlock(pos.up());
            }

            if (state.isIn(BlockTags.SMALL_FLOWERS)) {
                return true;
            }

            if (state.isIn(BlockTags.TALL_FLOWERS) && state.getBlock() instanceof TallFlowerBlock) {
                if (state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER) {
                    return true;
                }
            }
        }

        return state.isSolidSide(world, pos, direction.getOpposite()) && world.isAirBlock(pos.offset(direction));
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource damageSource, float damage) {
        boolean damaged = super.attackEntityFrom(damageSource, damage);
        if (damaged && this.isLanded()) {
            this.setNotLanded();
        }

        return damaged;
    }

    public boolean isLanded() {
        return dataManager.get(LANDED_DIRECTION) != -1;
    }

    protected void setNotLanded() {
        dataManager.set(LANDED_DIRECTION, -1);
    }

    protected Direction getLandedDirection() {
        return Direction.byIndex(dataManager.get(LANDED_DIRECTION));
    }

    protected void setLandedDirection(Direction direction) {
        if (direction == Direction.UP) {
            this.setNotLanded();
        } else {
            dataManager.set(LANDED_DIRECTION, direction.getIndex());
        }
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
