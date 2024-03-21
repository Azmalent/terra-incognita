package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.world.placement.ModMiscFeaturePlacements;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModDefaultFeatures {
    //Spawns
    public static void butterflies(MobSpawnSettings.Builder spawns) {
        WorldGenUtil.addSpawn(spawns, ModEntities.BUTTERFLY, MobCategory.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
    }

    //Vegetation
    public static void alpineFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.alpineFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.ALPINE_FLOWERS);
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.EDELWEISS);
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SAXIFRAGE_PATCH);
        }
    }

    public static void arcticFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.arcticFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.ARCTIC_FLOWERS);
        }
    }

    public static void caribouMoss(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.caribouMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.CARIBOU_MOSS);
        }
    }

    public static void desertVegetation(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.cactusFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.CACTUS_FLOWERS);
        }
    }

    public static void forestFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.forestFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.FOREST_FLOWERS);
        }
    }

    public static void hangingMoss(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.hangingMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.HANGING_MOSS_PATCH);
        }
    }

    public static void jungleVegetation(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.lotus.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.LOTUS);
        }
    }

    public static void savannaFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.savannaFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SAVANNA_FLOWERS);
        }
    }

    public static void swampFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.swampFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SWAMP_FLOWERS);
        }
    }

    public static void swampReeds(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.swampReeds.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SWAMP_REEDS);
        }
    }

    public static void sourBerries(BiomeGenerationSettings.Builder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SOUR_BERRIES);
    }

    public static void sweetPeas(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.sweetPeas.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SWEET_PEAS);
        }
    }

    public static void witherRoses(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Misc.witherRoseGeneration.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.WITHER_ROSES);
        }
    }

    //Trees
    public static void appleTrees(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Trees.apple.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.PLAINS_APPLE_TREES);
        }
    }

    public static void hazelTrees(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Trees.hazel.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.FOREST_HAZEL_TREES);
        }
    }

    public static void borealForestTrees(BiomeGenerationSettings.Builder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetationPlacements.BOREAL_FOREST_TREES);
    }

    public static void sparseLarchTrees(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Trees.larch.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SPARSE_LARCH_TREES);
        }
    }

    public static void rareLarchTrees(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Trees.larch.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.RARE_LARCH_TREES);
        }
    }

    public static void clearingVegetation(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.arcticFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.CLEARING_VEGETATION);
        }
    }

    public static void ginkgoGroveVegetation(BiomeGenerationSettings.Builder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetationPlacements.GINKGO_GROVE_TREES, ModVegetationPlacements.GINKGO_GROVE_FLOWERS);
    }

    //Misc
    public static void peatAndMossyGravel(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Misc.peat.get()) {
            WorldGenUtil.addOre(builder, ModMiscFeaturePlacements.PEAT_DISK);
        }

        if (TIConfig.Misc.mossyGravel.get()) {
            WorldGenUtil.addOre(builder, ModMiscFeaturePlacements.MOSSY_GRAVEL_DISK);
        }
    }

    public static void borealForestRocks(BiomeGenerationSettings.Builder builder) {
        WorldGenUtil.addModification(builder, ModMiscFeaturePlacements.BOREAL_FOREST_ROCK);
    }
}
