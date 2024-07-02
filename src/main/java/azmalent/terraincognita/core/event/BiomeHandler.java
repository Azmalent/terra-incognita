package azmalent.terraincognita.core.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.registry.ModBiomes;
import azmalent.terraincognita.core.registry.ModEntities;
import azmalent.terraincognita.common.world.biome.TIBiomeEntry;
import azmalent.terraincognita.common.world.placement.ModMiscFeaturePlacements;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.util.WorldGenUtil.*;
import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings({"ConstantConditions", "deprecation", "DataFlowIssue"})
//@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeHandler {
    //@SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEarlyLoadBiome(BiomeLoadingEvent event) {
        ResourceLocation id = event.getName();
        if (id != null && id.getNamespace().equals(TerraIncognita.MODID)) {
            TIBiomeEntry biome = ModBiomes.BIOMES_BY_NAME.get(id);

            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            biome.initFeatures(generation);

            MobSpawnSettingsBuilder spawns = event.getSpawns();
            biome.initSpawns(spawns);
        }
    }

    //@SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onLoadBiome(BiomeLoadingEvent event) {
        ResourceLocation id = event.getName();
        Biome biome = ForgeRegistries.BIOMES.getValue(id);

        if (biome == null) {
            return;
        }

        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(id);

        if (BiomeDictionary.hasType(key, OVERWORLD)) {
            handleOverworldBiome(biome, event.getGeneration(), event.getSpawns());
        } else if (BiomeDictionary.hasType(key, NETHER)) {
            handleNetherBiome(biome, event.getGeneration());
        }
    }

    private static void handleOverworldBiome(Biome biome, BiomeGenerationSettingsBuilder generation, MobSpawnSettingsBuilder spawns) {
        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(biome);

        boolean isTIBiome = biome.getRegistryName().getNamespace().equals(TerraIncognita.MODID);

        //Spawns
        if (TIConfig.Fauna.butterflies.get() && WorldGenUtil.hasAnyBiomeType(key, PLAINS, FOREST) && !WorldGenUtil.hasAnyBiomeType(key, COLD, DENSE)) {
            addSpawn(spawns, ModEntities.BUTTERFLY, MobCategory.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
        }

        if (key == Biomes.FLOWER_FOREST) {
            addVegetation(generation, TIConfig.Flora.sweetPeas, ModVegetationPlacements.SWEET_PEAS);
        } else if (key == Biomes.LUSH_CAVES) {
            addVegetation(generation, TIConfig.Flora.hangingMoss, ModVegetationPlacements.HANGING_MOSS_PATCH);
        } else {
            var category = WorldGenUtil.getProperBiomeCategory(biome);
            switch (category) {
                case PLAINS -> {
                    addVegetation(generation, TIConfig.Trees.apple, ModVegetationPlacements.PLAINS_APPLE_TREES);
                }

                case FOREST -> {
                    addVegetation(generation, TIConfig.Trees.hazel, ModVegetationPlacements.FOREST_HAZEL_TREES);
                    addVegetation(generation, TIConfig.Flora.forestFlowers, ModVegetationPlacements.FOREST_FLOWERS);
                }

                case SWAMP -> {
                    addOre(generation, TIConfig.Misc.peat, ModMiscFeaturePlacements.PEAT_DISK);
                    addOre(generation, TIConfig.Misc.mossyGravel, ModMiscFeaturePlacements.MOSSY_GRAVEL_DISK);
                    addVegetation(generation, TIConfig.Flora.swampFlowers, ModVegetationPlacements.SWAMP_FLOWERS);
                    addVegetation(generation, TIConfig.Flora.swampReeds, ModVegetationPlacements.SWAMP_REEDS);
                }

                case DESERT -> {
                    addVegetation(generation, TIConfig.Flora.cactusFlowers, ModVegetationPlacements.CACTUS_FLOWERS);
                }

                case SAVANNA -> {
                    addVegetation(generation, TIConfig.Flora.savannaFlowers, ModVegetationPlacements.SAVANNA_FLOWERS);
                }

                case EXTREME_HILLS -> {
                    if (!BiomeDictionary.hasType(key, HOT)) {
                        addVegetation(generation, TIConfig.Flora.alpineFlowers, ModVegetationPlacements.ALPINE_FLOWERS);
                        addVegetation(generation, TIConfig.Trees.larch, ModVegetationPlacements.SPARSE_LARCH_TREES);
                    }
                }

                case JUNGLE -> {
                    addVegetation(generation, TIConfig.Flora.lotus, ModVegetationPlacements.LOTUS);
                }

                case TAIGA -> {
                    if (!isTIBiome) {
                        addVegetation(generation, TIConfig.Trees.larch, ModVegetationPlacements.RARE_LARCH_TREES);
                    }

                    addVegetation(generation, TIConfig.Flora.arcticFlowers, ModVegetationPlacements.ARCTIC_FLOWERS);
                    addVegetation(generation, TIConfig.Flora.caribouMoss, ModVegetationPlacements.CARIBOU_MOSS);
                    addVegetation(generation, TIConfig.Flora.sourBerries, ModVegetationPlacements.SOUR_BERRIES);
                }

                case ICY -> {
                    addVegetation(generation, TIConfig.Trees.larch, ModVegetationPlacements.RARE_LARCH_TREES);
                    addVegetation(generation, TIConfig.Flora.caribouMoss, ModVegetationPlacements.CARIBOU_MOSS);
                }
            }
        }
    }

    private static void handleNetherBiome(Biome biome, BiomeGenerationSettingsBuilder generation) {
        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(biome);

        if (key == Biomes.SOUL_SAND_VALLEY) {
            addVegetation(generation, TIConfig.Misc.witherRoseGeneration, ModVegetationPlacements.WITHER_ROSES);
        }
    }
}
