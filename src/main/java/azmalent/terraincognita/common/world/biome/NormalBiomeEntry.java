package azmalent.terraincognita.common.world.biome;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.util.NoiseWeightedList;
import com.mojang.datafixers.util.Function3;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraftforge.common.BiomeManager;

import java.util.function.Supplier;

public abstract class NormalBiomeEntry extends BiomeEntry {
    public final NoiseWeightedList<SubBiomeEntry> subBiomes = new NoiseWeightedList<>();

    public NormalBiomeEntry(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    public void register() {
        super.register();

        if (spawnWeight > 0) {
            ResourceKey<Biome> key = BiomeUtil.getBiomeKey(id);
            BiomeManager.addBiome(getBiomeType(), new BiomeManager.BiomeEntry(key, spawnWeight));
        }
    }

    public SubBiomeEntry createSubBiome(String id, Function3<String, NormalBiomeEntry, Integer, SubBiomeEntry> constructor) {
        return createSubBiome(id, constructor, 1);
    }

    public SubBiomeEntry createSubBiome(String id, Function3<String, NormalBiomeEntry, Integer, SubBiomeEntry> constructor, int weight) {
        return constructor.apply(id, this, weight);
    }

    public final SubBiomeEntry getRandomSubBiome(Context random) {
        return subBiomes.getRandomItem(random);
    }
}
