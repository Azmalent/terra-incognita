package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModFeatures;
import azmalent.terraincognita.common.world.placement.ModTreePlacements;
import azmalent.terraincognita.util.WeightedStateProviderBuilder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static azmalent.terraincognita.core.registry.ModConfiguredFeatures.register;
import static azmalent.terraincognita.util.ConfiguredFeatureHelper.*;
import static net.minecraft.data.worldgen.placement.PlacementUtils.inlinePlaced;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ModVegetationFeatures {
    public static void init() {
        //Called to force static constructor
    }

    //Common biome flowers
    public static final RegistryObject<ConfiguredFeature<?, ?>> ALPINE_FLOWERS = registerFlowerFeature(
        "alpine_flowers", 32, () -> new WeightedStateProviderBuilder()
            .add(ModBlocks.ALPINE_PINK, 1)
            .add(ModBlocks.ASTER, 1)
            .add(ModBlocks.GENTIAN, 1)
            .build()
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> EDELWEISS = registerNoBonemealFlowerFeature(
        "edelweiss",32, () -> BlockStateProvider.simple(ModBlocks.EDELWEISS.defaultBlockState())
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> SAXIFRAGE_PATCH = registerNoBonemealFlowerFeature(
        "saxifrage_patch", 32, () -> new WeightedStateProviderBuilder()
            .add(ModBlocks.MAGENTA_SAXIFRAGE.defaultBlockState(), 1)
            .add(ModBlocks.YELLOW_SAXIFRAGE.defaultBlockState(), 1)
            .add(Blocks.GRASS.defaultBlockState(), 2)
            .build()
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> ARCTIC_FLOWERS = registerRandomFeature(
        "arctic_flowers", () -> List.of(
            inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProviderBuilder()
                .add(ModBlocks.HEATHER, 3).add(ModBlocks.WHITE_DRYAD, 2).build()
            )),

            inlinePlaced(Feature.FLOWER, randomPatchConfig(32, SimpleStateProvider.simple(ModBlocks.HEATHER.defaultBlockState()))),

            inlinePlaced(Feature.RANDOM_PATCH, randomPatchConfig(32,
                BlockStateProvider.simple(ModBlocks.FIREWEED.defaultBlockState())
            )),

            inlinePlaced(Feature.RANDOM_PATCH, randomPatchConfig(16,
                BlockStateProvider.simple(ModBlocks.WHITE_RHODODENDRON.defaultBlockState())
            ))
        )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> FOREST_FLOWERS = registerFlowerFeature(
        "forest_flowers", 32, () -> new WeightedStateProviderBuilder()
            .add(ModBlocks.WILD_GARLIC, 2)
            .add(ModBlocks.FOXGLOVE, 2)
            .add(ModBlocks.PINK_PRIMROSE, 1)
            .add(ModBlocks.PURPLE_PRIMROSE, 1)
            .add(ModBlocks.YELLOW_PRIMROSE, 1)
            .build()
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> SAVANNA_FLOWERS = registerRandomFeature(
        "savanna_flowers", () -> List.of(
            inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProviderBuilder()
                    .add(ModBlocks.MARIGOLD, 4)
                    .add(ModBlocks.SNAPDRAGON, 4)
                    .add(ModBlocks.BLACK_IRIS, 1)
                    .add(ModBlocks.BLUE_IRIS, 1)
                    .add(ModBlocks.PURPLE_IRIS, 1)
                    .build()
            )),

            inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(ModBlocks.SAGE.defaultBlockState())
            ))),

            inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(ModBlocks.OLEANDER.defaultBlockState())
            )))
        )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> SWAMP_FLOWERS = registerRandomFeature(
        "swamp_flowers", () -> List.of(
            inlinePlaced(Feature.FLOWER, randomPatchConfig(32, new WeightedStateProviderBuilder()
                .add(ModBlocks.FORGET_ME_NOT, 1)
                .add(ModBlocks.GLOBEFLOWER, 1)
                .build()
            )),

            inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(ModBlocks.WATER_FLAG.defaultBlockState())
            )))
        )
    );


    //Misc vegetation
    public static final RegistryObject<ConfiguredFeature<?, ?>> CACTUS_FLOWERS =
        register("cactus_flowers", ModFeatures.CACTUS_FLOWERS);

    public static final RegistryObject<ConfiguredFeature<?, ?>> CARIBOU_MOSS =
        register("caribou_moss", ModFeatures.CARIBOU_MOSS);

    public static final RegistryObject<ConfiguredFeature<?, ?>> HANGING_MOSS =
        register("hanging_moss", ModFeatures.HANGING_MOSS);

    public static final RegistryObject<ConfiguredFeature<?, ?>> HANGING_MOSS_PATCH = register(
        "hanging_moss_patch", Feature.VEGETATION_PATCH, () -> new VegetationPatchConfiguration(
                BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(Blocks.MOSS_BLOCK),
                inlinePlaced(HANGING_MOSS.getHolder().get()), CaveSurface.CEILING,
                UniformInt.of(1, 2), 0.0F, 5, 0.08F, UniformInt.of(4, 7), 0.3F
        )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> LOTUS = registerRandomPatchFeature(
        "lotus", 10, () -> new WeightedStateProviderBuilder()
            .add(Blocks.LILY_PAD, 3)
            .add(ModBlocks.PINK_LOTUS, 1)
            .add(ModBlocks.WHITE_LOTUS, 1)
            .add(ModBlocks.YELLOW_LOTUS, 1)
            .build()
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> SWAMP_REEDS =
        register("swamp_reeds", ModFeatures.SWAMP_REEDS);

    public static final RegistryObject<ConfiguredFeature<?, ?>> SWEET_PEAS =
        register("sweet_peas", ModFeatures.SWEET_PEAS);

    public static final RegistryObject<ConfiguredFeature<?, ?>> WITHER_ROSES = registerFlowerFeature(
        "wither_roses", 128, () -> BlockStateProvider.simple(Blocks.WITHER_ROSE.defaultBlockState())
    );

    //Biome specific
    public static final RegistryObject<ConfiguredFeature<?, ?>> SOUR_BERRIES = registerRandomPatchFeature(
        "sour_berries", 4, () -> SimpleStateProvider.simple(
            ModBlocks.SOUR_BERRY_BUSH.defaultBlockState().setValue(SourBerryBushBlock.AGE, 3)
        )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> BOREAL_FOREST_TREES = registerWeightedRandomFeature(
        "boreal_forest_trees", () -> ModTreePlacements.LARCH_CHECKED, () -> List.of(
            new WeightedPlacedFeature(ModTreePlacements.LARCH_TALL_CHECKED.getHolder().get(), 0.6f),
            new WeightedPlacedFeature(ModTreePlacements.MEGA_LARCH_CHECKED.getHolder().get(), 0.2f)
        )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> CLEARING_VEGETATION = register(
        "clearing_vegetation", Feature.RANDOM_PATCH, () -> new RandomPatchConfiguration(
            96, 6, 2,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new DualNoiseProvider(
                new InclusiveRange<>(1, 3),
                new NormalNoise.NoiseParameters(-10, 1.0D), 1.0F, 2345L,
                new NormalNoise.NoiseParameters(-3, 1.0D), 1.0F,
                List.of(ModBlocks.HEATHER.defaultBlockState(), Blocks.GRASS.defaultBlockState(), ModBlocks.FIREWEED.defaultBlockState(), Blocks.FERN.defaultBlockState())))))
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> GINKGO_GROVE_FLOWERS = registerFlowerFeature(
        "ginkgo_grove_flowers", 32, () -> new WeightedStateProviderBuilder()
            .add(Blocks.ROSE_BUSH, 3)
            .add(Blocks.LILAC, 2)
            .add(Blocks.PEONY, 2)
            .build()
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> GINKGO_GROVE_TREES = registerWeightedRandomFeature(
        "ginkgo_grove_trees", () -> ModTreePlacements.GINKGO_CHECKED, () -> List.of(
            new WeightedPlacedFeature(TreePlacements.OAK_BEES_002, 0.1F),
            new WeightedPlacedFeature(TreePlacements.JUNGLE_BUSH, 0.33F)
        )
    );
}
