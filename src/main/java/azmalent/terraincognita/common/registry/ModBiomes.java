package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.biome.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import terrablender.api.Regions;

import java.util.List;
import java.util.Map;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.BIOMES);
    public static final List<TIBiomeEntry> BIOME_LIST = Lists.newArrayList();

    public static final BorealForestBiome BOREAL_FOREST = new BorealForestBiome("boreal_forest");
    public static final GinkgoGroveBiome GINKGO_GROVE = new GinkgoGroveBiome("ginkgo_grove");
    public static final MuskegBiome MUSKEG = new MuskegBiome("muskeg");
    public static final LushPlainsBiome LUSH_PLAINS = new LushPlainsBiome("lush_plains");

    public static void initBiomes() {
        BIOME_LIST.forEach(TIBiomeEntry::initBiomeDictionary);

        int weight = TIConfig.Biomes.biomeWeight.get();
        if (weight > 0) {
            Regions.register(new TIBiomeProvider(weight));
        }
    }
}
