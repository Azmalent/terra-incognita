package azmalent.terraincognita.common.world.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

@SuppressWarnings("deprecation")
public class MuskegBiome extends TIBiomeEntry {
    public MuskegBiome(String name) {
        super(name);
    }

    @Override
    protected BiomeCategory getCategory() {
        return BiomeCategory.SWAMP;
    }

    @Override
    public List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return List.of(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.COLD, BiomeDictionary.Type.CONIFEROUS);
    }

    @Override
    protected float getTemperature() {
        return 0.25f;
    }

    @Override
    protected float getRainfall() {
        return 0.8f;
    }

    @Override
    protected BiomeSpecialEffects getSpecialEffects() {
        return defaultSpecialEffects(0x787360, 0x232317)
            .foliageColorOverride(0x6A7039)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
            .build();
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder builder) {

    }

    @Override
    public void initSpawns(MobSpawnSettings.Builder builder) {

    }
}
