package azmalent.terraincognita.common.world;

import azmalent.terraincognita.common.integration.EnvironmentalIntegration;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import azmalent.terraincognita.common.world.blockstateprovider.AlpineFlowerBlockStateProvider;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.blockplacers.DoublePlantPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.gen.feature.*;

import static azmalent.terraincognita.common.world.ModFlowerFeatures.States.*;

import net.minecraft.data.worldgen.Features;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;

public class ModFlowerFeatures {
    public static class States {
        static final BlockState WILD_GARLIC     = ModBlocks.WILD_GARLIC.getBlock().defaultBlockState();
        static final BlockState FOXGLOVE        = ModBlocks.FOXGLOVE.getBlock().defaultBlockState();
        static final BlockState YELLOW_PRIMROSE = ModBlocks.YELLOW_PRIMROSE.getBlock().defaultBlockState();
        static final BlockState PINK_PRIMROSE   = ModBlocks.PINK_PRIMROSE.getBlock().defaultBlockState();
        static final BlockState PURPLE_PRIMROSE = ModBlocks.PURPLE_PRIMROSE.getBlock().defaultBlockState();
        static final BlockState FORGET_ME_NOT   = ModBlocks.FORGET_ME_NOT.getBlock().defaultBlockState();
        static final BlockState GLOBEFLOWER     = ModBlocks.GLOBEFLOWER.getBlock().defaultBlockState();
        static final BlockState WATER_FLAG      = ModBlocks.WATER_FLAG.getBlock().defaultBlockState();
        static final BlockState DWARF_FIREWEED  = ModBlocks.DWARF_FIREWEED.getBlock().defaultBlockState();
        static final BlockState WHITE_DRYAD     = ModBlocks.WHITE_DRYAD.getBlock().defaultBlockState();
        static final BlockState FIREWEED        = ModBlocks.FIREWEED.getBlock().defaultBlockState();
        static final BlockState WHITE_RHODODENDRON = ModBlocks.WHITE_RHODODENDRON.getBlock().defaultBlockState();
        static final BlockState MARIGOLD        = ModBlocks.MARIGOLD.getBlock().defaultBlockState();
        static final BlockState BLUE_LUPINE     = ModBlocks.BLUE_LUPINE.getBlock().defaultBlockState();
        static final BlockState SNAPDRAGON  = ModBlocks.SNAPDRAGON.getBlock().defaultBlockState();
        static final BlockState GLADIOLUS   = ModBlocks.GLADIOLUS.getBlock().defaultBlockState();
        static final BlockState GERANIUM    = ModBlocks.GERANIUM.getBlock().defaultBlockState();
        static final BlockState OLEANDER    = ModBlocks.OLEANDER.getBlock().defaultBlockState();
        static final BlockState SAGE        = ModBlocks.SAGE.getBlock().defaultBlockState();
        static final BlockState BLUE_IRIS       = ModBlocks.BLUE_IRIS.getBlock().defaultBlockState();
        static final BlockState PURPLE_IRIS     = ModBlocks.PURPLE_IRIS.getBlock().defaultBlockState();
        static final BlockState BLACK_IRIS      = ModBlocks.BLACK_IRIS.getBlock().defaultBlockState();
        static final BlockState CACTUS_FLOWER   = ModBlocks.CACTUS_FLOWER.getBlock().defaultBlockState();

        static final BlockState ROSE_BUSH   = Blocks.ROSE_BUSH.defaultBlockState();
        static final BlockState PEONY       = Blocks.PEONY.defaultBlockState();
        static final BlockState LILAC       = Blocks.LILAC.defaultBlockState();
        static final BlockState SUNFLOWER   = Blocks.SUNFLOWER.defaultBlockState();
        static final BlockState WITHER_ROSE = Blocks.WITHER_ROSE.defaultBlockState();
    }

