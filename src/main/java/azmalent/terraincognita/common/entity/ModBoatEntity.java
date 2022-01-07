package azmalent.terraincognita.common.entity;

import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.mixin.accessor.BoatEntityAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class ModBoatEntity extends Boat {
    private static final EntityDataAccessor<String> WOOD_TYPE = SynchedEntityData.defineId(ModBoatEntity.class, EntityDataSerializers.STRING);

    public ModBoatEntity(EntityType<ModBoatEntity> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public ModBoatEntity(Level world, double x, double y, double z) {
        this(ModEntities.BOAT.get(), world);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public ModBoatEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) {
        this(ModEntities.BOAT.get(), world);
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
        if (compound.contains("Type", Constants.NBT.TAG_STRING)) {
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
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        BoatEntityAccessor accessor = (BoatEntityAccessor) this;

        accessor.ti_setLastYd(this.getDeltaMovement().y);
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (accessor.ti_getStatus() != ModBoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F);
                    if (!this.level.isClientSide && this.isAlive()) {
                        this.remove();
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
        ModWoodType woodType = getWoodType();
        if (woodType != null) return woodType.PLANKS.getItem();

        return Items.OAK_PLANKS;
    }

    @Nonnull
    @Override
    public Item getDropItem() {
        ModWoodType woodType = getWoodType();
        if (woodType != null) return woodType.BOAT.get();

        return Items.OAK_BOAT;
    }

    public void setWoodType(ModWoodType type) {
        this.entityData.set(WOOD_TYPE, type.name);
    }

    public ModWoodType getWoodType() {
        return ModWoodTypes.byName(this.entityData.get(WOOD_TYPE));
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
