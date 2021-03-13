package azmalent.terraincognita.common.world.biome.normal;

import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.common.world.ModVegetation;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

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
        return -0.2f;
    }

    @Override
    protected float getScale() {
        return 0.1f;
    }

    @Override
    protected BiomeAmbience getAmbience() {
        return (new BiomeAmbience.Builder())
            .setWaterColor(6388580).setWaterFogColor(2302743).setFogColor(12638463)
            .withSkyColor(getSkyColorWithTemperatureModifier(0.8F))
            .withFoliageColor(6975545).withGrassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP)
            .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return ConfiguredSurfaceBuilders.field_244177_i;
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
        DefaultBiomeFeatures.withBatsAndHostiles(spawns);
        return spawns;
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
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
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_TAIGA);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_TAIGA);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);

        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withSparseBerries(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);

        WorldGenUtil.addVegetation(builder, ModTrees.MUSKEG_TREES, ModVegetation.ARCTIC_FLOWERS, ModVegetation.CARIBOU_MOSS);
    }
}
