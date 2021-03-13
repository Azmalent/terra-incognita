package azmalent.terraincognita.common.world.biome;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.util.NoiseWeightedList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.WeightedList;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.common.BiomeManager;
import vazkii.quark.base.util.TriFunction;

import java.util.function.Supplier;

public abstract class NormalBiomeEntry extends BiomeEntry {
    public final NoiseWeightedList<SubBiomeEntry> subBiomes = new NoiseWeightedList<>();

    public NormalBiomeEntry(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    public void register() {
        super.register();

        if (spawnWeight > 0) {
            RegistryKey<Biome> key = BiomeUtil.getBiomeKey(id);
            BiomeManager.addBiome(getBiomeType(), new BiomeManager.BiomeEntry(key, spawnWeight));
        }
    }

    public SubBiomeEntry createSubBiome(String id, TriFunction<SubBiomeEntry, String, NormalBiomeEntry, Integer> constructor) {
        return createSubBiome(id, constructor, 1);
    }

    public SubBiomeEntry createSubBiome(String id, TriFunction<SubBiomeEntry, String, NormalBiomeEntry, Integer> constructor, int weight) {
        return constructor.apply(id, this, weight);
    }

    public final SubBiomeEntry getRandomSubBiome(INoiseRandom random) {
        return subBiomes.getRandomItem(random);
    }
}
