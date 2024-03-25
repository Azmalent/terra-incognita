package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.placement.ModMiscFeaturePlacements;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import azmalent.terraincognita.common.world.placement.VanillaVegetationPlacements;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

@SuppressWarnings("deprecation")
public class BorealForestBiome extends TIBiomeEntry {
    private final boolean isSnowy;
    private final boolean isClearing;

    public BorealForestBiome(String name, boolean isSnowy, boolean isClearing) {
        super(name);
        this.isSnowy = isSnowy;
        this.isClearing = isClearing;
    }

    @Override
    public boolean isEnabled() {
        return TIConfig.Biomes.borealForest.get();
    }

    @Override
    protected Biome.BiomeCategory getCategory() {
        return Biome.BiomeCategory.TAIGA;
    }

    @Override
    public List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return List.of(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.COLD, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.FOREST);
    }

    @Override
    protected float getTemperature() {
        return isSnowy ? -0.5f : 0.25f;
    }

    @Override
    protected float getRainfall() {
        return isSnowy ? 0.4f : 0.8f;
    }

    @Override
    protected Biome.Precipitation getPrecipitation() {
        return isSnowy ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
    }

    @Override
    protected BiomeSpecialEffects getSpecialEffects() {
        return defaultSpecialEffects().waterColor(isSnowy ? 4020182 : 4159204).waterFogColor(329011).build();
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder generation) {
        OverworldBiomes.globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        WorldGenUtil.addModification(generation, ModMiscFeaturePlacements.BOREAL_FOREST_ROCK);

        WorldGenUtil.addVegetation(generation,
            VanillaVegetationPlacements.DEFAULT_FLOWERS,
            VanillaVegetationPlacements.TAIGA_GRASS,
            VanillaVegetationPlacements.FERNS,
            isSnowy ? VanillaVegetationPlacements.RARE_BERRY_BUSHES : VanillaVegetationPlacements.COMMON_BERRY_BUSHES,
            isClearing ? ModVegetationPlacements.SPARSE_LARCH_TREES : ModVegetationPlacements.BOREAL_FOREST_TREES
        );

        if (isClearing) {
            WorldGenUtil.addVegetation(generation, ModVegetationPlacements.CLEARING_VEGETATION);
        }

        WorldGenUtil.addVegetation(generation,
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
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
    }
}
