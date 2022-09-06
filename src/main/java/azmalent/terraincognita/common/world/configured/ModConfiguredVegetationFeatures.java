package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import azmalent.terraincognita.common.world.feature.*;
import azmalent.terraincognita.util.WeightedStateProviderBuilder;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import static azmalent.terraincognita.util.ConfiguredFeatureHelper.*;
import static net.minecraft.data.worldgen.placement.PlacementUtils.inlinePlaced;
import static net.minecraft.world.level.levelgen.feature.Feature.SIMPLE_BLOCK;

public class ModConfiguredVegetationFeatures {
    //Common biome flowers
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> ALPINE_FLOWERS = registerFlowerFeature(
        "alpine_flowers", 32, new WeightedStateProviderBuilder().add(ModBlocks.ALPINE_PINK, 1).add(ModBlocks.ASTER, 1).add(ModBlocks.GENTIAN, 1).build()
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> EDELWEISS = registerFeature(
        "edelweiss", Feature.NO_BONEMEAL_FLOWER, randomPatchConfig(32, BlockStateProvider.simple(ModBlocks.EDELWEISS.defaultBlockState()))
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> SAXIFRAGE = registerFeature(
        "saxifrage", Feature.NO_BONEMEAL_FLOWER, randomPatchConfig(32, new WeightedStateProviderBuilder()
                .add(ModBlocks.MAGENTA_SAXIFRAGE.defaultBlockState(), 5)
                .add(ModBlocks.YELLOW_SAXIFRAGE.defaultBlockState(), 5)
                .add(Blocks.MOSS_CARPET.defaultBlockState(), 5)
                .add(Blocks.GRASS.defaultBlockState(), 10)
                .build()
        )
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
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProviderBuilder()
            .add(ModBlocks.HEATHER, 3).add(ModBlocks.WHITE_DRYAD, 2).build()
        )),

        inlinePlaced(Feature.RANDOM_PATCH, randomPatchConfig(16,
            BlockStateProvider.simple(ModBlocks.FIREWEED.defaultBlockState())
        )),

        inlinePlaced(Feature.RANDOM_PATCH, randomPatchConfig(16,
            BlockStateProvider.simple(ModBlocks.WHITE_RHODODENDRON.defaultBlockState())
        ))
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> FOREST_FLOWERS = registerFlowerFeature(
        "forest_flowers", 32,
        new WeightedStateProviderBuilder()
            .add(ModBlocks.WILD_GARLIC, 2)
            .add(ModBlocks.FOXGLOVE, 2)
            .add(ModBlocks.PINK_PRIMROSE, 1)
            .add(ModBlocks.PURPLE_PRIMROSE, 1)
            .add(ModBlocks.YELLOW_PRIMROSE, 1)
            .build()
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> SAVANNA_FLOWERS = registerRandomFeature(
        "savanna_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProviderBuilder()
                .add(ModBlocks.MARIGOLD, 4)
                .add(ModBlocks.SNAPDRAGON, 4)
                .add(ModBlocks.BLACK_IRIS, 1)
                .add(ModBlocks.BLUE_IRIS, 1)
                .add(ModBlocks.PURPLE_IRIS, 1)
                .build()
        )),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(ModBlocks.SAGE.defaultBlockState())
        ))),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(SIMPLE_BLOCK, new SimpleBlockConfiguration(
            BlockStateProvider.simple(ModBlocks.OLEANDER.defaultBlockState())
        )))
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> SWAMP_FLOWERS = registerRandomFeature(
        "swamp_flowers",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProviderBuilder()
            .add(ModBlocks.FORGET_ME_NOT, 1)
            .add(ModBlocks.GLOBEFLOWER, 1)
            .build()
        )),

        inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(SIMPLE_BLOCK, new SimpleBlockConfiguration(
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
        new WeightedStateProviderBuilder()
            .add(Blocks.LILY_PAD, 3)
            .add(ModBlocks.PINK_LOTUS, 1)
            .add(ModBlocks.WHITE_LOTUS, 1)
            .add(ModBlocks.YELLOW_LOTUS, 1)
            .build()
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

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> TUNDRA_VEGETATION = registerRandomFeature(
        "tundra_vegetation",
        inlinePlaced(Feature.FLOWER, randomPatchConfig(16, new WeightedStateProviderBuilder()
            .add(Blocks.DANDELION, 2)
            .add(Blocks.POPPY, 2)
            .add(ModBlocks.HEATHER, 3)
            .add(ModBlocks.WHITE_DRYAD, 3)
            .add(ModBlocks.CARIBOU_MOSS, 8)
            .add(Blocks.GRASS, 8)
            .build()
        )),

        inlinePlaced(ModConfiguredTreeFeatures.TUNDRA_SHRUB, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING))
    );

    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, Feature<VegetationPatchConfiguration>>> TUNDRA_VEGETATION_PATCH = registerFeature(
        "tundra_vegetation_patch", Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
            BlockTags.DIRT,
            new WeightedStateProviderBuilder().add(Blocks.GRASS_BLOCK, 3).add(Blocks.COARSE_DIRT, 1).build(),
            inlinePlaced(TUNDRA_VEGETATION), CaveSurface.FLOOR,
            ConstantInt.of(1), 0.0F, 3, 0.8F, UniformInt.of(6, 12), 0.0F
        )
    );
}
