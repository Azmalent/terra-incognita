package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.ModDefaultFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
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
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
        defaultFeatures(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);

        BiomeDefaultFeatures.addTaigaGrass(builder);
        BiomeDefaultFeatures.addFerns(builder);
        BiomeDefaultFeatures.addDefaultFlowers(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);

        if (isSnowy) {
            BiomeDefaultFeatures.addRareBerryBushes(builder);
            ModDefaultFeatures.caribouMoss(builder);
        } else {
            BiomeDefaultFeatures.addCommonBerryBushes(builder);
            ModDefaultFeatures.sourBerries(builder);
        }

        ModDefaultFeatures.arcticFlowers(builder);
        ModDefaultFeatures.borealForestRocks(builder);

        if (isClearing) {
            ModDefaultFeatures.sparseLarchTrees(builder);
            ModDefaultFeatures.clearingVegetation(builder);
        } else {
            ModDefaultFeatures.borealForestTrees(builder);
        }
    }

    @Override
    public void initSpawns(MobSpawnSettings.Builder builder) {
        BiomeDefaultFeatures.farmAnimals(builder);
        BiomeDefaultFeatures.commonSpawns(builder);
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
    }
}
