package azmalent.terraincognita.common.world;

import azmalent.terraincognita.common.block.plant.SmallLilyPadBlock;
import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import azmalent.terraincognita.common.world.feature.*;
import azmalent.terraincognita.common.world.stateprovider.AlpineFlowerStateProvider;
import azmalent.terraincognita.common.world.tree.AppleTree;
import azmalent.terraincognita.common.world.tree.HazelTree;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import static azmalent.terraincognita.common.registry.ModBlocks.*;
import static azmalent.terraincognita.util.ConfiguredFeatureHelper.*;
import static net.minecraft.data.worldgen.placement.PlacementUtils.inlinePlaced;

public class ModConfiguredFeatures {
    /*
     * Flowers
     */
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> FOREST_FLOWERS = registerFlowerFeature(
        "forest_flowers", 16,
        new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(WILD_GARLIC.defaultBlockState(), 2)
                .add(FOXGLOVE.defaultBlockState(), 2)
                .add(PINK_PRIMROSE.defaultBlockState(), 1)
                .add(PURPLE_PRIMROSE.defaultBlockState(), 1)
                .add(YELLOW_PRIMROSE.defaultBlockState(), 1)
                .build()
        )
    );

    public static Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> SWAMP_FLOWERS = registerRandomFeature(
        "swamp_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(FORGET_ME_NOT.defaultBlockState(), 1)
                .add(GLOBEFLOWER.defaultBlockState(), 1)
                .build()
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(WATER_FLAG.defaultBlockState())
        )))
    );
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> ALPINE_FLOWERS = registerFlowerFeature(
        "alpine_flowers", 32, AlpineFlowerStateProvider.INSTANCE
    );

    public static Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> SAVANNA_FLOWERS = registerRandomFeature(
        "savanna_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(MARIGOLD.defaultBlockState(), 4)
                .add(SNAPDRAGON.defaultBlockState(), 4)
                .add(BLACK_IRIS.defaultBlockState(), 1)
                .add(BLUE_IRIS.defaultBlockState(), 1)
                .add(PURPLE_IRIS.defaultBlockState(), 1)
                .build()
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(SAGE.defaultBlockState())
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(OLEANDER.defaultBlockState())
        )))
    );

    public static Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> ARCTIC_FLOWERS  = registerRandomFeature(
        "arctic_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(HEATHER.defaultBlockState(), 1)
                .add(WHITE_DRYAD.defaultBlockState(), 1)
                .build()
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(FIREWEED.defaultBlockState())
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(WHITE_RHODODENDRON.defaultBlockState())
        )))
    );

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> CACTUS_FLOWERS = registerFeature(
        "cactus_flowers", Feature.RANDOM_PATCH, randomPatchConfig(1024, BlockStateProvider.simple(CACTUS_FLOWER.defaultBlockState()))
    );

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, SweetPeasFeature>> SWEET_PEAS = registerFeature("sweet_peas", ModFeatures.SWEET_PEAS);

    public static Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> LUSH_PLAINS_FLOWERS = registerRandomFeature(
        "lush_plains_flowers",
        inlinePlaced(VegetationFeatures.FLOWER_PLAIN),

        inlinePlaced(Feature.RANDOM_PATCH, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(Blocks.ROSE_BUSH.defaultBlockState(), 1)
                .add(Blocks.PEONY.defaultBlockState(), 1)
                .add(Blocks.LILAC.defaultBlockState(), 1)
                .add(Blocks.SUNFLOWER.defaultBlockState(), 1)
                .build()
        )))
    );

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> WITHER_ROSE = registerFlowerFeature(
        "wither_roses", 128, BlockStateProvider.simple(Blocks.WITHER_ROSE.defaultBlockState())
    );



    /*
     * Other vegetation
     */
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> LOTUS = registerRandomPatchFeature(
        "lotus", 10,
        new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(Blocks.LILY_PAD.defaultBlockState(), 3)
                .add(PINK_LOTUS.defaultBlockState(), 1)
                .add(WHITE_LOTUS.defaultBlockState(), 1)
                .add(YELLOW_LOTUS.defaultBlockState(), 1)
                .build()
        )
    );

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> SMALL_LILY_PADS = registerRandomPatchFeature(
        "small_lilypad", 10,
        new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(SMALL_LILY_PAD.defaultBlockState().setValue(SmallLilyPadBlock.LILY_PADS, 1), 1)
                .add(SMALL_LILY_PAD.defaultBlockState().setValue(SmallLilyPadBlock.LILY_PADS, 2), 1)
                .add(SMALL_LILY_PAD.defaultBlockState().setValue(SmallLilyPadBlock.LILY_PADS, 3), 1)
                .add(SMALL_LILY_PAD.defaultBlockState().setValue(SmallLilyPadBlock.LILY_PADS, 4), 1)
                .build()
        )
    );

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> SOUR_BERRIES = registerRandomPatchFeature(
        "sour_berries", 4, SimpleStateProvider.simple(SOUR_BERRY_BUSH.defaultBlockState().setValue(SourBerryBushBlock.AGE, 3))
    );

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, SedgeFeature>> SEDGE = registerFeature("sedge", ModFeatures.SEDGE);

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, HangingMossFeature>> HANGING_MOSS = registerFeature("hanging_moss", ModFeatures.HANGING_MOSS);

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, CaribouMossFeature>> CARIBOU_MOSS = registerFeature("caribou_moss", ModFeatures.CARIBOU_MOSS);



    /*
     * Trees
     */
    public static Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> APPLE_GROWN = registerTreeFeature("apple_grown", AppleTree.getTreeConfig(true));
    public static Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> APPLE_NATURAL = registerTreeFeature("apple_natural", AppleTree.getTreeConfig(false));

    public static Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> HAZEL_GROWN = registerTreeFeature("hazel_grown", HazelTree.getTreeConfig(true));
    public static Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> HAZEL_NATURAL = registerTreeFeature("hazel_natural", HazelTree.getTreeConfig(false));

    public static Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> MUSKEG_TREES = registerWeightedRandomFeature(
        "muskeg_trees", TreePlacements.PINE_CHECKED,
        new WeightedPlacedFeature(TreePlacements.MEGA_PINE_CHECKED, 0.05f),
        new WeightedPlacedFeature(TreePlacements.BIRCH_CHECKED, 0.1f)
    );



    /*
     * Ores and stuff
     */
    public static Holder<ConfiguredFeature<DiskConfiguration, Feature<DiskConfiguration>>> PEAT = registerDiskFeature(
        "peat", new DiskConfiguration(
            ModBlocks.PEAT.defaultBlockState(),
            UniformInt.of(2, 3), 1,
            ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState())
        )
    );

    public static Holder<ConfiguredFeature<DiskConfiguration, Feature<DiskConfiguration>>> MOSSY_GRAVEL = registerDiskFeature(
        "mossy_gravel", new DiskConfiguration(
            ModBlocks.PEAT.defaultBlockState(),
            UniformInt.of(2, 3), 1,
            ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState())
        )
    );

    /*
     * Misc
     */
    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, FallenLogFeature>> FALLEN_LOG = registerFeature("fallen_log", ModFeatures.FALLEN_LOG);
}
