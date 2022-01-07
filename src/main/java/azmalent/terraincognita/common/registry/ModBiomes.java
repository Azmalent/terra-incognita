package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.biome.*;
import azmalent.terraincognita.common.world.biome.normal.LushPlainsBiome;
import azmalent.terraincognita.common.world.biome.normal.MuskegBiome;
import azmalent.terraincognita.common.world.biome.normal.TundraBiome;
import azmalent.terraincognita.common.world.biome.sub.BasicHillsSubBiome;
import azmalent.terraincognita.common.world.biome.sub.RockyTundraSubBiome;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@SuppressWarnings("unused")
public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, TerraIncognita.MODID);
    public static final Map<ResourceLocation, BiomeEntry> ID_TO_BIOME_MAP = Maps.newHashMap();

    public static final NormalBiomeEntry LUSH_PLAINS = new LushPlainsBiome("lush_plains", TIConfig.Biomes.lushPlainsWeight);
    public static final NormalBiomeEntry TUNDRA = new TundraBiome("tundra", TIConfig.Biomes.tundraWeight);
    public static final NormalBiomeEntry MUSKEG = new MuskegBiome("muskeg", TIConfig.Biomes.muskegWeight);

    public static final SubBiomeEntry LUSH_HILLS = LUSH_PLAINS.createSubBiome("lush_hills", BasicHillsSubBiome::new);
    public static final SubBiomeEntry ROCKY_TUNDRA = TUNDRA.createSubBiome("rocky_tundra", RockyTundraSubBiome::new);


    public static void registerBiomes() {
        ID_TO_BIOME_MAP.values().forEach(BiomeEntry::register);
    }
}
