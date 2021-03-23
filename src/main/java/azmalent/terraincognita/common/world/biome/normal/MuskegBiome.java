package azmalent.terraincognita.common.world.biome.normal;

import azmalent.terraincognita.common.registry.ModSurfaceBuilders;
import azmalent.terraincognita.common.world.*;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.biome.Biome.*;

public class MuskegBiome extends NormalBiomeEntry {
    public MuskegBiome(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    @Override
    protected Category getCategory() {
        return Category.SWAMP;
    }

    @Override
    protected Climate getClimate() {
        return new Climate(RainType.RAIN, 0.25f, TemperatureModifier.NONE, 0.8f);
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
    protected BiomeAmbience getAmbience() {
        return (new BiomeAmbience.Builder())
            .setWaterColor(0x787360).setWaterFogColor(0x232317).setFogColor(0xc0d8ff)
            .withSkyColor(getSkyColorWithTemperatureModifier(0.25F))
            .withFoliageColor(0x6a7039).withGrassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP)
            .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return ModSurfaceBuilders.MUSKEG.get().func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
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
    protected MobSpawnInfo.Builder initSpawns() {
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(spawns);
        DefaultBiomeFeatures.withBats(spawns);
        DefaultBiomeFeatures.withHostileMobs(spawns, 95, 5, 50);
        spawns.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.STRAY, 50, 4, 4));

        return spawns;
    }

    @Override
    public void initFeatures(BiomeGenerationSettingsBuilder builder) {
        initDefaultFeatures(builder);

        builder.withStructure(StructureFeatures.SWAMP_HUT)
               .withStructure(StructureFeatures.PILLAGER_OUTPOST)
               .withStructure(StructureFeatures.MINESHAFT)
               .withStructure(StructureFeatures.RUINED_PORTAL_SWAMP);

        DefaultBiomeFeatures.withClayDisks(builder);
        DefaultBiomeFeatures.withFossils(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSwampSugarcaneAndPumpkin(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        DefaultBiomeFeatures.withGiantTaigaGrassVegetation(builder);
        DefaultBiomeFeatures.withLargeFern(builder);

        ModDefaultFeatures.withArcticFlowers(builder);
        ModDefaultFeatures.withCaribouMoss(builder);
        ModDefaultFeatures.withSourBerries(builder);

        WorldGenUtil.addVegetation(builder, Features.PATCH_WATERLILLY, Features.SEAGRASS_SWAMP, ModTreeFeatures.MUSKEG_TREES);
        WorldGenUtil.addModification(builder, ModConfiguredFeatures.MUSKEG_LOG);
    }
}