    public static class StateProviders {
        static final WeightedStateProvider FOREST_FLOWERS = new WeightedStateProvider().add(WILD_GARLIC, 2).add(FOXGLOVE, 2).add(YELLOW_PRIMROSE, 1).add(PINK_PRIMROSE, 1).add(PURPLE_PRIMROSE, 1);
        static final WeightedStateProvider SWAMP_FLOWERS  = new WeightedStateProvider().add(FORGET_ME_NOT, 1).add(GLOBEFLOWER, 1);
        static final WeightedStateProvider SAVANNA_FLOWERS = new WeightedStateProvider().add(MARIGOLD, 2).add(BLUE_LUPINE, 3).add(GLADIOLUS, 1).add(SNAPDRAGON, 1).add(GERANIUM, 1);
        static final WeightedStateProvider SAVANNA_TALL_FLOWERS = new WeightedStateProvider().add(OLEANDER, 2).add(SAGE, 3);
        static final WeightedStateProvider ARCTIC_FLOWERS = new WeightedStateProvider().add(DWARF_FIREWEED, 2).add(WHITE_DRYAD, 3);
        static final WeightedStateProvider ARCTIC_TALL_FLOWERS = new WeightedStateProvider().add(FIREWEED, 2).add(WHITE_RHODODENDRON, 1);
        static final WeightedStateProvider JUNGLE_FLOWERS = new WeightedStateProvider().add(BLUE_IRIS, 1).add(PURPLE_IRIS, 1).add(BLACK_IRIS, 1);

        static final WeightedStateProvider LUSH_PLAINS_TALL_FLOWERS = new WeightedStateProvider().add(States.ROSE_BUSH, 2).add(States.PEONY, 2).add(States.LILAC, 2).add(States.SUNFLOWER, 2);
        static {
            EnvironmentalIntegration.addDelphiniums(LUSH_PLAINS_TALL_FLOWERS);
        }
    }

    public static class Configs {
        public static final RandomPatchConfiguration FOREST_FLOWERS = flowerConfig(StateProviders.FOREST_FLOWERS, 16);

        public static RandomPatchConfiguration SWAMP_SMALL_FLOWERS = flowerConfig(StateProviders.SWAMP_FLOWERS, 32);
        public static RandomPatchConfiguration WATER_FLAG = new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(States.WATER_FLAG), DoublePlantPlacer.INSTANCE).tries(24).noProjection().needWater().canReplace().build();
        public static SimpleRandomFeatureConfiguration SWAMP_FLOWERS = new SimpleRandomFeatureConfiguration(Lists.newArrayList(() -> initFlowerFeature(SWAMP_SMALL_FLOWERS), () -> initTallFlowerFeature(WATER_FLAG, -1, 4)));

        public static RandomPatchConfiguration ALPINE_FLOWERS = flowerConfig(AlpineFlowerBlockStateProvider.INSTANCE, 32);

        public static RandomPatchConfiguration SAVANNA_SMALL_FLOWERS = flowerConfig(StateProviders.SAVANNA_FLOWERS, 32);
        public static RandomPatchConfiguration SAVANNA_TALL_FLOWERS = new RandomPatchConfiguration.GrassConfigurationBuilder(StateProviders.SAVANNA_TALL_FLOWERS, DoublePlantPlacer.INSTANCE).tries(12).noProjection().build();
        public static SimpleRandomFeatureConfiguration SAVANNA_FLOWERS = new SimpleRandomFeatureConfiguration(Lists.newArrayList(() -> initFlowerFeature(SAVANNA_SMALL_FLOWERS), () -> initTallFlowerFeature(SAVANNA_TALL_FLOWERS, -3, 4)));

        public static RandomPatchConfiguration JUNGLE_FLOWERS = flowerConfig(StateProviders.JUNGLE_FLOWERS, 64);

        public static RandomPatchConfiguration ARCTIC_SMALL_FLOWERS = flowerConfig(StateProviders.ARCTIC_FLOWERS, 16);
        public static RandomPatchConfiguration ARCTIC_TALL_FLOWERS = new RandomPatchConfiguration.GrassConfigurationBuilder(StateProviders.ARCTIC_TALL_FLOWERS, DoublePlantPlacer.INSTANCE).tries(12).noProjection().build();
        public static SimpleRandomFeatureConfiguration ARCTIC_FLOWERS = new SimpleRandomFeatureConfiguration(Lists.newArrayList(() -> initFlowerFeature(ARCTIC_SMALL_FLOWERS), () -> initTallFlowerFeature(ARCTIC_TALL_FLOWERS, -3, 4)));

        public static RandomPatchConfiguration CACTUS_FLOWERS = flowerConfig(new SimpleStateProvider(CACTUS_FLOWER), 1024);

