package azmalent.terraincognita.common.world.biome.sub;

import azmalent.terraincognita.common.world.ModMiscFeatures;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.common.world.biome.SubBiomeEntry;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;

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
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
        super.initFeatures(builder);
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ModMiscFeatures.ROCK);
    }
}
