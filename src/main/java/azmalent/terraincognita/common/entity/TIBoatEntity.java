package azmalent.terraincognita.common.entity;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModEntities;
import azmalent.terraincognita.common.init.blocksets.TIWoodType;
import azmalent.terraincognita.mixin.accessor.BoatEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class TIBoatEntity extends BoatEntity {
    private static final DataParameter<String> WOOD_TYPE = EntityDataManager.createKey(TIBoatEntity.class, DataSerializers.STRING);

    public TIBoatEntity(EntityType<TIBoatEntity> type, World world) {
        super(type, world);
        this.preventEntitySpawning = true;
    }

    public TIBoatEntity(World world, double x, double y, double z) {
        this(ModEntities.BOAT.get(), world);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public TIBoatEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.BOAT.get(), world);
    }

    @Override
    public void registerData() {
        super.registerData();
        this.dataManager.register(WOOD_TYPE, ModBlocks.WoodTypes.APPLE.name);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putString("Type", this.dataManager.get(WOOD_TYPE));
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        if (compound.contains("Type", Constants.NBT.TAG_STRING)) {
            String type = compound.getString("Type");
            dataManager.set(WOOD_TYPE, type);
        } else {
            this.setWoodType(ModBlocks.WoodTypes.APPLE);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName() {
        ITextComponent customName = getCustomName();
        return customName != null ? customName : new TranslationTextComponent("entity.minecraft.boat");
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        BoatEntityAccessor accessor = (BoatEntityAccessor) this;

        accessor.setLastYd(this.getMotion().y);
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (accessor.getStatus() != TIBoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.onLivingFall(this.fallDistance, 1.0F);
                    if (!this.world.isRemote && this.isAlive()) {
                        this.remove();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            for (int i = 0; i < 3; ++i) {
                                this.entityDropItem(this.getPlanks());
                            }

                            for (int j = 0; j < 2; ++j) {
                                this.entityDropItem(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.world.getFluidState((new BlockPos(this.getPositionVec())).down()).isTagged(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float) ((double) this.fallDistance - y);
            }
        }
    }

    private Item getPlanks() {
        BlockEntry planks = getWoodType().PLANKS;
        if (planks != null) return planks.getItem();

        return Items.OAK_PLANKS;
    }

    @Override
    public Item getItemBoat() {
        RegistryObject<Item> boat = this.getWoodType().BOAT;
        if (boat != null) return boat.get();

        return Items.OAK_BOAT;
    }

    public void setWoodType(TIWoodType type) {
        this.dataManager.set(WOOD_TYPE, type.name);
    }

    public TIWoodType getWoodType() {
        return ModBlocks.WoodTypes.byName(this.dataManager.get(WOOD_TYPE));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
