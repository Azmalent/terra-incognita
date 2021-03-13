package azmalent.terraincognita.common.world.biome.normal;

import azmalent.terraincognita.common.registry.ModSurfaceBuilders;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.common.world.ModVegetation;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import java.util.List;
import java.util.function.Supplier;

public class LushPlainsBiome extends NormalBiomeEntry {
    public LushPlainsBiome(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    @Override
    protected Biome.Category getCategory() {
        return Biome.Category.PLAINS;
    }

    @Override
    protected Biome.Climate getClimate() {
        return new Biome.Climate(Biome.RainType.RAIN, 0.8f, Biome.TemperatureModifier.NONE, 0.4f);
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
    protected BiomeAmbience getAmbience() {
        return (new BiomeAmbience.Builder())
            .setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463)
            .withSkyColor(getSkyColorWithTemperatureModifier(0.8F))
            .withGrassColor(0x9BCA26)
            .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return ModSurfaceBuilders.LUSH_PLAINS.get().func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
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
    protected MobSpawnInfo.Builder initSpawns() {
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithHorseAndDonkey(spawns);
        return spawns;
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
        initDefaultFeatures(builder);

        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
        builder.withStructure(StructureFeatures.RUINED_PORTAL);

        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withNoiseTallGrass(builder);
        DefaultBiomeFeatures.withPlainGrassVegetation(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withChanceBerries(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);

        WorldGenUtil.addVegetation(builder, ModTrees.LUSH_PLAINS_APPLE, ModTrees.LUSH_PLAINS_OAK, ModTrees.OAK_SHRUB, ModVegetation.LUSH_PLAINS_FLOWERS);
    }
}
