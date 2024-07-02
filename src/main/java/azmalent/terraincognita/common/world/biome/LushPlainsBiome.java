package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.placement.VanillaVegetationPlacements;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

@SuppressWarnings("deprecation")
public class LushPlainsBiome extends TIBiomeEntry {
    public LushPlainsBiome(String name) {
        super(name);
    }

    @Override
    public boolean isEnabled() {
        return TIConfig.Biomes.lushPlains.get();
    }

    @Override
    protected BiomeCategory getCategory() {
        return BiomeCategory.PLAINS;
    }

    @Override
    public List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return List.of(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.LUSH);
    }

    @Override
    protected float getTemperature() {
        return 0.8f;
    }

    @Override
    protected float getRainfall() {
        return 0.4f;
    }

    @Override
    protected BiomeSpecialEffects getSpecialEffects() {
        return defaultSpecialEffects(0x3F76E4, 0x50533).grassColorOverride(0x6FA81E).build();
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder generation) {
        OverworldBiomes.globalOverworldGeneration(generation);
        
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        WorldGenUtil.addVegetation(generation,
            VanillaVegetationPlacements.PLAIN_TALL_GRASS,
            VanillaVegetationPlacements.PLAIN_TREES,
            VanillaVegetationPlacements.PLAIN_FLOWERS,
            VanillaVegetationPlacements.PLAIN_GRASS,
            VanillaVegetationPlacements.DEFAULT_BROWN_MUSHROOMS,
            VanillaVegetationPlacements.DEFAULT_RED_MUSHROOMS,
            VanillaVegetationPlacements.SUGAR_CANE,
            VanillaVegetationPlacements.PUMPKINS
        );
    }

    @Override
    public void initSpawns(MobSpawnSettings.Builder spawns) {
        BiomeDefaultFeatures.plainsSpawns(spawns);
    }
}
