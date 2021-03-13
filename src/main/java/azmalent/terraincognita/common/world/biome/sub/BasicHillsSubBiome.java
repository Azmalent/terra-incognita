package azmalent.terraincognita.common.world.biome.sub;

import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.common.world.biome.SubBiomeEntry;

public class BasicHillsSubBiome extends SubBiomeEntry {
    public BasicHillsSubBiome(String id, NormalBiomeEntry baseBiome, int weight) {
        super(id, baseBiome, weight);
    }

    @Override
    protected float getDepth() {
        return 0.4f;
    }

    @Override
    protected float getScale() {
        return 0.2f;
    }
}
