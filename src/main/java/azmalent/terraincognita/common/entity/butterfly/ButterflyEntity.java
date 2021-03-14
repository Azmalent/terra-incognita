package azmalent.terraincognita.common.entity.butterfly;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyRestGoal;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyLandOnFlowerGoal;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyWanderGoal;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ButterflyEntity extends AbstractButterflyEntity {
    public static final DataParameter<Integer> BUTTERFLY_TYPE = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> TIRED = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> LANDED = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);

    public static final Predicate<LivingEntity> SHOULD_AVOID = entity -> {
        if (!(entity instanceof PlayerEntity)) return false;
        PlayerEntity player = (PlayerEntity) entity;
        return !player.isCreative() && !player.isSpectator() && player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() != ModItems.WREATH.get();
    };

    public boolean hasPlayersNearby() {
        return !world.getEntitiesWithinAABB(PlayerEntity.class, getBoundingBox().expand(4, 4, 4), SHOULD_AVOID).isEmpty();
    }

    public ButterflyEntity(EntityType<ButterflyEntity> type, World world) {
        super(type, world);
    }

    public ButterflyEntity(World world, double x, double y, double z) {
        super(ModEntities.BUTTERFLY.get(), world);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public ButterflyEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(ModEntities.BUTTERFLY.get(), world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(BUTTERFLY_TYPE, 0);
        dataManager.register(TIRED, false);
        dataManager.register(LANDED, false);
        dataManager.register(SIZE_MODIFIER, 0.7f);
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);
        tag.putString("Type", getButterflyType().getName());
        tag.putBoolean("IsTired", isTired());
        tag.putBoolean("IsLanded", isLanded());
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);

        setButterflyType(Type.getTypeByName(tag.getString("Type")));
        setTired(tag.getBoolean("IsTired"));
        setLanded(tag.getBoolean("IsLanded"));
    }

    public ButterflyRestGoal restGoal;

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new AvoidEntityGoal<>(this, PlayerEntity.class, 4, 2, 2.4, SHOULD_AVOID));
        goalSelector.addGoal(1, restGoal = new ButterflyRestGoal(this));
        goalSelector.addGoal(2, new ButterflyLandOnFlowerGoal(this, 2.4, 16));
        goalSelector.addGoal(3, new ButterflyWanderGoal(this));
        goalSelector.addGoal(4, new SwimGoal(this));
    }

    @SuppressWarnings("deprecation")
    public static boolean canSpawn(EntityType<ButterflyEntity> butterfly, IWorld world, SpawnReason reason, BlockPos pos, Random randomIn) {
        return pos.getY() >= world.getSeaLevel() && world.getLightSubtracted(pos, 0) > 8;
    }

    public ButterflyEntity.Type getButterflyType() {
        return ButterflyEntity.Type.getTypeByIndex(dataManager.get(BUTTERFLY_TYPE));
    }

    private void setButterflyType(ButterflyEntity.Type type) {
        dataManager.set(BUTTERFLY_TYPE, type.getIndex());
    }

    @Override
    protected float getRandomSizeModifier() {
        return getButterflyType().getRandomSize(rand);
    }

    @Override
    public boolean isTired() {
        return dataManager.get(TIRED);
    }

    public void setTired(boolean tired) {
        dataManager.set(TIRED, tired);
    }

    @Override
    public boolean isLanded() {
        return dataManager.get(LANDED);
    }

    public void setLanded(boolean landed) {
        dataManager.set(LANDED, landed);
    }

    public void setNotLanded() {
        setLanded(false);
        this.setPositionAndUpdate(this.getPosition().getX() + 0.5D, this.getPosition().getY() + 0.5D, this.getPosition().getZ() + 0.5D);
    }

    @Override
    public float getWingRotation(float ageInTicks) {
        if (isLanded() && getMotion().lengthSquared() < 1.0E-7D) {
            if (!world.isNightTime() && rand.nextInt(80) == 0) {
                targetWingRotation = rand.nextFloat();
            }

            wingRotation = MathHelper.lerp(0.05f, wingRotation, targetWingRotation);
        } else {
            wingRotation = MathHelper.abs(MathHelper.cos(ageInTicks / 1.5f));
            targetWingRotation = wingRotation;
        }

        return wingRotation;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        if (this.isLanded()) {
            flyingTicks = 0;
            setMotion(Vector3d.ZERO);
        } else {
            flyingTicks++;

            if (flyingTicks > 200 && this.getRNG().nextInt(100) == 0) {
                this.setTired(true);
            }

            this.setMotion(this.getMotion().mul(1, 0.8, 1));
        }
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource damageSource, float damage) {
        boolean damaged = super.attackEntityFrom(damageSource, damage);
        if (damaged && this.isLanded()) {
            this.setNotLanded();
        }

        return damaged;
    }

    @Override
    public ResourceLocation getTexture() {
        return getButterflyType().getTexture();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        RegistryKey<Biome> biomeKey = BiomeUtil.getBiomeKey(worldIn, this.getPosition());
        Type type = Type.getRandomType(biomeKey, worldIn.getRandom());
        setButterflyType(type);

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ModItems.BUTTERFLY_SPAWN_EGG.get());
    }

    public enum Type {
        PEACOCK(0, "peacock", 0.6f, 0.8f),
        BRIMSTONE(1, "brimstone", 0.5f, 0.7f),
        CABBAGE_WHITE(2, "cabbage_white", 0.6f, 0.8f);

        private static final Type[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(Type::getIndex)).toArray(Type[]::new);
        private static final Map<String, Type> TYPES_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Type::getName, name -> name));

        private final int index;
        private final String name;
        private final ResourceLocation texture;
        private final float minSize;
        private final float maxSize;

        Type(int index, String name, float minSize, float maxSize) {
            assert maxSize >= minSize;

            this.index = index;
            this.name = name;
            this.texture = TerraIncognita.prefix("textures/entity/butterfly/" + name + ".png");
            this.minSize = minSize;
            this.maxSize = maxSize;
        }

        public String getName() {
            return this.name;
        }

        public int getIndex() {
            return this.index;
        }

        public ResourceLocation getTexture() {
            return texture;
        }

        public float getRandomSize(Random random) {
            return MathHelper.lerp(random.nextFloat(), minSize, maxSize);
        }

        public static Type getTypeByName(String nameIn) {
            return TYPES_BY_NAME.getOrDefault(nameIn, PEACOCK);
        }

        public static Type getTypeByIndex(int indexIn) {
            if (indexIn < 0 || indexIn > VALUES.length) {
                indexIn = 0;
            }

            return VALUES[indexIn];
        }

        public static Type getRandomType(RegistryKey<Biome> biome, Random random) {
            return VALUES[random.nextInt(VALUES.length)];
        }
    }
}
