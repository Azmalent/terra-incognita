package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.biome.TIBiomeEntry;
import azmalent.terraincognita.common.world.biome.TIBiomeProvider;
import azmalent.terraincognita.common.world.biome.TundraBiome;
import com.google.common.collect.Lists;
import terrablender.api.Regions;

import java.util.List;

public class ModBiomes {
    public static List<TIBiomeEntry> BIOME_LIST = Lists.newArrayList();

    public static TundraBiome TUNDRA = new TundraBiome("tundra");

    public static void registerBiomes() {
        BIOME_LIST.forEach(TIBiomeEntry::register);

        int weight = TIConfig.Biomes.biomeWeight.get();
        if (weight > 0) {
            Regions.register(new TIBiomeProvider(weight));
        }
    }
}
