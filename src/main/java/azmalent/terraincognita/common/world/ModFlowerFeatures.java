package azmalent.terraincognita.common.world;

import azmalent.terraincognita.common.integration.EnvironmentalIntegration;
import azmalent.terraincognita.common.integration.ModIntegration;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import azmalent.terraincognita.common.world.blockstateprovider.AlpineFlowerBlockStateProvider;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;

import static azmalent.terraincognita.common.world.ModFlowerFeatures.States.*;

public class ModFlowerFeatures {
    public static class States {
        static final BlockState WILD_GARLIC     = ModBlocks.WILD_GARLIC.getBlock().getDefaultState();
        static final BlockState FOXGLOVE        = ModBlocks.FOXGLOVE.getBlock().getDefaultState();
        static final BlockState YELLOW_PRIMROSE = ModBlocks.YELLOW_PRIMROSE.getBlock().getDefaultState();
        static final BlockState PINK_PRIMROSE   = ModBlocks.PINK_PRIMROSE.getBlock().getDefaultState();
        static final BlockState PURPLE_PRIMROSE = ModBlocks.PURPLE_PRIMROSE.getBlock().getDefaultState();
        static final BlockState FORGET_ME_NOT   = ModBlocks.FORGET_ME_NOT.getBlock().getDefaultState();
        static final BlockState GLOBEFLOWER     = ModBlocks.GLOBEFLOWER.getBlock().getDefaultState();
        static final BlockState WATER_FLAG      = ModBlocks.WATER_FLAG.getBlock().getDefaultState();
        static final BlockState DWARF_FIREWEED  = ModBlocks.DWARF_FIREWEED.getBlock().getDefaultState();
        static final BlockState WHITE_DRYAD     = ModBlocks.WHITE_DRYAD.getBlock().getDefaultState();
        static final BlockState FIREWEED        = ModBlocks.TALL_FIREWEED.getBlock().getDefaultState();
        static final BlockState WHITE_RHODODENDRON = ModBlocks.WHITE_RHODODENDRON.getBlock().getDefaultState();
        static final BlockState MARIGOLD        = ModBlocks.MARIGOLD.getBlock().getDefaultState();
        static final BlockState BLUE_LUPINE     = ModBlocks.BLUE_LUPINE.getBlock().getDefaultState();
        static final BlockState YELLOW_SNAPDRAGON = ModBlocks.YELLOW_SNAPDRAGON.getBlock().getDefaultState();
        static final BlockState RED_SNAPDRAGON  = ModBlocks.RED_SNAPDRAGON.getBlock().getDefaultState();
        static final BlockState MAGENTA_SNAPDRAGON = ModBlocks.MAGENTA_SNAPDRAGON.getBlock().getDefaultState();
        static final BlockState BLUE_IRIS       = ModBlocks.BLUE_IRIS.getBlock().getDefaultState();
        static final BlockState PURPLE_IRIS     = ModBlocks.PURPLE_IRIS.getBlock().getDefaultState();
        static final BlockState BLACK_IRIS      = ModBlocks.BLACK_IRIS.getBlock().getDefaultState();

        static final BlockState ROSE_BUSH   = Blocks.ROSE_BUSH.getDefaultState();
        static final BlockState PEONY       = Blocks.PEONY.getDefaultState();
        static final BlockState LILAC       = Blocks.LILAC.getDefaultState();
        static final BlockState SUNFLOWER   = Blocks.SUNFLOWER.getDefaultState();
        static final BlockState WITHER_ROSE = Blocks.WITHER_ROSE.getDefaultState();
    }

