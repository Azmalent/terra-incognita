package azmalent.terraincognita.common.world.biome.sub;

import azmalent.terraincognita.common.world.ModConfiguredFeatures;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.common.world.biome.SubBiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

public class RockyTundraSubBiome extends SubBiomeEntry {
    public RockyTundraSubBiome(String id, NormalBiomeEntry baseBiome, int weight) {
        super(id, baseBiome, weight);
    }

    @Override
    protected float getDepth() {
        return 0.5f;
    }

    @Override
    protected float getScale() {
        return 0.3f;
    }

    @Override
    public void initFeatures(BiomeGenerationSettingsBuilder builder) {
        super.initFeatures(builder);
        WorldGenUtil.addModification(builder, ModConfiguredFeatures.TUNDRA_ROCK);
    }
}
