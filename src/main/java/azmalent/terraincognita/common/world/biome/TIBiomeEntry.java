package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBiomes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@SuppressWarnings("deprecation")
public abstract class TIBiomeEntry {
    public final RegistryObject<Biome> biome;

    public TIBiomeEntry(String name) {
        biome = ModBiomes.BIOMES.register(name, this::initBiome);
        ModBiomes.BIOME_LIST.add(this);
        ModBiomes.BIOMES_BY_NAME.put(TerraIncognita.prefix(name), this);
    }

    public abstract boolean isEnabled();

    protected final Biome initBiome() {
        BiomeSpecialEffects specialEffects = getSpecialEffects();

        var generation = new BiomeGenerationSettings.Builder();
        //initFeatures(generation);

        var spawns = new MobSpawnSettings.Builder();
        //initSpawns(spawns);

        return new Biome.BiomeBuilder()
            .biomeCategory(getCategory())
            .temperature(getTemperature()).temperatureAdjustment(getTemperatureModifier())
            .precipitation(getPrecipitation()).downfall(getRainfall())
            .specialEffects(specialEffects)
            .mobSpawnSettings(spawns.build())
            .generationSettings(generation.build())
            .build();
    }

    protected abstract Biome.BiomeCategory getCategory();
    public abstract List<BiomeDictionary.Type> getBiomeDictionaryTypes();

    protected Biome.Precipitation getPrecipitation() {
        return Biome.Precipitation.RAIN;
    }

    protected Biome.TemperatureModifier getTemperatureModifier() {
        return Biome.TemperatureModifier.NONE;
    }
    protected abstract float getTemperature();
    protected abstract float getRainfall();
    protected abstract BiomeSpecialEffects getSpecialEffects();

    public abstract void initFeatures(BiomeGenerationSettings.Builder builder);

    public abstract void initSpawns(MobSpawnSettings.Builder builder);

    protected static void defaultFeatures(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    protected final BiomeSpecialEffects.Builder defaultSpecialEffects() {
        return defaultSpecialEffects(0x3F76E4, 0x50533);
    }

    protected final BiomeSpecialEffects.Builder defaultSpecialEffects(int waterColor, int waterFogColor) {
        return defaultSpecialEffects(waterColor, waterFogColor, 0xC0D8FF);
    }

    @SuppressWarnings("SameParameterValue")
    protected final BiomeSpecialEffects.Builder defaultSpecialEffects(int waterColor, int waterFogColor, int fogColor) {
        return defaultSpecialEffects(waterColor, waterFogColor, fogColor, calculateSkyColor(getTemperature()));
    }

    protected final BiomeSpecialEffects.Builder defaultSpecialEffects(int waterColor, int waterFogColor, int fogColor, int skyColor) {
        return new BiomeSpecialEffects.Builder()
            .waterColor(waterColor)
            .waterFogColor(waterFogColor)
            .fogColor(fogColor)
            .skyColor(skyColor)
            .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
    }

    protected static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}