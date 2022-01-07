package azmalent.terraincognita.common.world.biome.normal;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModSurfaceBuilders;
import azmalent.terraincognita.common.world.ModConfiguredFeatures;
import azmalent.terraincognita.common.world.ModFlowerFeatures;
import azmalent.terraincognita.common.world.ModTreeFeatures;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.*;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class LushPlainsBiome extends NormalBiomeEntry {
    public LushPlainsBiome(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    @Override
    protected Biome.BiomeCategory getCategory() {
        return Biome.BiomeCategory.PLAINS;
    }

    @Override
    protected Biome.ClimateSettings getClimate() {
        return new Biome.ClimateSettings(Biome.Precipitation.RAIN, 0.8f, Biome.TemperatureModifier.NONE, 0.4f);
    }

    @Override
    protected float getDepth() {
        return 0.125f;
    }

    @Override
    protected float getScale() {
        return 0.05f;
    }

    @Override
    protected BiomeSpecialEffects getAmbience() {
        return (new BiomeSpecialEffects.Builder())
            .waterColor(4159204).waterFogColor(329011).fogColor(12638463)
            .skyColor(getSkyColorWithTemperatureModifier(0.8F))
            .grassColorOverride(0x9BCA26)
            .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return ModSurfaceBuilders.LUSH_PLAINS.get().configured(SurfaceBuilder.CONFIG_GRASS);
    }

    @Override
    protected BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.WARM;
    }

    @Override
    protected List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return Lists.newArrayList(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.LUSH);
    }

    @Override
    protected MobSpawnSettings.Builder initSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.plainsSpawns(spawns);
        return spawns;
    }

    @Override
    public void initFeatures(BiomeGenerationSettingsBuilder builder) {
        initDefaultFeatures(builder);

        BiomeDefaultFeatures.addDefaultOverworldLandStructures(builder);
        builder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);

        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        BiomeDefaultFeatures.addPlainGrass(builder);
        BiomeDefaultFeatures.addPlainVegetation(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);

        if (TIConfig.Trees.apple.get()) {
            WorldGenUtil.addVegetation(builder, ModTreeFeatures.EXTRA_APPLE_TREE);
        }

        WorldGenUtil.addVegetation(builder, ModTreeFeatures.LUSH_PLAINS_OAK, ModTreeFeatures.OAK_SHRUB, ModFlowerFeatures.LUSH_PLAINS_FLOWERS);
    }
}
