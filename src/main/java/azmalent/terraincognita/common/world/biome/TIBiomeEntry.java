package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.List;

public abstract class TIBiomeEntry {
    public final ResourceLocation id;
    public final ResourceKey<Biome> resourceKey;
    public Holder<Biome> biome;

    public TIBiomeEntry(String name) {
        id = TerraIncognita.prefix(name);
        resourceKey = ResourceKey.create(Registry.BIOME_REGISTRY, id);

        ModBiomes.BIOME_LIST.add(this);
    }

    protected final Biome initBiome() {
        Biome.ClimateSettings climate = getClimate();
        BiomeSpecialEffects specialEffects = getSpecialEffects();
        MobSpawnSettings spawns = initSpawns();

        return (new Biome.BiomeBuilder())
            .biomeCategory(getCategory())
            .temperature(climate.temperature).temperatureAdjustment(climate.temperatureModifier)
            .precipitation(climate.precipitation).downfall(climate.downfall)
            .specialEffects(specialEffects)
            .mobSpawnSettings(spawns)
            .build();
    }

    @SuppressWarnings("deprecation")
    public void register() {
        biome = BuiltinRegistries.register(BuiltinRegistries.BIOME, resourceKey, this.initBiome());
        BiomeDictionary.addTypes(resourceKey, getBiomeDictionaryTypes().toArray(new BiomeDictionary.Type[0]));
    }

    protected abstract Biome.BiomeCategory getCategory();
    protected abstract Biome.ClimateSettings getClimate();
    protected abstract BiomeSpecialEffects getSpecialEffects();
    protected abstract MobSpawnSettings initSpawns();

    protected abstract BiomeManager.BiomeType getBiomeType();
    protected abstract List<BiomeDictionary.Type> getBiomeDictionaryTypes();

    public abstract void initFeatures(BiomeGenerationSettingsBuilder builder);

    @OnlyIn(Dist.CLIENT)
    public boolean hasCustomGrassModifier() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCustomGrassColor(double x, double z) {
        throw new AssertionError("Custom grass color is not defined for biome " + id);
    }

    protected static void initDefaultFeatures(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    protected static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
