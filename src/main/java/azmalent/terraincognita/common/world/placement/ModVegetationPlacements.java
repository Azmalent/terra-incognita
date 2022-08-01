package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.world.configured.ModConfiguredVegetationFeatures;
import azmalent.terraincognita.util.WeightedStateProviderBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import static azmalent.terraincognita.util.ConfiguredFeatureHelper.randomPatchConfig;

public class ModVegetationPlacements {
    //Common biome flowers
    public static final Holder<PlacedFeature> ALPINE_FLOWERS = PlacementUtils.register(
        "alpine_flowers", ModConfiguredVegetationFeatures.ALPINE_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> EDELWEISS = PlacementUtils.register(
        "edelweiss", ModConfiguredVegetationFeatures.EDELWEISS,
        RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
        HeightBiomeFilter.above(128)
    );

    public static final Holder<PlacedFeature> SAXIFRAGE_PATCH = PlacementUtils.register(
        "saxifrage_patch", ModConfiguredVegetationFeatures.SAXIFRAGE_PATCH,
        RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> ARCTIC_FLOWERS = PlacementUtils.register(
        "arctic_flowers", ModConfiguredVegetationFeatures.ARCTIC_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> FOREST_FLOWERS = PlacementUtils.register(
        "forest_flowers", ModConfiguredVegetationFeatures.FOREST_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SAVANNA_FLOWERS = PlacementUtils.register(
        "savanna_flowers", ModConfiguredVegetationFeatures.SAVANNA_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SWAMP_FLOWERS = PlacementUtils.register(
        "swamp_flowers", ModConfiguredVegetationFeatures.SWAMP_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );


    //Misc vegetation
    public static final Holder<PlacedFeature> CACTUS_FLOWERS = PlacementUtils.register(
        "cactus_flowers", ModConfiguredVegetationFeatures.CACTUS_FLOWERS, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> CARIBOU_MOSS = PlacementUtils.register(
        "caribou_moss", ModConfiguredVegetationFeatures.CARIBOU_MOSS,
        RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> HANGING_MOSS_PATCH = PlacementUtils.register(
        "hanging_moss_patch", ModConfiguredVegetationFeatures.HANGING_MOSS_PATCH,
        CountPlacement.of(12), InSquarePlacement.spread(),
        PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
        RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
        HeightBiomeFilter.between(30, 96)
    );


    public static final Holder<PlacedFeature> LOTUS = PlacementUtils.register(
        "lotus", ModConfiguredVegetationFeatures.LOTUS,
        VegetationPlacements.worldSurfaceSquaredWithCount(4)
    );

    public static final Holder<PlacedFeature> SEDGE = PlacementUtils.register(
        "sedge", ModConfiguredVegetationFeatures.SEDGE,
        RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SWEET_PEAS = PlacementUtils.register(
        "sweet_peas", ModConfiguredVegetationFeatures.SWEET_PEAS,
        CountPlacement.of(8), InSquarePlacement.spread(),
        HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(100)), BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> WITHER_ROSES = PlacementUtils.register(
        "wither_roses", ModConfiguredVegetationFeatures.WITHER_ROSES,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
        PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome(),
        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockTags.SOUL_FIRE_BASE_BLOCKS))
    );


    //Terra Incognita biome specific
    public static final Holder<PlacedFeature> SOUR_BERRIES = PlacementUtils.register(
        "sour_berries", ModConfiguredVegetationFeatures.SOUR_BERRIES,
        VegetationPlacements.worldSurfaceSquaredWithCount(3)
    );

    public static final Holder<PlacedFeature> TUNDRA_VEGETATION_PATCH = PlacementUtils.register(
        "tundra_vegetation_patch", ModConfiguredVegetationFeatures.TUNDRA_VEGETATION_PATCH,
        RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );
}
