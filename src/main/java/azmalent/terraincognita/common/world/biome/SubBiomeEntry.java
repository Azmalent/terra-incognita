package azmalent.terraincognita.common.world.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.List;

public abstract class SubBiomeEntry extends BiomeEntry {
    public final NormalBiomeEntry baseBiome;

    public SubBiomeEntry(String id, NormalBiomeEntry baseBiome, int weight) {
        super(id, weight);
        this.baseBiome = baseBiome;

        baseBiome.subBiomes.add(this, weight);
    }

    public Biome get() {
        return biome.get();
    }

    @Override
    protected Biome.BiomeCategory getCategory() {
        return baseBiome.getCategory();
    }

    @Override
    protected Biome.ClimateSettings getClimate() {
        return baseBiome.getClimate();
    }

    @Override
    protected float getDepth() {
        return baseBiome.getDepth();
    }

    @Override
    protected float getScale() {
        return baseBiome.getScale();
    }

    @Override
    protected BiomeSpecialEffects getAmbience() {
        return baseBiome.getAmbience();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return baseBiome.getSurfaceBuilder();
    }

    @Override
    protected BiomeManager.BiomeType getBiomeType() {
        return baseBiome.getBiomeType();
    }

    @Override
    protected List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return baseBiome.getBiomeDictionaryTypes();
    }

    @Override
    protected MobSpawnSettings.Builder initSpawns() {
        return baseBiome.initSpawns();
    }

    @Override
    public void initFeatures(BiomeGenerationSettingsBuilder builder) {
        baseBiome.initFeatures(builder);
    }

    @Override
    public boolean hasCustomGrassModifier() {
        return baseBiome.hasCustomGrassModifier();
    }

    @Override
    public int getCustomGrassColor(double x, double z) {
        return baseBiome.getCustomGrassColor(x, z);
    }
}
