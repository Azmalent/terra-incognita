package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.biome.*;
import azmalent.terraincognita.common.world.region.TIOverworldRegion;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import terrablender.api.Regions;

import java.util.List;
import java.util.Map;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.BIOMES);
    public static final List<TIBiomeEntry> BIOME_LIST = Lists.newArrayList();
    public static final Map<ResourceLocation, TIBiomeEntry> BIOMES_BY_NAME = Maps.newHashMap();

    public static final BorealForestBiome BOREAL_FOREST = new BorealForestBiome("boreal_forest", false, false);
    public static final BorealForestBiome BOREAL_FOREST_CLEARING = new BorealForestBiome("boreal_forest_clearing", false, true);
    public static final BorealForestBiome SNOWY_BOREAL_FOREST = new BorealForestBiome("snowy_boreal_forest", true, false);
    public static final BorealForestBiome SNOWY_BOREAL_FOREST_CLEARING = new BorealForestBiome("snowy_boreal_forest_clearing", true, true);

    public static final GinkgoGroveBiome GINKGO_GROVE = new GinkgoGroveBiome("ginkgo_grove");
    public static final LushPlainsBiome LUSH_PLAINS = new LushPlainsBiome("lush_plains");
    public static final TundraBiome TUNDRA = new TundraBiome("tundra");

    @SuppressWarnings("deprecation")
    public static void initBiomes() {
        for(TIBiomeEntry biomeEntry : BIOME_LIST) {
            var types = biomeEntry.getBiomeDictionaryTypes();
            BiomeDictionary.addTypes(biomeEntry.biome.getKey(), types.toArray( new BiomeDictionary.Type[0] ));
        }

        int weight = TIConfig.Biomes.biomeWeight.get();
        if (weight > 0) {
            Regions.register(new TIOverworldRegion(weight));
        }
    }
}
