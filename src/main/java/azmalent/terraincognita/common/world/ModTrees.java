package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.world.treedecorator.AppleTreeDecorator;
import azmalent.terraincognita.common.world.treedecorator.HazelnutTreeDecorator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.OptionalInt;

public class ModTrees {
    public static class Configs {
        public static final BaseTreeFeatureConfig TUNDRA_BIRCH = (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()),
                new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                new StraightTrunkPlacer(4, 2, 0),
                new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build();

        public static final BaseTreeFeatureConfig OAK_SHRUB = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
                new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2),
                new StraightTrunkPlacer(1, 0, 0),
                new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build();

        public static final BaseTreeFeatureConfig SPRUCE_SHRUB = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
                new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2),
                new StraightTrunkPlacer(1, 0, 0),
                new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build();

        public static final MultipleRandomFeatureConfig MUSKEG_TREES = new MultipleRandomFeatureConfig(ImmutableList.of(Features.MEGA_PINE.withChance(0.1F)), Features.PINE);
    }

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> OAK_SHRUB = register("oak_shrub", Feature.TREE.withConfiguration(Configs.OAK_SHRUB).chance(4));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SPRUCE_SHRUB = register("spruce_shrub", Feature.TREE.withConfiguration(Configs.SPRUCE_SHRUB).chance(4));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> TUNDRA_BIRCH = register("tundra_birch", Feature.TREE.withConfiguration(Configs.TUNDRA_BIRCH).chance(4));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> LUSH_PLAINS_OAK = register("lush_plains_oak", Features.FANCY_OAK_BEES_005.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1))).chance(12));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MUSKEG_TREES = register("muskeg_trees", Feature.RANDOM_SELECTOR.withConfiguration(Configs.MUSKEG_TREES).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))).chance(2));

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> NATURAL_APPLE;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> LUSH_PLAINS_APPLE;

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> HAZEL;
	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> NATURAL_HAZEL;

    @SuppressWarnings("unchecked")
    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> register(String id, ConfiguredFeature feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }

    public static void configureFeatures() {
        SimpleBlockStateProvider appleLogProvider = new SimpleBlockStateProvider(ModWoodTypes.APPLE.LOG.getDefaultState());
        WeightedBlockStateProvider appleLeavesProvider = new WeightedBlockStateProvider().addWeightedBlockstate(ModWoodTypes.APPLE.LEAVES.getDefaultState(), 1).addWeightedBlockstate(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getDefaultState(), 1);
        BaseTreeFeatureConfig.Builder appleBuilder = (new BaseTreeFeatureConfig.Builder(appleLogProvider, appleLeavesProvider, new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines();
        APPLE = register("apple_tree", Feature.TREE.withConfiguration(appleBuilder.build()));

        SimpleBlockStateProvider hazelLogProvider = new SimpleBlockStateProvider(ModWoodTypes.HAZEL.LOG.getDefaultState());
        SimpleBlockStateProvider hazelLeavesProvider = new SimpleBlockStateProvider(ModWoodTypes.HAZEL.LEAVES.getDefaultState());
        BaseTreeFeatureConfig.Builder hazelBuilder = (new BaseTreeFeatureConfig.Builder(hazelLogProvider, hazelLeavesProvider, new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(5, 8, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING);
        HAZEL = register("hazel_tree", Feature.TREE.withConfiguration(hazelBuilder.build()));

        if (TIConfig.Trees.apple.get()) {
            BaseTreeFeatureConfig config = appleBuilder.setDecorators(Lists.newArrayList(AppleTreeDecorator.INSTANCE)).build();
            NATURAL_APPLE = register("natural_apple_tree", Feature.TREE.withConfiguration(config).chance(32));
            if (TIConfig.Biomes.lushPlainsWeight.get() > 0) {
                LUSH_PLAINS_APPLE = register("lush_plains_apple_tree", Feature.TREE.withConfiguration(config).chance(12));
            }
        }

        if (TIConfig.Trees.hazel.get()) {
            NATURAL_HAZEL = register("natural_hazel_tree", Feature.TREE.withConfiguration(hazelBuilder.setDecorators(Lists.newArrayList(HazelnutTreeDecorator.INSTANCE)).build()).chance(24));
        }
    }
}