    public static class StateProviders {
        static final WeightedBlockStateProvider FOREST_FLOWERS = new WeightedBlockStateProvider().addWeightedBlockstate(WILD_GARLIC, 2).addWeightedBlockstate(FOXGLOVE, 2).addWeightedBlockstate(YELLOW_PRIMROSE, 1).addWeightedBlockstate(PINK_PRIMROSE, 1).addWeightedBlockstate(PURPLE_PRIMROSE, 1);
        static final WeightedBlockStateProvider SWAMP_FLOWERS  = new WeightedBlockStateProvider().addWeightedBlockstate(FORGET_ME_NOT, 1).addWeightedBlockstate(GLOBEFLOWER, 1);
        static final WeightedBlockStateProvider SAVANNA_FLOWERS = new WeightedBlockStateProvider().addWeightedBlockstate(MARIGOLD, 2).addWeightedBlockstate(BLUE_LUPINE, 3).addWeightedBlockstate(RED_SNAPDRAGON, 1).addWeightedBlockstate(YELLOW_SNAPDRAGON, 1).addWeightedBlockstate(MAGENTA_SNAPDRAGON, 1);
        static final WeightedBlockStateProvider JUNGLE_FLOWERS = new WeightedBlockStateProvider().addWeightedBlockstate(BLUE_IRIS, 1).addWeightedBlockstate(PURPLE_IRIS, 1).addWeightedBlockstate(BLACK_IRIS, 1);
        static final WeightedBlockStateProvider ARCTIC_FLOWERS = new WeightedBlockStateProvider().addWeightedBlockstate(DWARF_FIREWEED, 2).addWeightedBlockstate(WHITE_DRYAD, 3);
        static final WeightedBlockStateProvider ARCTIC_TALL_FLOWERS = new WeightedBlockStateProvider().addWeightedBlockstate(FIREWEED, 2).addWeightedBlockstate(WHITE_RHODODENDRON, 1);

        static final WeightedBlockStateProvider LUSH_PLAINS_TALL_FLOWERS = new WeightedBlockStateProvider().addWeightedBlockstate(States.ROSE_BUSH, 2).addWeightedBlockstate(States.PEONY, 2).addWeightedBlockstate(States.LILAC, 2).addWeightedBlockstate(States.SUNFLOWER, 2);
        static {
            EnvironmentalIntegration.addDelphiniums(LUSH_PLAINS_TALL_FLOWERS);
        }
    }

    public static class Configs {
        public static final BlockClusterFeatureConfig FOREST_FLOWERS = flowerConfig(StateProviders.FOREST_FLOWERS, 16);

