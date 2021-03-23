package azmalent.terraincognita.common.world;

import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.world.treedecorator.AppleTreeDecorator;
import azmalent.terraincognita.common.world.treedecorator.HazelnutTreeDecorator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.OptionalInt;

public class ModTreeFeatures {
    public static class States {
        static final BlockState APPLE_LOG = ModWoodTypes.APPLE.LOG.getDefaultState();
        static final BlockState APPLE_LEAVES = ModWoodTypes.APPLE.LEAVES.getDefaultState();
        static final BlockState BLOSSOMING_APPLE_LEAVES = ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getDefaultState();
        static final BlockState HAZEL_LOG = ModWoodTypes.HAZEL.LOG.getDefaultState();
        static final BlockState HAZEL_LEAVES = ModWoodTypes.HAZEL.LEAVES.getDefaultState();
    }
    
    public static class StateProviders {
        static final SimpleBlockStateProvider OAK_LOG       = simple(Blocks.OAK_LOG.getDefaultState());
        static final SimpleBlockStateProvider OAK_LEAVES    = simple(Blocks.OAK_LEAVES.getDefaultState());
        static final SimpleBlockStateProvider BIRCH_LOG     = simple(Blocks.BIRCH_LOG.getDefaultState());
        static final SimpleBlockStateProvider BIRCH_LEAVES  = simple(Blocks.BIRCH_LEAVES.getDefaultState());
        static final SimpleBlockStateProvider SPRUCE_LOG    = simple(Blocks.SPRUCE_LOG.getDefaultState());
        static final SimpleBlockStateProvider SPRUCE_LEAVES = simple(Blocks.SPRUCE_LEAVES.getDefaultState());

        static final SimpleBlockStateProvider APPLE_LOG      = simple(States.APPLE_LOG);
        static final WeightedBlockStateProvider APPLE_LEAVES = new WeightedBlockStateProvider().addWeightedBlockstate(States.APPLE_LEAVES, 1).addWeightedBlockstate(States.BLOSSOMING_APPLE_LEAVES, 1);
        static final SimpleBlockStateProvider HAZEL_LOG      = simple(States.HAZEL_LOG);
        static final SimpleBlockStateProvider HAZEL_LEAVES   = simple(States.HAZEL_LEAVES);

        private static SimpleBlockStateProvider simple(BlockState state) {
            return new SimpleBlockStateProvider(state);
        }
    }
    
    public static class Configs {
        public static final BaseTreeFeatureConfig APPLE_TREE_GROWN;
        public static final BaseTreeFeatureConfig APPLE_TREE;
        static {
            BaseTreeFeatureConfig.Builder builder = new BaseTreeFeatureConfig.Builder(StateProviders.APPLE_LOG, StateProviders.APPLE_LEAVES, new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines();
            APPLE_TREE_GROWN = builder.build();
            APPLE_TREE = builder.setDecorators(Lists.newArrayList(AppleTreeDecorator.INSTANCE)).build();
        }

        public static final BaseTreeFeatureConfig HAZEL_GROWN;
        public static final BaseTreeFeatureConfig HAZEL;
        static {
            BaseTreeFeatureConfig.Builder builder = new BaseTreeFeatureConfig.Builder(StateProviders.HAZEL_LOG, StateProviders.HAZEL_LEAVES, new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(5, 8, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING);
            HAZEL_GROWN = builder.build();
            HAZEL = builder.setDecorators(Lists.newArrayList(HazelnutTreeDecorator.INSTANCE)).build();
        }

        public static final BaseTreeFeatureConfig TUNDRA_BIRCH = (new BaseTreeFeatureConfig.Builder(StateProviders.BIRCH_LOG, StateProviders.BIRCH_LEAVES, new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build();
        public static final BaseTreeFeatureConfig OAK_SHRUB = (new BaseTreeFeatureConfig.Builder(StateProviders.OAK_LOG, StateProviders.OAK_LEAVES, new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build();
        public static final BaseTreeFeatureConfig SPRUCE_SHRUB = (new BaseTreeFeatureConfig.Builder(StateProviders.SPRUCE_LOG, StateProviders.SPRUCE_LEAVES, new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build();

        public static final MultipleRandomFeatureConfig MUSKEG_TREES = new MultipleRandomFeatureConfig(ImmutableList.of(Features.MEGA_PINE.withChance(0.05f), Features.BIRCH.withChance(0.1f)), Features.PINE);
    }

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE_TREE_GROWN;
    public static ConfiguredFeature<?, ?> APPLE_TREE;
    public static ConfiguredFeature<?, ?> EXTRA_APPLE_TREE;

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> HAZEL_GROWN;
    public static ConfiguredFeature<?, ?> HAZEL;

    public static ConfiguredFeature<?, ?> LUSH_PLAINS_OAK;
    public static ConfiguredFeature<?, ?> OAK_SHRUB;
    public static ConfiguredFeature<?, ?> SPRUCE_SHRUB;
    public static ConfiguredFeature<?, ?> TUNDRA_BIRCH;

    public static ConfiguredFeature<?, ?> MUSKEG_TREES;

    public static void registerFeatures() {
        APPLE_TREE_GROWN = ModConfiguredFeatures.register("apple_tree_grown", Feature.TREE.withConfiguration(Configs.APPLE_TREE_GROWN));
        APPLE_TREE = ModConfiguredFeatures.register("apple_tree", Feature.TREE.withConfiguration(Configs.APPLE_TREE).chance(32));
        EXTRA_APPLE_TREE = ModConfiguredFeatures.register("extra_apple_tree", Feature.TREE.withConfiguration(Configs.APPLE_TREE).chance(12));

        HAZEL_GROWN = ModConfiguredFeatures.register("hazel_grown", Feature.TREE.withConfiguration(Configs.HAZEL_GROWN));
        HAZEL = ModConfiguredFeatures.register("hazel", Feature.TREE.withConfiguration(Configs.HAZEL).chance(24));

        LUSH_PLAINS_OAK = ModConfiguredFeatures.register("lush_plains_oak", Features.FANCY_OAK_BEES_005.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1))).chance(12));
        OAK_SHRUB = ModConfiguredFeatures.register("oak_shrub", Feature.TREE.withConfiguration(Configs.OAK_SHRUB).chance(4));
        SPRUCE_SHRUB = ModConfiguredFeatures.register("spruce_shrub", Feature.TREE.withConfiguration(Configs.SPRUCE_SHRUB).chance(4));
        TUNDRA_BIRCH = ModConfiguredFeatures.register("tundra_birch", Feature.TREE.withConfiguration(Configs.TUNDRA_BIRCH).chance(4));

        MUSKEG_TREES = ModConfiguredFeatures.register("muskeg_trees", Feature.RANDOM_SELECTOR.withConfiguration(Configs.MUSKEG_TREES).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(4, 0.1F, 1))));
    }
}