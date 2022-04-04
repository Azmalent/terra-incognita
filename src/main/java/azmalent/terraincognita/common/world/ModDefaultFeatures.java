package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

public class ModDefaultFeatures {
    public static void withForestFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.forestFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.FOREST_FLOWERS);
        }
    }

    public static void withSwampFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.swampFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.SWAMP_FLOWERS);
        }
    }

    public static void withAlpineFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.alpineFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.ALPINE_FLOWERS);
        }
    }

    public static void withSavannaFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.savannaFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.SAVANNA_FLOWERS);
        }
    }

    public static void withJungleVegetation(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.lotus.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationFeatures.LOTUS);
        }
    }

    public static void withArcticFlowers(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.arcticFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.ARCTIC_FLOWERS);
        }
    }

    public static void withSweetPeas(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.sweetPeas.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.SWEET_PEAS);
        }
    }

    public static void withSwampVegetation(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.smallLilypad.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationFeatures.SMALL_LILY_PADS);
        }

        if (TIConfig.Flora.reeds.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationFeatures.REEDS);
        }
    }

    public static void withCaribouMoss(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.caribouMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationFeatures.CARIBOU_MOSS);
        }
    }

    public static void withDesertVegetation(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.cactusFlowers.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.CACTUS_FLOWERS);
        }
    }

    public static void withSourBerries(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetationFeatures.SOUR_BERRIES);
    }

    public static void withHangingRoots(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.roots.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationFeatures.ROOTS);
        }
    }

    public static void withHangingMoss(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Flora.hangingMoss.get()) {
            WorldGenUtil.addVegetation(builder, ModVegetationFeatures.HANGING_MOSS);
        }
    }

    public static void withAppleTrees(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Trees.apple.get()) {
            WorldGenUtil.addVegetation(builder, ModTreeFeatures.APPLE_TREE);
        }
    }

    public static void withHazelTrees(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Trees.hazel.get()) {
            WorldGenUtil.addVegetation(builder, ModTreeFeatures.HAZEL);
        }
    }
    public static void withPeatAndMossyGravel(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Misc.peat.get()) {
            WorldGenUtil.addOre(builder, ModConfiguredFeatures.PEAT_DISK);
        }

        if (TIConfig.Misc.mossyGravel.get()) {
            WorldGenUtil.addOre(builder, ModConfiguredFeatures.MOSSY_GRAVEL_DISK);
        }
    }

    //Vanilla biome tweaks
    public static void withWitherRoses(BiomeGenerationSettingsBuilder builder) {
        if (TIConfig.Misc.witherRoseGeneration.get()) {
            WorldGenUtil.addVegetation(builder, ModFlowerFeatures.WITHER_ROSE);
        }
    }
}
