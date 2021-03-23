package azmalent.terraincognita.common.world.biome;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.common.registry.ModBiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

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
        return WorldGenRegistries.BIOME.getId(biome.get());
    }

    protected final Biome initBiome() {
        Biome.Climate climate = getClimate();
        BiomeAmbience ambience = getAmbience();
        BiomeGenerationSettings settings = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(this::getSurfaceBuilder).build();
        MobSpawnInfo spawns = initSpawns().copy();

        return (new Biome.Builder())
                .category(getCategory())
                .depth(getDepth()).scale(getScale())
                .temperature(climate.temperature).withTemperatureModifier(climate.temperatureModifier)
                .precipitation(climate.precipitation).downfall(climate.downfall)
                .setEffects(ambience)
                .withMobSpawnSettings(spawns)
                .withGenerationSettings(settings)
                .build();
    }

    public void register() {
        RegistryKey<Biome> key = BiomeUtil.getBiomeKey(id);
        BiomeDictionary.addTypes(key, getBiomeDictionaryTypes().toArray(new BiomeDictionary.Type[0]));
    }

    protected abstract Biome.Category getCategory();
    protected abstract Biome.Climate getClimate();
    protected abstract float getDepth();
    protected abstract float getScale();
    protected abstract BiomeAmbience getAmbience();
    protected abstract ConfiguredSurfaceBuilder<?> getSurfaceBuilder();
    protected abstract MobSpawnInfo.Builder initSpawns();

    protected abstract BiomeManager.BiomeType getBiomeType();
    protected abstract List<BiomeDictionary.Type> getBiomeDictionaryTypes();

    public abstract void initFeatures(BiomeGenerationSettingsBuilder builder);

    protected void initDefaultFeatures(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
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
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}
