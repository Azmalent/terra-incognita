package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.mixin.accessor.BoatEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class TIBoat extends Boat {
    private static final EntityDataAccessor<String> WOOD_TYPE = SynchedEntityData.defineId(TIBoat.class, EntityDataSerializers.STRING);

    public TIBoat(EntityType<TIBoat> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public TIBoat(Level world, double x, double y, double z) {
        this(ModEntities.BOAT.get(), world);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public TIBoat(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(ModEntities.BOAT.get(), level);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WOOD_TYPE, ModWoodTypes.APPLE.name);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("Type", this.entityData.get(WOOD_TYPE));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Type", Tag.TAG_STRING)) {
            String type = compound.getString("Type");
            entityData.set(WOOD_TYPE, type);
        } else {
            this.setWoodType(ModWoodTypes.APPLE);
        }
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getName() {
        Component customName = getCustomName();
        return customName != null ? customName : new TranslatableComponent("entity.minecraft.boat");
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
        BoatEntityAccessor accessor = (BoatEntityAccessor) this;

        accessor.ti_setLastYd(this.getDeltaMovement().y);
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (accessor.ti_getStatus() != TIBoat.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
                    if (!this.level.isClientSide && this.isAlive()) {
                        this.kill();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for (int i = 0; i < 3; ++i) {
                                this.spawnAtLocation(this.getPlanks());
                            }

                            for (int j = 0; j < 2; ++j) {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.level.getFluidState((new BlockPos(this.position())).below()).is(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float) ((double) this.fallDistance - y);
            }
        }
    }

    private Item getPlanks() {
        TIWoodType woodType = getWoodType();
        if (woodType != null) return woodType.PLANKS.asItem();

        return Items.OAK_PLANKS;
    }

    @Nonnull
    @Override
    public Item getDropItem() {
        TIWoodType woodType = getWoodType();
        if (woodType != null) return woodType.BOAT.get();

        return Items.OAK_BOAT;
    }

    public void setWoodType(TIWoodType type) {
        this.entityData.set(WOOD_TYPE, type.name);
    }

    public TIWoodType getWoodType() {
        return ModWoodTypes.byName(this.entityData.get(WOOD_TYPE));
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
