package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.placement.ModMiscFeaturePlacements;
import azmalent.terraincognita.common.world.placement.ModTreePlacements;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

public class ModDefaultFeatures {
    public static void withForestFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.forestFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.FOREST_FLOWERS);
        }
    }

    public static void withSwampFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.swampFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SWAMP_FLOWERS);
        }
    }

    public static void withAlpineFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.alpineFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.ALPINE_FLOWERS);
        }
    }

    public static void withSavannaFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.savannaFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SAVANNA_FLOWERS);
        }
    }

    public static void withJungleVegetation(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.lotus.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.LOTUS);
        }
    }

    public static void withArcticFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.arcticFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.ARCTIC_FLOWERS);
        }
    }

    public static void withSweetPeas(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.sweetPeas.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SWEET_PEAS);
        }
    }

    public static void withSwampVegetation(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.smallLilypad.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SMALL_LILY_PADS);
        }

        if (TIConfig.Flora.sedge.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SEDGE);
        }
    }

    public static void withCaribouMoss(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.caribouMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.CARIBOU_MOSS);
        }
    }

    public static void withDesertVegetation(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.cactusFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.CACTUS_FLOWERS);
        }
    }

    public static void withSourBerries(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetationPlacements.SOUR_BERRIES);
    }

    public static void withHangingMoss(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.hangingMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.HANGING_MOSS);
        }
    }

    public static void withAppleTrees(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Trees.apple.get()) {
            WorldGenUtil.addVegetation(builder, ModTreePlacements.APPLE_CHECKED);
        }
    }

    public static void withHazelTrees(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Trees.hazel.get()) {
            WorldGenUtil.addVegetation(builder, ModTreePlacements.HAZEL_CHECKED);
        }
    }
    public static void withPeatAndMossyGravel(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Misc.peat.get()) {
            WorldGenUtil.addOre(builder, ModMiscFeaturePlacements.PEAT_DISK);
        }

        if (TIConfig.Misc.mossyGravel.get()) {
            WorldGenUtil.addOre(builder, ModMiscFeaturePlacements.MOSSY_GRAVEL_DISK);
        }
    }

    //Vanilla biome tweaks
    public static void withWitherRoses(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Misc.witherRoseGeneration.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationPlacements.WITHER_ROSES);
        }
    }
}
