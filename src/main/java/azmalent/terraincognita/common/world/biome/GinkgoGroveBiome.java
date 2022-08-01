package azmalent.terraincognita.common.world.biome;

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
    protected Biome.BiomeCategory getCategory() {
        return Biome.BiomeCategory.MOUNTAIN;
    }

    @Override
    public List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return List.of(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SLOPE);
    }

    @Override
    protected float getTemperature() {
        return 0.8f;
    }

    @Override
    protected float getRainfall() {
        return 0.8f;
    }

    @Override
    protected BiomeSpecialEffects getSpecialEffects() {
        return defaultSpecialEffects().build();
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
        defaultFeatures(builder);
    }

    @Override
    public void initSpawns(MobSpawnSettings.Builder builder) {

    }
}
