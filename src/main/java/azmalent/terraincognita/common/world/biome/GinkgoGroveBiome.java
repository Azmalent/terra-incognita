package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import azmalent.terraincognita.common.world.placement.VanillaVegetationPlacements;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

@SuppressWarnings("deprecation")
public class GinkgoGroveBiome extends TIBiomeEntry {
    public GinkgoGroveBiome(String name) {
        super(name);
    }

    @Override
    public boolean isEnabled() {
        return TIConfig.Biomes.ginkgoGrove.get();
    }

    @Override
    protected Biome.BiomeCategory getCategory() {
        return Biome.BiomeCategory.MOUNTAIN;
    }

    @Override
    public List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return List.of(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.PLATEAU, BiomeDictionary.Type.SLOPE);
    }

    @Override
    protected float getTemperature() {
        return 0.7f;
    }

    @Override
    protected float getRainfall() {
        return 0.6f;
    }

    @Override
    protected BiomeSpecialEffects getSpecialEffects() {
        return defaultSpecialEffects().build();
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder generation) {
        OverworldBiomes.globalOverworldGeneration(generation);

        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        WorldGenUtil.addVegetation(generation,
            ModVegetationPlacements.GINKGO_GROVE_TREES,
            ModVegetationPlacements.GINKGO_GROVE_FLOWERS,
            VanillaVegetationPlacements.LIGHT_BAMBOO,
            VanillaVegetationPlacements.WARM_FLOWERS,
            VanillaVegetationPlacements.JUNGLE_GRASS,
            VanillaVegetationPlacements.SPARSE_MELONS,
            VanillaVegetationPlacements.DEFAULT_BROWN_MUSHROOMS,
            VanillaVegetationPlacements.DEFAULT_RED_MUSHROOMS,
            VanillaVegetationPlacements.SUGAR_CANE,
            VanillaVegetationPlacements.PUMPKINS
        );
    }

    @Override
    public void initSpawns(MobSpawnSettings.Builder spawns) {
        BiomeDefaultFeatures.farmAnimals(spawns);
        BiomeDefaultFeatures.commonSpawns(spawns);
    }
}
