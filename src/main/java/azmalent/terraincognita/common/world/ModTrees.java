package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModFeatures;
import azmalent.terraincognita.common.world.feature.config.HugeTreeFeatureConfig;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.OptionalInt;

public class ModTrees {
    public static class Configs {
        public static final BaseTreeFeatureConfig FANCY_BIRCH = (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()),
                new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4),
                new FancyTrunkPlacer(2, 9, 0),
                new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))
            ).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();

        public static final HugeTreeFeatureConfig HUGE_OAK = (new HugeTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.OAK_WOOD.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
                new BlobFoliagePlacer(FeatureSpread.func_242252_a(5), FeatureSpread.func_242252_a(5), 3),
                new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))
            ).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();

        public static final HugeTreeFeatureConfig HUGE_BIRCH = (new HugeTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.BIRCH_WOOD.getDefaultState()),
                new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()),
                new BlobFoliagePlacer(FeatureSpread.func_242252_a(5), FeatureSpread.func_242252_a(5), 3),
                new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))
        ).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();
    }

    public static final ConfiguredFeature<?, ?> SPRUCE_SHRUB = Feature.TREE.withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
            new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2),
            new StraightTrunkPlacer(1, 0, 0),
            new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()
    ).chance(4);

    public static final ConfiguredFeature<?, ?> DWARF_BIRCH = Feature.TREE.withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()),
            new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2),
            new StraightTrunkPlacer(2, 0, 0),
            new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()
    ).chance(4);

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_BIRCH = Feature.TREE.withConfiguration(Configs.FANCY_BIRCH);
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_BIRCH_BEES_0002 = Feature.TREE.withConfiguration(Configs.FANCY_BIRCH.func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT)));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_BIRCH_BEES_002 = Feature.TREE.withConfiguration(Configs.FANCY_BIRCH.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT)));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_BIRCH_BEES_005 = Feature.TREE.withConfiguration(Configs.FANCY_BIRCH.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT)));

    public static final ConfiguredFeature<HugeTreeFeatureConfig, ?> HUGE_OAK = ModFeatures.HUGE_TREE.get().withConfiguration(Configs.HUGE_OAK);
    public static final ConfiguredFeature<HugeTreeFeatureConfig, ?> HUGE_BIRCH = ModFeatures.HUGE_TREE.get().withConfiguration(Configs.HUGE_BIRCH);

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE;

    public static void configureFeatures() {
        if (TIConfig.Trees.apple.get()) {
            SimpleBlockStateProvider logProvider = new SimpleBlockStateProvider(ModBlocks.WoodTypes.APPLE.LOG.getBlock().getDefaultState());
            WeightedBlockStateProvider leavesProvider = new WeightedBlockStateProvider();
            leavesProvider.addWeightedBlockstate(ModBlocks.WoodTypes.APPLE.LEAVES.getBlock().getDefaultState(), 1);
            leavesProvider.addWeightedBlockstate(ModBlocks.WoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock().getDefaultState(), 1);

            APPLE = Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(logProvider, leavesProvider, new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(6, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build());
        }
    }
}
