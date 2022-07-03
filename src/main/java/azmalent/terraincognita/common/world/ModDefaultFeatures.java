package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.world.placement.ModMiscFeaturePlacements;
import azmalent.terraincognita.common.world.placement.ModTreePlacements;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class ModDefaultFeatures {
    //Spawns
    public static void butterflies(MobSpawnSettings.Builder spawns) {
        WorldGenUtil.addSpawn(spawns, ModEntities.BUTTERFLY, MobCategory.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
    }

    //Vegetation
    public static void addAlpineFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.alpineFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.ALPINE_FLOWERS);
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.EDELWEISS);
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SAXIFRAGE_PATCH);
        }
    }

    public static void addArcticFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.arcticFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.ARCTIC_FLOWERS);
        }
    }

    public static void addCaribouMoss(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.caribouMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.CARIBOU_MOSS);
        }
    }

    public static void addDesertVegetation(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.cactusFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.CACTUS_FLOWERS);
        }
    }

    public static void addForestFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.forestFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.FOREST_FLOWERS);
        }
    }

    public static void addHangingMoss(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.hangingMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.HANGING_MOSS_PATCH);
        }
    }

    public static void addJungleVegetation(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.lotus.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.LOTUS);
        }
    }

    public static void addSavannaFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.savannaFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SAVANNA_FLOWERS);
        }
    }

    public static void addSwampFlowers(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.swampFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SWAMP_FLOWERS);
        }
    }

    public static void addSedge(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.sedge.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SEDGE);
        }
    }

    public static void addSourBerries(BiomeGenerationSettings.Builder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SOUR_BERRIES);
    }

    public static void addSweetPeas(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Flora.sweetPeas.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SWEET_PEAS);
        }
    }

    public static void addWitherRoses(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Misc.witherRoseGeneration.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.WITHER_ROSES);
        }
    }

    //Trees
    public static void addAppleTrees(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Trees.apple.get()) {
            WorldGenUtil.addVegetation(builder, ModTreePlacements.PLAINS_APPLE_TREES);
        }
    }

    public static void addHazelTrees(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Trees.hazel.get()) {
            WorldGenUtil.addVegetation(builder, ModTreePlacements.FOREST_HAZEL_TREES);
        }
    }

    //Misc
    public static void addPeatAndMossyGravel(BiomeGenerationSettings.Builder builder) {
        if (TIConfig.Misc.peat.get()) {
            WorldGenUtil.addOre(builder, ModMiscFeaturePlacements.PEAT_DISK);
        }

        if (TIConfig.Misc.mossyGravel.get()) {
            WorldGenUtil.addOre(builder, ModMiscFeaturePlacements.MOSSY_GRAVEL_DISK);
        }
    }
}
