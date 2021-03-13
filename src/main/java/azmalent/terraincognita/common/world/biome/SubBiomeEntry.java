package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

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
    protected Biome.Category getCategory() {
        return baseBiome.getCategory();
    }

    @Override
    protected Biome.Climate getClimate() {
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
    protected BiomeAmbience getAmbience() {
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
    protected MobSpawnInfo.Builder initSpawns() {
        return baseBiome.initSpawns();
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
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
