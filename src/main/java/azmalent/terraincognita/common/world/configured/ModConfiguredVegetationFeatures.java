package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.ModBlockTags;
import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import azmalent.terraincognita.common.world.feature.*;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import static azmalent.terraincognita.util.ConfiguredFeatureHelper.*;
import static net.minecraft.data.worldgen.placement.PlacementUtils.inlinePlaced;

public class ModConfiguredVegetationFeatures {
    //Common biome flowers
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> ALPINE_FLOWERS = registerFlowerFeature(
        "alpine_flowers", 32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(ModBlocks.ALPINE_PINK.defaultBlockState(), 1)
                .add(ModBlocks.ASTER.defaultBlockState(), 1)
                .add(ModBlocks.GENTIAN.defaultBlockState(), 1)
                .build()
        )
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> EDELWEISS = registerFeature(
        "edelweiss", Feature.NO_BONEMEAL_FLOWER, randomPatchConfig(32, BlockStateProvider.simple(ModBlocks.EDELWEISS.defaultBlockState()))
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> SAXIFRAGE = registerFeature(
        "saxifrage", Feature.NO_BONEMEAL_FLOWER, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(ModBlocks.MAGENTA_SAXIFRAGE.defaultBlockState(), 5)
                .add(ModBlocks.YELLOW_SAXIFRAGE.defaultBlockState(), 5)
                .add(Blocks.MOSS_CARPET.defaultBlockState(), 5)
                .add(Blocks.GRASS.defaultBlockState(), 10)
                .build()
        ))
    );

    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, Feature<VegetationPatchConfiguration>>> SAXIFRAGE_PATCH = registerFeature(
        "saxifrage_patch", Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
            BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.MOSS_BLOCK),
            inlinePlaced(SAXIFRAGE), CaveSurface.FLOOR, ConstantInt.of(1),
            0.0F, 5, 0.08F, UniformInt.of(4, 9), 0.3F
        )
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> ARCTIC_FLOWERS  = registerRandomFeature(
        "arctic_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(ModBlocks.HEATHER.defaultBlockState(), 1)
                .add(ModBlocks.WHITE_DRYAD.defaultBlockState(), 1)
                .build()
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(ModBlocks.FIREWEED.defaultBlockState())
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(ModBlocks.WHITE_RHODODENDRON.defaultBlockState())
        )))
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> FOREST_FLOWERS = registerFlowerFeature(
        "forest_flowers", 32,
        new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(ModBlocks.WILD_GARLIC.defaultBlockState(), 2)
                .add(ModBlocks.FOXGLOVE.defaultBlockState(), 2)
                .add(ModBlocks.PINK_PRIMROSE.defaultBlockState(), 1)
                .add(ModBlocks.PURPLE_PRIMROSE.defaultBlockState(), 1)
                .add(ModBlocks.YELLOW_PRIMROSE.defaultBlockState(), 1)
                .build()
        )
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> SAVANNA_FLOWERS = registerRandomFeature(
        "savanna_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(ModBlocks.MARIGOLD.defaultBlockState(), 4)
                .add(ModBlocks.SNAPDRAGON.defaultBlockState(), 4)
                .add(ModBlocks.BLACK_IRIS.defaultBlockState(), 1)
                .add(ModBlocks.BLUE_IRIS.defaultBlockState(), 1)
                .add(ModBlocks.PURPLE_IRIS.defaultBlockState(), 1)
                .build()
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(ModBlocks.SAGE.defaultBlockState())
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(ModBlocks.OLEANDER.defaultBlockState())
        )))
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> SWAMP_FLOWERS = registerRandomFeature(
        "swamp_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(ModBlocks.FORGET_ME_NOT.defaultBlockState(), 1)
                .add(ModBlocks.GLOBEFLOWER.defaultBlockState(), 1)
                .build()
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(ModBlocks.WATER_FLAG.defaultBlockState())
        )))
    );


    //Misc vegetation
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, CactusFlowerFeature>> CACTUS_FLOWERS = registerFeature("cactus_flowers", ModFeatures.CACTUS_FLOWERS);
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, CaribouMossFeature>> CARIBOU_MOSS = registerFeature("caribou_moss", ModFeatures.CARIBOU_MOSS);

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, HangingMossFeature>> HANGING_MOSS = registerFeature("hanging_moss", ModFeatures.HANGING_MOSS);

    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, Feature<VegetationPatchConfiguration>>> HANGING_MOSS_PATCH = registerFeature(
        "hanging_moss_patch", Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
                BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(Blocks.MOSS_BLOCK),
                inlinePlaced(HANGING_MOSS), CaveSurface.CEILING,
                UniformInt.of(1, 2), 0.0F, 5, 0.08F, UniformInt.of(4, 7), 0.3F
        )
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> LOTUS = registerRandomPatchFeature(
        "lotus", 10,
        new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                .add(Blocks.LILY_PAD.defaultBlockState(), 3)
                .add(ModBlocks.PINK_LOTUS.defaultBlockState(), 1)
                .add(ModBlocks.WHITE_LOTUS.defaultBlockState(), 1)
                .add(ModBlocks.YELLOW_LOTUS.defaultBlockState(), 1)
                .build()
        )
    );

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, SedgeFeature>> SEDGE = registerFeature("sedge", ModFeatures.SEDGE);
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, SweetPeasFeature>> SWEET_PEAS = registerFeature("sweet_peas", ModFeatures.SWEET_PEAS);

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> WITHER_ROSES = registerFlowerFeature(
        "wither_roses", 128, BlockStateProvider.simple(Blocks.WITHER_ROSE.defaultBlockState())
    );

    //Terra Incognita biome specific
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> SOUR_BERRIES = registerRandomPatchFeature(
        "sour_berries", 4, SimpleStateProvider.simple(ModBlocks.SOUR_BERRY_BUSH.defaultBlockState().setValue(SourBerryBushBlock.AGE, 3))
    );
}
