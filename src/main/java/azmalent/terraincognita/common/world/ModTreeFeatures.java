package azmalent.terraincognita.common.world;

import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.world.treedecorator.AppleTreeDecorator;
import azmalent.terraincognita.common.world.treedecorator.HazelnutTreeDecorator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

import net.minecraft.data.worldgen.Features;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;

public class ModTreeFeatures {
    public static class States {
        static final BlockState APPLE_LOG = ModWoodTypes.APPLE.LOG.getDefaultState();
        static final BlockState APPLE_LEAVES = ModWoodTypes.APPLE.LEAVES.getDefaultState();
        static final BlockState BLOSSOMING_APPLE_LEAVES = ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getDefaultState();
        static final BlockState HAZEL_LOG = ModWoodTypes.HAZEL.LOG.getDefaultState();
        static final BlockState HAZEL_LEAVES = ModWoodTypes.HAZEL.LEAVES.getDefaultState();
    }
    
    public static class StateProviders {
        static final SimpleStateProvider OAK_LOG       = simple(Blocks.OAK_LOG.defaultBlockState());
        static final SimpleStateProvider OAK_LEAVES    = simple(Blocks.OAK_LEAVES.defaultBlockState());
        static final SimpleStateProvider BIRCH_LOG     = simple(Blocks.BIRCH_LOG.defaultBlockState());
        static final SimpleStateProvider BIRCH_LEAVES  = simple(Blocks.BIRCH_LEAVES.defaultBlockState());
        static final SimpleStateProvider SPRUCE_LOG    = simple(Blocks.SPRUCE_LOG.defaultBlockState());
        static final SimpleStateProvider SPRUCE_LEAVES = simple(Blocks.SPRUCE_LEAVES.defaultBlockState());

        static final SimpleStateProvider APPLE_LOG      = simple(States.APPLE_LOG);
        static final WeightedStateProvider APPLE_LEAVES = new WeightedStateProvider().add(States.APPLE_LEAVES, 1).add(States.BLOSSOMING_APPLE_LEAVES, 1);
        static final SimpleStateProvider HAZEL_LOG      = simple(States.HAZEL_LOG);
        static final SimpleStateProvider HAZEL_LEAVES   = simple(States.HAZEL_LEAVES);

        private static SimpleStateProvider simple(BlockState state) {
            return new SimpleStateProvider(state);
        }
    }
    
    public static class Configs {
        public static final TreeConfiguration APPLE_TREE_GROWN;
        public static final TreeConfiguration APPLE_TREE;
        static {
            TreeConfiguration.TreeConfigurationBuilder builder = new TreeConfiguration.TreeConfigurationBuilder(StateProviders.APPLE_LOG, StateProviders.APPLE_LEAVES, new BlobFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines();
            APPLE_TREE_GROWN = builder.build();
            APPLE_TREE = builder.decorators(Lists.newArrayList(AppleTreeDecorator.INSTANCE)).build();
        }

        public static final TreeConfiguration HAZEL_GROWN;
        public static final TreeConfiguration HAZEL;
        static {
            TreeConfiguration.TreeConfigurationBuilder builder = new TreeConfiguration.TreeConfigurationBuilder(StateProviders.HAZEL_LOG, StateProviders.HAZEL_LEAVES, new FancyFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(4), 4), new FancyTrunkPlacer(5, 8, 0), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().heightmap(Heightmap.Types.MOTION_BLOCKING);
            HAZEL_GROWN = builder.build();
            HAZEL = builder.decorators(Lists.newArrayList(HazelnutTreeDecorator.INSTANCE)).build();
        }

        public static final TreeConfiguration TUNDRA_BIRCH = (new TreeConfiguration.TreeConfigurationBuilder(StateProviders.BIRCH_LOG, StateProviders.BIRCH_LEAVES, new BushFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(0, 0, 0))).heightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES).build();
        public static final TreeConfiguration OAK_SHRUB = (new TreeConfiguration.TreeConfigurationBuilder(StateProviders.OAK_LOG, StateProviders.OAK_LEAVES, new BushFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayersFeatureSize(0, 0, 0))).heightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES).build();
        public static final TreeConfiguration SPRUCE_SHRUB = (new TreeConfiguration.TreeConfigurationBuilder(StateProviders.SPRUCE_LOG, StateProviders.SPRUCE_LEAVES, new BushFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayersFeatureSize(0, 0, 0))).heightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES).build();

        public static final RandomFeatureConfiguration MUSKEG_TREES = new RandomFeatureConfiguration(ImmutableList.of(Features.MEGA_PINE.weighted(0.05f), Features.BIRCH.weighted(0.1f)), Features.PINE);
    }

    public static ConfiguredFeature<TreeConfiguration, ?> APPLE_TREE_GROWN;
    public static ConfiguredFeature<?, ?> APPLE_TREE;
    public static ConfiguredFeature<?, ?> EXTRA_APPLE_TREE;

    public static ConfiguredFeature<TreeConfiguration, ?> HAZEL_GROWN;
    public static ConfiguredFeature<?, ?> HAZEL;

    public static ConfiguredFeature<?, ?> LUSH_PLAINS_OAK;
    public static ConfiguredFeature<?, ?> OAK_SHRUB;
    public static ConfiguredFeature<?, ?> SPRUCE_SHRUB;
    public static ConfiguredFeature<?, ?> TUNDRA_BIRCH;

    public static ConfiguredFeature<?, ?> MUSKEG_TREES;

    public static void registerFeatures() {
        APPLE_TREE_GROWN = ModConfiguredFeatures.register("apple_tree_grown", Feature.TREE.configured(Configs.APPLE_TREE_GROWN));
        APPLE_TREE = ModConfiguredFeatures.register("apple_tree", Feature.TREE.configured(Configs.APPLE_TREE).chance(32));
        EXTRA_APPLE_TREE = ModConfiguredFeatures.register("extra_apple_tree", Feature.TREE.configured(Configs.APPLE_TREE).chance(12));

        HAZEL_GROWN = ModConfiguredFeatures.register("hazel_grown", Feature.TREE.configured(Configs.HAZEL_GROWN));
        HAZEL = ModConfiguredFeatures.register("hazel", Feature.TREE.configured(Configs.HAZEL).chance(24));

        LUSH_PLAINS_OAK = ModConfiguredFeatures.register("lush_plains_oak", Features.FANCY_OAK_BEES_005.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(3, 0.1F, 1))).chance(12));
        OAK_SHRUB = ModConfiguredFeatures.register("oak_shrub", Feature.TREE.configured(Configs.OAK_SHRUB).chance(4));
        SPRUCE_SHRUB = ModConfiguredFeatures.register("spruce_shrub", Feature.TREE.configured(Configs.SPRUCE_SHRUB).chance(4));
        TUNDRA_BIRCH = ModConfiguredFeatures.register("tundra_birch", Feature.TREE.configured(Configs.TUNDRA_BIRCH).chance(4));

        MUSKEG_TREES = ModConfiguredFeatures.register("muskeg_trees", Feature.RANDOM_SELECTOR.configured(Configs.MUSKEG_TREES).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(4, 0.1F, 1))));
    }
}