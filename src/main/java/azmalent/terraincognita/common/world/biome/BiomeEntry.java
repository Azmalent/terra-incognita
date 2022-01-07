package azmalent.terraincognita.common.world.biome;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.common.registry.ModBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

@SuppressWarnings("deprecation")
public abstract class BiomeEntry implements Supplier<Biome> {
    public final RegistryObject<Biome> biome;
    public final ResourceLocation id;
    public final int spawnWeight;

    public BiomeEntry(String id, Supplier<Integer> spawnWeight) {
        this(id, spawnWeight.get());
    }

    public BiomeEntry(String id, int spawnWeight) {
        biome = ModBiomes.BIOMES.register(id, this::initBiome);
        this.id = biome.getId();
        this.spawnWeight = spawnWeight;

        ModBiomes.ID_TO_BIOME_MAP.put(this.id, this);
    }

    @Override
    public Biome get() {
        return biome.get();
    }

    public int getNumericId() {
        return BuiltinRegistries.BIOME.getId(biome.get());
    }

    protected final Biome initBiome() {
        Biome.ClimateSettings climate = getClimate();
        BiomeSpecialEffects ambience = getAmbience();
        BiomeGenerationSettings settings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(this::getSurfaceBuilder).build();
        MobSpawnSettings spawns = initSpawns().build();

        return (new Biome.BiomeBuilder())
                .biomeCategory(getCategory())
                .depth(getDepth()).scale(getScale())
                .temperature(climate.temperature).temperatureAdjustment(climate.temperatureModifier)
                .precipitation(climate.precipitation).downfall(climate.downfall)
                .specialEffects(ambience)
                .mobSpawnSettings(spawns)
                .generationSettings(settings)
                .build();
    }

    public void register() {
        ResourceKey<Biome> key = BiomeUtil.getBiomeKey(id);
        BiomeDictionary.addTypes(key, getBiomeDictionaryTypes().toArray(new BiomeDictionary.Type[0]));
    }

    protected abstract Biome.BiomeCategory getCategory();
    protected abstract Biome.ClimateSettings getClimate();
    protected abstract float getDepth();
    protected abstract float getScale();
    protected abstract BiomeSpecialEffects getAmbience();
    protected abstract ConfiguredSurfaceBuilder<?> getSurfaceBuilder();
    protected abstract MobSpawnSettings.Builder initSpawns();

    protected abstract BiomeManager.BiomeType getBiomeType();
    protected abstract List<BiomeDictionary.Type> getBiomeDictionaryTypes();

    public abstract void initFeatures(BiomeGenerationSettingsBuilder builder);

    protected void initDefaultFeatures(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarvers(builder);
        BiomeDefaultFeatures.addDefaultLakes(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasCustomGrassModifier() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCustomGrassColor(double x, double z) {
        throw new AssertionError("Custom grass color is not defined for biome " + biome.getId());
    }

    protected static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = Mth.clamp(lvt_1_1_, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}
