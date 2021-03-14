package azmalent.terraincognita.common.world;

import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

public class ModDefaultFeatures {
    public static void withForestFlowers(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.FOREST_FLOWERS);
    }

    public static void withSwampFlowers(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.SWAMP_FLOWERS);
    }

    public static void withAlpineFlowers(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.ALPINE_FLOWERS);
    }

    public static void withSavannaFlowers(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.SAVANNA_FLOWERS);
    }

    public static void withDesertMarigolds(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.DESERT_MARIGOLDS);
    }

    public static void withJungleFlowers(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.JUNGLE_FLOWERS);
    }

    public static void withArcticFlowers(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.ARCTIC_FLOWERS);
    }

    public static void withSweetPeas(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.SWEET_PEAS);
    }

    public static void withSwampReeds(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.REEDS);
    }

    public static void withCaribouMoss(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.CARIBOU_MOSS);
    }

    public static void withSmallLilyPads(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.SMALL_LILYPADS);
    }

    public static void withLotuses(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.LOTUS);
    }

    public static void withHangingRoots(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.ROOTS);
    }

    public static void withHangingMoss(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.HANGING_MOSS);
    }

    public static void withAppleTrees(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModTrees.NATURAL_APPLE);
    }

    public static void withHazelTrees(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModTrees.NATURAL_HAZEL);
    }

    public static void withPeatAndMossyGravel(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addOre(builder, ModMiscFeatures.PEAT_DISK);
        WorldGenUtil.addOre(builder, ModMiscFeatures.MOSSY_GRAVEL_DISK);
    }

    //Vanilla biome tweaks
    public static void withExtraTundraFeatures(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.withSparseBerries(builder);

        WorldGenUtil.addVegetation(builder, ModTrees.SPRUCE_SHRUB);
        WorldGenUtil.addVegetation(builder, ModTrees.TUNDRA_BIRCH);
    }

    public static void withWitherRoses(BiomeGenerationSettingsBuilder builder) {
        WorldGenUtil.addVegetation(builder, ModVegetation.WITHER_ROSE);
    }
}
