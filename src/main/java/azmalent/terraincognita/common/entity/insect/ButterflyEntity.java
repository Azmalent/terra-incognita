package azmalent.terraincognita.common.entity;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.ai.ButterflySitOnFlowerGoal;
import azmalent.terraincognita.common.entity.ai.ButterflyWanderGoal;
import azmalent.terraincognita.common.init.ModEntities;
import azmalent.terraincognita.common.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
import java.util.stream.Collectors;

public class ButterflyEntity extends AbstractButterflyEntity {
    public static final DataParameter<Integer> BUTTERFLY_TYPE = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> TIRED = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> LANDED = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);

    private BlockState landedFlowerState = null;
    private int landedTicks = 0;

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
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);
        tag.putString("Type", getButterflyType().getName());
        tag.putBoolean("IsTired", dataManager.get(TIRED));
        tag.putBoolean("IsLanded", dataManager.get(LANDED));
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);
        setButterflyType(Type.getTypeByName(tag.getString("Type")));
        setTired(tag.getBoolean("IsTired"));
        setLanded(tag.getBoolean("IsLanded"));
    }

    @Override
    protected void registerGoals() {
        //goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PlayerEntity.class, 6f, 1f, 1.2f, living -> shouldAvoidPlayer((PlayerEntity) living)));
        goalSelector.addGoal(1, new ButterflySitOnFlowerGoal(this, 8, 8));
        goalSelector.addGoal(2, new ButterflyWanderGoal(this));
        goalSelector.addGoal(3, new SwimGoal(this));
    }

    @SuppressWarnings("deprecation")
    public static boolean canSpawn(EntityType<ButterflyEntity> butterfly, IWorld world, SpawnReason reason, BlockPos pos, Random randomIn) {
        if (pos.getY() < world.getSeaLevel() || !world.canBlockSeeSky(pos)) {
            return false;
        }

        int light = world.getLight(pos);
        return (world.getBlockState(pos).isIn(BlockTags.FLOWERS) || world.getBlockState(pos.down()).isIn(Blocks.GRASS)) && light > 7;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    public ButterflyEntity.Type getButterflyType() {
        return ButterflyEntity.Type.getTypeByIndex(dataManager.get(BUTTERFLY_TYPE));
    }

    private void setButterflyType(ButterflyEntity.Type type) {
        dataManager.set(BUTTERFLY_TYPE, type.getIndex());
    }

    public boolean isTired() {
        return dataManager.get(TIRED);
    }

    private void setTired(boolean tired) {
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
    protected void updateAITasks() {
        super.updateAITasks();

        if (this.isLanded()) {
            flyingTicks = 0;
            landedTicks++;

            if (flowerStateUpdated() || world.getClosestPlayer(PLAYER_PREDICATE, this) != null) {
                this.setNotLanded();
            }
            else if (!world.isNightTime() && landedTicks > 200 && this.rand.nextInt(200) == 0) {
                this.setNotLanded();
                this.setTired(false);
            }
        } else {
            landedTicks = 0;
            flyingTicks++;

            if (flyingTicks > 200 && this.getRNG().nextInt(100) == 0) {
                this.setTired(true);
            }

            //Prevent flying too high up
            this.setMotion(this.getMotion().mul(1, 0.6, 1));
        }
    }

    private boolean flowerStateUpdated() {
        if (landedFlowerState != null) {
            return landedFlowerState != world.getBlockState(this.getPosition());
        } else {
            landedFlowerState = world.getBlockState(this.getPosition());
        }

        return false;
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
        PEACOCK(0, "peacock"),
        BRIMSTONE(1, "brimstone"),
        CABBAGE_WHITE(2, "cabbage_white");

        private static final Type[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(Type::getIndex)).toArray(Type[]::new);
        private static final Map<String, Type> TYPES_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Type::getName, name -> name));

        private final int index;
        private final String name;
        private final ResourceLocation texture;

        Type(int index, String name) {
            this.index = index;
            this.name = name;
            this.texture = TerraIncognita.prefix("textures/entity/butterfly/" + name + ".png");
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
