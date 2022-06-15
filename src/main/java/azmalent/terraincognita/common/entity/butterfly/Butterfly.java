package azmalent.terraincognita.common.entity.butterfly;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.ModBiomeTags;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyLandOnFlowerGoal;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyRestGoal;
import azmalent.terraincognita.common.entity.butterfly.ai.ButterflyWanderGoal;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Butterfly extends AbstractButterfly {
    public static final EntityDataAccessor<Integer> BUTTERFLY_TYPE = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> TIRED = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> LANDED = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);

    public static final Predicate<LivingEntity> SHOULD_AVOID = entity -> {
        if (entity instanceof Player player) {
            return !player.isCreative() && !player.isSpectator() && player.getItemBySlot(EquipmentSlot.HEAD).getItem() != ModItems.WREATH.get();
        }

        return false;
    };

    public Butterfly(EntityType<Butterfly> type, Level world) {
        super(type, world);
    }

    @SuppressWarnings("unused")
    public Butterfly(Level world, double x, double y, double z) {
        super(ModEntities.BUTTERFLY.get(), world);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @SuppressWarnings("unused")
    public Butterfly(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntities.BUTTERFLY.get(), world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        entityData.define(BUTTERFLY_TYPE, 0);
        entityData.define(TIRED, false);
        entityData.define(LANDED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putString("Type", getButterflyType().getName());
        tag.putBoolean("IsTired", isTired());
        tag.putBoolean("IsLanded", isLanded());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        setButterflyType(Type.getTypeByName(tag.getString("Type")));
        setTired(tag.getBoolean("IsTired"));
        setLanded(tag.getBoolean("IsLanded"));
    }

    public ButterflyRestGoal restGoal;

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 4, 2, 2.4, SHOULD_AVOID));
        goalSelector.addGoal(1, restGoal = new ButterflyRestGoal(this));
        goalSelector.addGoal(2, new ButterflyLandOnFlowerGoal(this, 2.4, 16));
        goalSelector.addGoal(3, new ButterflyWanderGoal(this));
        goalSelector.addGoal(4, new FloatGoal(this));
    }

    @SuppressWarnings({"deprecation", "unused"})
    public static boolean canSpawn(EntityType<Butterfly> butterfly, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return pos.getY() >= world.getSeaLevel() && world.getRawBrightness(pos, 0) > 8;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || isFromBottle();
    }


    public Butterfly.Type getButterflyType() {
        return Butterfly.Type.getTypeByIndex(entityData.get(BUTTERFLY_TYPE));
    }

    private void setButterflyType(Butterfly.Type type) {
        entityData.set(BUTTERFLY_TYPE, type.getIndex());
    }

    public TranslatableComponent getTypeDisplayName() {
        String key = getButterflyType().getTranslationKey();
        return new TranslatableComponent(key);
    }

    @Override
    protected float getRandomSizeModifier() {
        return getButterflyType().getRandomSize(random);
    }

    @Override
    public boolean isTired() {
        return entityData.get(TIRED);
    }

    public void setTired(boolean tired) {
        entityData.set(TIRED, tired);
    }

    @Override
    public boolean isLanded() {
        return entityData.get(LANDED);
    }

    public void setLanded(boolean landed) {
        entityData.set(LANDED, landed);
    }

    public void setNotLanded() {
        setLanded(false);
        this.teleportTo(this.blockPosition().getX() + 0.5D, this.blockPosition().getY() + 0.5D, this.blockPosition().getZ() + 0.5D);
    }

    @Override
    public float getWingRotation(float ageInTicks) {
        if (isLanded() && getDeltaMovement().lengthSqr() < 1.0E-7D) {
            if (level.isDay() && random.nextInt(100) == 0) {
                targetWingRotation = random.nextFloat();
            }

            wingRotation = Mth.lerp(0.05f, wingRotation, targetWingRotation);
        } else {
            wingRotation = Mth.abs(Mth.cos(ageInTicks / 1.5f));
        }

        return wingRotation;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (this.isLanded()) {
            flyingTicks = 0;
            setDeltaMovement(Vec3.ZERO);
        } else {
            flyingTicks++;

            if (flyingTicks > 600 && this.getRandom().nextInt(200) == 0) {
                this.setTired(true);
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply(1, 0.8, 1));
        }
    }

    @Override
    public boolean hurt(@Nonnull DamageSource damageSource, float damage) {
        boolean damaged = super.hurt(damageSource, damage);
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
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @NotNull SpawnGroupData spawnDataIn, @NotNull CompoundTag dataTag) {
        Holder<Biome> biome = level.getBiome(this.blockPosition());
        Type type = Type.getRandomType(biome, level.getRandom());
        setButterflyType(type);

        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(ModItems.BUTTERFLY_SPAWN_EGG.get());
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    //IBottleableEntity implementation
    @Override
    public Item getBottledItem() {
        return ModItems.BOTTLED_BUTTERFLY.get();
    }

    @Override
    public void onReleasedFromBottle() {
        setNotLanded();
        setTired(false);
        setFromBottle(true);
    }

    public static void addBottleTooltip(CompoundTag nbt, List<Component> tooltip) {
        String type = nbt.getString("Type");
        String key = Type.getTypeByName(type).getTranslationKey();
        tooltip.add(new TranslatableComponent(key).withStyle(ChatFormatting.GRAY));
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
        PURPLE_EMPEROR(9, "purple_emperor", 0.7f, 0.15f),
        RINGLET(10, "ringlet", 0.7f, 0.15f),
        SWALLOWTAIL(11, "swallowtail", 0.7f, 0.15f),
        MOURNING_CLOAK(12, "mourning_cloak", 0.7f, 0.15f);

        private static final Type[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(Type::getIndex)).toArray(Type[]::new);
        private static final Map<String, Type> TYPES_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Type::getName, name -> name));

        private static final SimpleWeightedRandomList<Type> COMMON_TYPES = SimpleWeightedRandomList.<Type>builder()
            .add(CABBAGE_WHITE, 5)
            .add(PEACOCK, 4)
            .add(MONARCH, 3)
            .add(RED_ADMIRAL, 2)
            .build();

        private static final SimpleWeightedRandomList<Type> PLAINS_TYPES = SimpleWeightedRandomList.<Type>builder()
            .add(BRIMSTONE, 5)
            .add(COMMON_BLUE, 4)
            .add(ORANGE_TIP, 1)
            .build();

        private static final SimpleWeightedRandomList<Type> FOREST_TYPES = SimpleWeightedRandomList.<Type>builder()
            .add(WHITE_ADMIRAL, 5)
            .add(SPECKLED_WOOD, 4)
            .add(PURPLE_EMPEROR, 1)
            .build();

        private static final SimpleWeightedRandomList<Type> MOUNTAIN_TYPES = SimpleWeightedRandomList.<Type>builder()
            .add(RINGLET, 5)
            .add(SWALLOWTAIL, 3)
            .add(MOURNING_CLOAK, 1)
            .build();

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
            return ModEntities.BUTTERFLY.get().getDescriptionId() + "." + this.name;
        }

        public float getRandomSize(Random random) {
            return Mth.lerp(random.nextFloat(), averageSize - sizeVariation, averageSize + sizeVariation);
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

        public static Type getRandomType(Holder<Biome> biome, Random random) {
            SimpleWeightedRandomList<Type> list = COMMON_TYPES;
            if (random.nextDouble() < 0.66) {
                if (biome.is(ModBiomeTags.SPAWNS_PLAINS_BUTTERFLIES)) {
                    list = PLAINS_TYPES;
                } else if (biome.is(ModBiomeTags.SPAWNS_FOREST_BUTTERFLIES)) {
                    list = FOREST_TYPES;
                } else if (biome.is(ModBiomeTags.SPAWNS_MOUNTAIN_BUTTERFLIES)) {
                    list = MOUNTAIN_TYPES;
                }
            }

            return list.getRandomValue(random).orElse(CABBAGE_WHITE);
        }
    }
}
