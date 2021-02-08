package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class ModTrees {
    public static class Configs {
        public static final BaseTreeFeatureConfig DWARF_BIRCH = (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()),
                new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2),
                new StraightTrunkPlacer(2, 0, 0),
                new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES
            ).build();

        public static final BaseTreeFeatureConfig SPRUCE_SHRUB = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
                new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2),
                new StraightTrunkPlacer(1, 0, 0),
                new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES
            ).build();
    }

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SPRUCE_SHRUB = register("spruce_shrub", Feature.TREE.withConfiguration(Configs.SPRUCE_SHRUB).chance(4));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DWARF_BIRCH = register("dwarf_birch", Feature.TREE.withConfiguration(Configs.DWARF_BIRCH).chance(4));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RARE_BIRCHES = register("rare_birches", Features.BIRCH.chance(8));

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> RARE_APPLE;

    @SuppressWarnings("unchecked")
    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> register(String id, ConfiguredFeature feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }

    public static void configureFeatures() {
        if (TIConfig.Trees.apple.get()) {
            SimpleBlockStateProvider logProvider = new SimpleBlockStateProvider(ModBlocks.WoodTypes.APPLE.LOG.getBlock().getDefaultState());
            WeightedBlockStateProvider leavesProvider = new WeightedBlockStateProvider();
            leavesProvider.addWeightedBlockstate(ModBlocks.WoodTypes.APPLE.LEAVES.getBlock().getDefaultState(), 1);
            leavesProvider.addWeightedBlockstate(ModBlocks.WoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock().getDefaultState(), 1);

            BaseTreeFeatureConfig.Builder builder = (new BaseTreeFeatureConfig.Builder(logProvider, leavesProvider, new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(6, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines();
            APPLE = register("apple_tree", Feature.TREE.withConfiguration(builder.build()));
            RARE_APPLE = register("rare_apple_trees", Feature.TREE.withConfiguration(builder.build()).chance(32));
        }
    }
}
