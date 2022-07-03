package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.common.world.ModDefaultFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
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
    protected BiomeCategory getCategory() {
        return BiomeCategory.PLAINS;
    }

    @Override
    protected List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
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
        return defaultSpecialEffects(0x3F76E4, 0x50533).grassColorOverride(0x9BCA26).build();
    }

    @Override
    protected void initFeatures(BiomeGenerationSettings.Builder builder) {
        initDefaultFeatures(builder);

        BiomeDefaultFeatures.addPlainGrass(builder);
        BiomeDefaultFeatures.addPlainVegetation(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);

        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);

        ModDefaultFeatures.addAppleTrees(builder);
    }

    @Override
    protected void initSpawns(MobSpawnSettings.Builder spawns) {
        BiomeDefaultFeatures.plainsSpawns(spawns);
        ModDefaultFeatures.butterflies(spawns);
    }
}