        public static RandomPatchConfiguration LUSH_PLAINS_TALL_FLOWERS = new RandomPatchConfiguration.GrassConfigurationBuilder(StateProviders.LUSH_PLAINS_TALL_FLOWERS, DoublePlantPlacer.INSTANCE).tries(12).noProjection().build();
        public static RandomFeatureConfiguration LUSH_PLAINS_FLOWERS = new RandomFeatureConfiguration(Lists.newArrayList(Features.FLOWER_PLAIN_DECORATED.weighted(0.8f)), initTallFlowerFeature(LUSH_PLAINS_TALL_FLOWERS, -2, 4));

        public static RandomPatchConfiguration WITHER_ROSE = flowerConfig(new SimpleStateProvider(States.WITHER_ROSE), 128);

        private static RandomPatchConfiguration flowerConfig(BlockStateProvider provider, int tries) {
            return new RandomPatchConfiguration.GrassConfigurationBuilder(provider, SimpleBlockPlacer.INSTANCE).tries(tries).build();
        }
    }

    public static ConfiguredFeature<?, ?> FOREST_FLOWERS;
    public static ConfiguredFeature<?, ?> SWAMP_FLOWERS;
    public static ConfiguredFeature<?, ?> ALPINE_FLOWERS;
    public static ConfiguredFeature<?, ?> SAVANNA_FLOWERS;
    public static ConfiguredFeature<?, ?> JUNGLE_FLOWERS;
    public static ConfiguredFeature<?, ?> ARCTIC_FLOWERS;
    public static ConfiguredFeature<?, ?> CACTUS_FLOWERS;
    public static ConfiguredFeature<?, ?> SWEET_PEAS;

    public static ConfiguredFeature<?, ?> LUSH_PLAINS_FLOWERS;
    public static ConfiguredFeature<?, ?> WITHER_ROSE;

    private static ConfiguredFeature<?, ?> initFlowerFeature(RandomPatchConfiguration config) {
        return Feature.FLOWER.configured(config).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4);
    }

    private static ConfiguredFeature<?, ?> initTallFlowerFeature(RandomPatchConfiguration config, int min, int max) {
        return Feature.RANDOM_PATCH.configured(config).count(UniformInt.of(min, max)).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(5);
    }

    private static ConfiguredFeature<?, ?> initSimpleRandomFeature(SimpleRandomFeatureConfiguration config) {
        return Feature.SIMPLE_RANDOM_SELECTOR.configured(config).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4);
    }

    public static void registerFeatures() {
        FOREST_FLOWERS = ModConfiguredFeatures.register("forest_flowers", initFlowerFeature(Configs.FOREST_FLOWERS));
        SWAMP_FLOWERS = ModConfiguredFeatures.register("swamp_flowers", initSimpleRandomFeature(Configs.SWAMP_FLOWERS));
        ALPINE_FLOWERS = ModConfiguredFeatures.register("alpine_flowers", ModFeatures.ALPINE_FLOWERS.get().configured(Configs.ALPINE_FLOWERS).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4));
        SAVANNA_FLOWERS = ModConfiguredFeatures.register("savanna_flowers", initSimpleRandomFeature(Configs.SAVANNA_FLOWERS));
        JUNGLE_FLOWERS = ModConfiguredFeatures.register("jungle_flowers", initFlowerFeature(Configs.JUNGLE_FLOWERS));
        ARCTIC_FLOWERS = ModConfiguredFeatures.register("arctic_flowers", initSimpleRandomFeature(Configs.ARCTIC_FLOWERS));
        CACTUS_FLOWERS = ModConfiguredFeatures.register("cactus_flowers", initFlowerFeature(Configs.CACTUS_FLOWERS));
        SWEET_PEAS = ModConfiguredFeatures.register("sweet_peas", ModFeatures.SWEET_PEAS.get().configured(FeatureConfiguration.NONE).squared());

        LUSH_PLAINS_FLOWERS = ModConfiguredFeatures.register("lush_plains_flowers", Feature.RANDOM_SELECTOR.configured(Configs.LUSH_PLAINS_FLOWERS).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4));
        WITHER_ROSE = ModConfiguredFeatures.register("wither_rose", initFlowerFeature(Configs.WITHER_ROSE));
    }
}
