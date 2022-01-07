package azmalent.terraincognita.common.world.biome.normal;

import azmalent.terraincognita.common.registry.ModSurfaceBuilders;
import azmalent.terraincognita.common.world.*;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import com.google.common.collect.Lists;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
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

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class MuskegBiome extends NormalBiomeEntry {
    public MuskegBiome(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    @Override
    protected BiomeCategory getCategory() {
        return BiomeCategory.SWAMP;
    }

    @Override
    protected ClimateSettings getClimate() {
        return new ClimateSettings(Precipitation.RAIN, 0.25f, TemperatureModifier.NONE, 0.8f);
    }

    @Override
    protected float getDepth() {
        return -0.15f;
    }

    @Override
    protected float getScale() {
        return 0.05f;
    }

    @Override
    protected BiomeSpecialEffects getAmbience() {
        return (new BiomeSpecialEffects.Builder())
            .waterColor(0x787360).waterFogColor(0x232317).fogColor(0xc0d8ff)
            .skyColor(getSkyColorWithTemperatureModifier(0.25F))
            .foliageColorOverride(0x6a7039).grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
            .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return ModSurfaceBuilders.MUSKEG.get().configured(SurfaceBuilder.CONFIG_GRASS);
    }

    @Override
    protected BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.ICY;
    }

    @Override
    protected List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return Lists.newArrayList(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.COLD, BiomeDictionary.Type.CONIFEROUS);
    }

    @Override
    protected MobSpawnSettings.Builder initSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawns);
        BiomeDefaultFeatures.ambientSpawns(spawns);
        BiomeDefaultFeatures.monsters(spawns, 95, 5, 50);
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 50, 4, 4));

        return spawns;
    }

    @Override
    public void initFeatures(BiomeGenerationSettingsBuilder builder) {
        initDefaultFeatures(builder);

        builder.addStructureStart(StructureFeatures.SWAMP_HUT)
               .addStructureStart(StructureFeatures.PILLAGER_OUTPOST)
               .addStructureStart(StructureFeatures.MINESHAFT)
               .addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP);

        BiomeDefaultFeatures.addSwampClayDisk(builder);
        BiomeDefaultFeatures.addFossilDecoration(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        BiomeDefaultFeatures.addSwampExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addDefaultFlowers(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addGiantTaigaVegetation(builder);
        BiomeDefaultFeatures.addFerns(builder);

        ModDefaultFeatures.withArcticFlowers(builder);
        ModDefaultFeatures.withCaribouMoss(builder);
        ModDefaultFeatures.withSourBerries(builder);

        WorldGenUtil.addVegetation(builder, Features.PATCH_WATERLILLY, Features.SEAGRASS_SWAMP, ModTreeFeatures.MUSKEG_TREES);
        WorldGenUtil.addModification(builder, ModConfiguredFeatures.MUSKEG_LOG);
    }
}