        public static BlockClusterFeatureConfig SWAMP_SMALL_FLOWERS = flowerConfig(StateProviders.SWAMP_FLOWERS, 32);
        public static BlockClusterFeatureConfig WATER_FLAG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.WATER_FLAG), DoublePlantBlockPlacer.PLACER).tries(24).func_227317_b_().requiresWater().replaceable().build();
        public static SingleRandomFeature SWAMP_FLOWERS = new SingleRandomFeature(Lists.newArrayList(() -> initFlowerFeature(SWAMP_SMALL_FLOWERS), () -> initTallFlowerFeature(WATER_FLAG, -1, 4)));

        public static BlockClusterFeatureConfig ALPINE_FLOWERS = flowerConfig(AlpineFlowerBlockStateProvider.INSTANCE, 32);

        public static BlockClusterFeatureConfig SAVANNA_FLOWERS = flowerConfig(StateProviders.SAVANNA_FLOWERS, 32);
        public static BlockClusterFeatureConfig JUNGLE_FLOWERS = flowerConfig(StateProviders.JUNGLE_FLOWERS, 64);

        public static BlockClusterFeatureConfig ARCTIC_SMALL_FLOWERS = flowerConfig(StateProviders.ARCTIC_FLOWERS, 16);
        public static BlockClusterFeatureConfig ARCTIC_TALL_FLOWERS = new BlockClusterFeatureConfig.Builder(StateProviders.ARCTIC_TALL_FLOWERS, DoublePlantBlockPlacer.PLACER).tries(12).func_227317_b_().build();
        public static SingleRandomFeature ARCTIC_FLOWERS = new SingleRandomFeature(Lists.newArrayList(() -> initFlowerFeature(ARCTIC_SMALL_FLOWERS), () -> initTallFlowerFeature(ARCTIC_TALL_FLOWERS, -3, 4)));

        public static BlockClusterFeatureConfig LUSH_PLAINS_TALL_FLOWERS = new BlockClusterFeatureConfig.Builder(StateProviders.LUSH_PLAINS_TALL_FLOWERS, DoublePlantBlockPlacer.PLACER).tries(12).func_227317_b_().build();
        public static MultipleRandomFeatureConfig LUSH_PLAINS_FLOWERS = new MultipleRandomFeatureConfig(Lists.newArrayList(Features.FLOWER_PLAIN_DECORATED.withChance(0.8f)), initTallFlowerFeature(LUSH_PLAINS_TALL_FLOWERS, -2, 4));

        public static BlockClusterFeatureConfig WITHER_ROSE = flowerConfig(new SimpleBlockStateProvider(States.WITHER_ROSE), 128);

        private static BlockClusterFeatureConfig flowerConfig(BlockStateProvider provider, int tries) {
            return new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER).tries(tries).build();
        }
    }

    public static ConfiguredFeature<?, ?> FOREST_FLOWERS;
    public static ConfiguredFeature<?, ?> SWAMP_FLOWERS;
    public static ConfiguredFeature<?, ?> ALPINE_FLOWERS;
    public static ConfiguredFeature<?, ?> SAVANNA_FLOWERS;
    public static ConfiguredFeature<?, ?> JUNGLE_FLOWERS;
    public static ConfiguredFeature<?, ?> ARCTIC_FLOWERS;
    public static ConfiguredFeature<?, ?> SWEET_PEAS;

    public static ConfiguredFeature<?, ?> LUSH_PLAINS_FLOWERS;
    public static ConfiguredFeature<?, ?> WITHER_ROSE;

    private static ConfiguredFeature<?, ?> initFlowerFeature(BlockClusterFeatureConfig config) {
        return Feature.FLOWER.withConfiguration(config).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4);
    }

    private static ConfiguredFeature<?, ?> initTallFlowerFeature(BlockClusterFeatureConfig config, int min, int max) {
        return Feature.RANDOM_PATCH.withConfiguration(config).func_242730_a(FeatureSpread.func_242253_a(min, max)).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5);
    }

    private static ConfiguredFeature<?, ?> initSimpleRandomFeature(SingleRandomFeature config) {
        return Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(config).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4);
    }

    public static void registerFeatures() {
        FOREST_FLOWERS = ModConfiguredFeatures.register("forest_flowers", initFlowerFeature(Configs.FOREST_FLOWERS));
        SWAMP_FLOWERS = ModConfiguredFeatures.register("swamp_flowers", initSimpleRandomFeature(Configs.SWAMP_FLOWERS));
        ALPINE_FLOWERS = ModConfiguredFeatures.register("alpine_flowers", ModFeatures.ALPINE_FLOWERS.get().withConfiguration(Configs.ALPINE_FLOWERS).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4));
        SAVANNA_FLOWERS = ModConfiguredFeatures.register("savanna_flowers", initFlowerFeature(Configs.SAVANNA_FLOWERS));
        JUNGLE_FLOWERS = ModConfiguredFeatures.register("jungle_flowers", initFlowerFeature(Configs.JUNGLE_FLOWERS));
        ARCTIC_FLOWERS = ModConfiguredFeatures.register("arctic_flowers", initSimpleRandomFeature(Configs.ARCTIC_FLOWERS));
        SWEET_PEAS = ModConfiguredFeatures.register("sweet_peas", ModFeatures.SWEET_PEAS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).square());

        LUSH_PLAINS_FLOWERS = ModConfiguredFeatures.register("lush_plains_flowers", Feature.RANDOM_SELECTOR.withConfiguration(Configs.LUSH_PLAINS_FLOWERS).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4));
        WITHER_ROSE = ModConfiguredFeatures.register("wither_rose", initFlowerFeature(Configs.WITHER_ROSE));
    }
}
