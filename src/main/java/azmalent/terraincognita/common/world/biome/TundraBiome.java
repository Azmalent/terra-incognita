package azmalent.terraincognita.common.world.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.List;

public class TundraBiome extends TIBiomeEntry {
    public TundraBiome(String name) {
        super(name);
    }

    @Override
    protected Biome.BiomeCategory getCategory() {
        return Biome.BiomeCategory.ICY;
    }

    @Override
    protected Biome.ClimateSettings getClimate() {
        return null;
    }

    @Override
    protected BiomeSpecialEffects getSpecialEffects() {
        return null;
    }

    @Override
    protected MobSpawnSettings initSpawns() {
        return null;
    }

    @Override
    protected BiomeManager.BiomeType getBiomeType() {
        return null;
    }

    @Override
    protected List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return null;
    }

    @Override
    public void initFeatures(BiomeGenerationSettingsBuilder builder) {
        initDefaultFeatures(builder);
    }
}
