package azmalent.terraincognita.common.entity;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModEntities;
import azmalent.terraincognita.common.init.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ButterflyEntity extends AbstractButterflyEntity {
    public static final DataParameter<Integer> BUTTERFLY_TYPE = EntityDataManager.createKey(ButterflyEntity.class, DataSerializers.VARINT);

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
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);
        tag.putString("Type", getButterflyType().getName());
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);
        setButterflyType(Type.getTypeByName(tag.getString("Type")));
    }

    private void setButterflyType(ButterflyEntity.Type type) {
        dataManager.set(BUTTERFLY_TYPE, type.getIndex());
    }

    public ButterflyEntity.Type getButterflyType() {
        return ButterflyEntity.Type.getTypeByIndex(dataManager.get(BUTTERFLY_TYPE));
    }

    @Override
    public ResourceLocation getTexture() {
        return getButterflyType().getTexture();
    }

    @Override
    protected boolean isNocturnal() {
        return false;
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

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
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
