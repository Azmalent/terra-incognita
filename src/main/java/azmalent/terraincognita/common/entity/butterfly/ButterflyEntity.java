package azmalent.terraincognita.common.entity.butterfly;

import azmalent.cuneiform.lib.collections.WeightedList;
import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyLandOnFlowerGoal;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyRestGoal;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyWanderGoal;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ButterflyEntity extends AbstractButterflyEntity {
    public static final DataParameter<Integer> BUTTERFLY_TYPE = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> TIRED = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> LANDED = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> FROM_BOTTLE = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.BOOLEAN);

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
        dataManager.register(FROM_BOTTLE, false);
        dataManager.register(SIZE_MODIFIER, 0.7f);
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);

        tag.putString("Type", getButterflyType().getName());
        tag.putBoolean("IsTired", isTired());
        tag.putBoolean("IsLanded", isLanded());
        tag.putBoolean("IsFromBottle", isFromBottle());
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);

        setButterflyType(Type.getTypeByName(tag.getString("Type")));
        setTired(tag.getBoolean("IsTired"));
        setLanded(tag.getBoolean("IsLanded"));
        setFromBottle(tag.getBoolean("IsFromBottle"));
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
        return world.getBlockState(pos.down()).getBlock() instanceof GrassBlock
            && pos.getY() >= world.getSeaLevel()
            && world.getLightSubtracted(pos, 0) > 8;
    }

    public boolean preventDespawn() {
        return super.preventDespawn() || isFromBottle();
    }


    public ButterflyEntity.Type getButterflyType() {
        return ButterflyEntity.Type.getTypeByIndex(dataManager.get(BUTTERFLY_TYPE));
    }

    private void setButterflyType(ButterflyEntity.Type type) {
        dataManager.set(BUTTERFLY_TYPE, type.getIndex());
    }

    public TranslationTextComponent getTypeDisplayName() {
        String key = getButterflyType().getTranslationKey();
        return new TranslationTextComponent(key);
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

    private void setFromBottle(boolean fromBottle) {
        dataManager.set(FROM_BOTTLE, fromBottle);
    }

    private boolean isFromBottle() {
        return dataManager.get(FROM_BOTTLE);
    }

    @Override
    public float getWingRotation(float ageInTicks) {
        if (isLanded() && getMotion().lengthSquared() < 1.0E-7D) {
            if (world.isDaytime() && rand.nextInt(100) == 0) {
                targetWingRotation = rand.nextFloat();
            }

            wingRotation = MathHelper.lerp(0.05f, wingRotation, targetWingRotation);
        } else {
            wingRotation = MathHelper.abs(MathHelper.cos(ageInTicks / 1.5f));
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

            if (flyingTicks > 600 && this.getRNG().nextInt(200) == 0) {
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

    //IBottleableEntity implementation
    @Override
    public Item getBottledItem() {
        return ModItems.BOTTLED_BUTTERFLY.get();
    }

    @Override
    public void onUnbottled() {
        setNotLanded();
        setTired(false);
        setFromBottle(true);
    }

    public static void addBottleTooltip(CompoundNBT nbt, List<ITextComponent> tooltip) {
        String type = nbt.getString("Type");
        String key = Type.getTypeByName(type).getTranslationKey();
        tooltip.add(new TranslationTextComponent(key).mergeStyle(TextFormatting.GRAY));
    }

    public enum Type {
        PEACOCK(0, "peacock", 0.6f, 0.15f),
        BRIMSTONE(1, "brimstone", 0.5f, 0.1f),
        CABBAGE_WHITE(2, "cabbage_white", 0.6f, 0.15f),
        COMMON_BLUE(3, "common_blue", 0.5f, 0.1f),
        ORANGE_TIP(4, "orange_tip", 0.5f, 0.15f),
        MONARCH(5, "monarch", 0.7f, 0.15f),
        WHITE_ADMIRAL(6, "white_admiral", 0.6f, 0.1f),
        RED_ADMIRAL(7, "red_admiral", 0.6f, 0.15f),
        SPECKLED_WOOD(8, "speckled_wood", 0.5f, 0.1f),
        PURPLE_EMPEROR(9, "purple_emperor", 0.7f, 0.15f);

        private static final Type[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(Type::getIndex)).toArray(Type[]::new);
        private static final Map<String, Type> TYPES_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Type::getName, name -> name));

        private static final WeightedList<Type> COMMON_TYPES = new WeightedList<>();
        private static final WeightedList<Type> PLAINS_TYPES = new WeightedList<>();
        private static final WeightedList<Type> FOREST_TYPES = new WeightedList<>();

        static {
            COMMON_TYPES.add(CABBAGE_WHITE, 5);
            COMMON_TYPES.add(PEACOCK, 4);
            COMMON_TYPES.add(MONARCH, 3);
            COMMON_TYPES.add(RED_ADMIRAL, 2);

            PLAINS_TYPES.add(BRIMSTONE, 5);
            PLAINS_TYPES.add(COMMON_BLUE, 4);
            PLAINS_TYPES.add(ORANGE_TIP, 1);

            FOREST_TYPES.add(WHITE_ADMIRAL, 5);
            FOREST_TYPES.add(SPECKLED_WOOD, 4);
            FOREST_TYPES.add(PURPLE_EMPEROR, 1);
        }

        private final int index;
        private final String name;
        private final ResourceLocation texture;
        private final float averageSize;
        private final float sizeVariation;

        Type(int index, String name, float averageSize, float sizeVariation) {
            assert averageSize >= sizeVariation;

            this.index = index;
            this.name = name;
            this.texture = TerraIncognita.prefix("textures/entity/butterfly/" + name + ".png");
            this.averageSize = averageSize;
            this.sizeVariation = sizeVariation;
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

        public String getTranslationKey() {
            return ModEntities.BUTTERFLY.get().getTranslationKey() + "." + this.name;
        }

        public float getRandomSize(Random random) {
            return MathHelper.lerp(random.nextFloat(), averageSize - sizeVariation, averageSize + sizeVariation);
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

        @SuppressWarnings("ConstantConditions")
        public static Type getRandomType(RegistryKey<Biome> biomeKey, Random random) {
            if (random.nextFloat() < 0.66) {
                Biome biome = ForgeRegistries.BIOMES.getValue(biomeKey.getLocation());
                Biome.Category category = WorldGenUtil.getProperBiomeCategory(biome);
                switch (category) {
                    case PLAINS:
                        return PLAINS_TYPES.getRandomItem(random);
                    case FOREST:
                        return FOREST_TYPES.getRandomItem(random);
                }
            }

            return COMMON_TYPES.getRandomItem(random);
        }
    }
}
