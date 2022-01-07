package azmalent.terraincognita.common.world.biome.normal;

import azmalent.terraincognita.common.ModTweaks;
import azmalent.terraincognita.common.world.ModDefaultFeatures;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import com.google.common.collect.Lists;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.level.biome.Biome.*;

import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;

public class TundraBiome extends NormalBiomeEntry {
    public TundraBiome(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    @Override
    protected BiomeCategory getCategory() {
        return BiomeCategory.ICY;
    }

    @Override
    protected ClimateSettings getClimate() {
        return new ClimateSettings(Precipitation.RAIN, 0.2f, TemperatureModifier.NONE, 0.5f);
    }

    @Override
    protected float getDepth() {
        return 0.125f;
    }

    @Override
    protected float getScale() {
        return 0.1f;
    }

    @Override
    protected BiomeSpecialEffects getAmbience() {
        return (new BiomeSpecialEffects.Builder())
            .waterColor(4159204).waterFogColor(329011).fogColor(12638463)
            .skyColor(getSkyColorWithTemperatureModifier(0.2F))
            .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return SurfaceBuilders.GRASS;
    }

    @Override
    protected MobSpawnSettings.Builder initSpawns() {
        MobSpawnSettings.Builder spawns = (new MobSpawnSettings.Builder()).creatureGenerationProbability(0.07F);
        BiomeDefaultFeatures.snowySpawns(spawns);
        ModTweaks.addExtraTundraSpawns(spawns);

        return spawns;
    }

    @Override
    protected BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.ICY;
    }

    @Override
    protected List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return Lists.newArrayList(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.COLD, BiomeDictionary.Type.WASTELAND);
    }

    @Override
    public void initFeatures(BiomeGenerationSettingsBuilder builder) {
        initDefaultFeatures(builder);

        BiomeDefaultFeatures.addDefaultOverworldLandStructures(builder);
        builder.addStructureStart(StructureFeatures.VILLAGE_TAIGA)
               .addStructureStart(StructureFeatures.PILLAGER_OUTPOST)
               .addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);

        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        BiomeDefaultFeatures.addSnowyTrees(builder);
        BiomeDefaultFeatures.addDefaultFlowers(builder);
        BiomeDefaultFeatures.addDefaultGrass(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        ModDefaultFeatures.withExtraTundraFeatures(builder);
    }

    @Override
    public boolean hasCustomGrassModifier() {
        return true;
    }

    @Override
    public int getCustomGrassColor(double x, double z) {
        double d0 = BIOME_INFO_NOISE.getValue(x * 0.0225D, z * 0.0225D, false);
        return d0 < -0.1D ? 0xADA258 : 0x80B497;
    }
}
