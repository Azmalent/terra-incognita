package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import azmalent.terraincognita.common.world.configured.ModVegetationFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

import static azmalent.terraincognita.common.registry.ModWoodTypes.*;
import static azmalent.terraincognita.common.registry.ModWoodTypes.LARCH;

public class ModVegetationPlacements {
    //Common biome flowers
    public static final Holder<PlacedFeature> ALPINE_FLOWERS = PlacementUtils.register(
        "alpine_flowers", ModVegetationFeatures.ALPINE_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> EDELWEISS = PlacementUtils.register(
        "edelweiss", ModVegetationFeatures.EDELWEISS,
        RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
        HeightBiomeFilter.above(128)
    );

    public static final Holder<PlacedFeature> SAXIFRAGE_PATCH = PlacementUtils.register(
        "saxifrage_patch", ModVegetationFeatures.SAXIFRAGE_PATCH,
        RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> ARCTIC_FLOWERS = PlacementUtils.register(
        "arctic_flowers", ModVegetationFeatures.ARCTIC_FLOWERS,
        RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> FOREST_FLOWERS = PlacementUtils.register(
        "forest_flowers", ModVegetationFeatures.FOREST_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SAVANNA_FLOWERS = PlacementUtils.register(
        "savanna_flowers", ModVegetationFeatures.SAVANNA_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SWAMP_FLOWERS = PlacementUtils.register(
        "swamp_flowers", ModVegetationFeatures.SWAMP_FLOWERS,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );


    //Misc vegetation
    public static final Holder<PlacedFeature> CACTUS_FLOWERS = PlacementUtils.register(
        "cactus_flowers", ModVegetationFeatures.CACTUS_FLOWERS, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> CARIBOU_MOSS = PlacementUtils.register(
        "caribou_moss", ModVegetationFeatures.CARIBOU_MOSS,
        RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> HANGING_MOSS_PATCH = PlacementUtils.register(
        "hanging_moss_patch", ModVegetationFeatures.HANGING_MOSS_PATCH,
        CountPlacement.of(12), InSquarePlacement.spread(),
        PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
        RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
        HeightBiomeFilter.between(30, 96)
    );


    public static final Holder<PlacedFeature> LOTUS = PlacementUtils.register(
        "lotus", ModVegetationFeatures.LOTUS,
        VegetationPlacements.worldSurfaceSquaredWithCount(4)
    );

    public static final Holder<PlacedFeature> SWAMP_REEDS = PlacementUtils.register(
        "swamp_reeds", ModVegetationFeatures.SWAMP_REEDS,
        RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SWEET_PEAS = PlacementUtils.register(
        "sweet_peas", ModVegetationFeatures.SWEET_PEAS,
        CountPlacement.of(8), InSquarePlacement.spread(),
        HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(100)), BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> WITHER_ROSES = PlacementUtils.register(
        "wither_roses", ModVegetationFeatures.WITHER_ROSES,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
        PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome(),
        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockTags.SOUL_FIRE_BASE_BLOCKS))
    );

    //Biome specific
    public static final Holder<PlacedFeature> PLAINS_APPLE_TREES = PlacementUtils.register(
        "plains_apple_trees", ModTreeFeatures.APPLE_NATURAL,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.02f, 1), APPLE.SAPLING.get())
    );

    public static final Holder<PlacedFeature> FOREST_HAZEL_TREES = PlacementUtils.register(
        "forest_hazel_trees", ModTreeFeatures.HAZEL_NATURAL,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.04f, 1), HAZEL.SAPLING.get())
    );

    public static final Holder<PlacedFeature> BOREAL_FOREST_TREES = PlacementUtils.register(
        "boreal_forest_trees", ModVegetationFeatures.BOREAL_FOREST_TREES,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1), LARCH.SAPLING.get())
    );

    public static final Holder<PlacedFeature> SPARSE_LARCH_TREES = PlacementUtils.register(
        "sparse_larch_trees", ModTreeFeatures.LARCH,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.5f, 1), LARCH.SAPLING.get())
    );

    public static final Holder<PlacedFeature> RARE_LARCH_TREES = PlacementUtils.register(
        "rare_larch_trees", ModTreeFeatures.LARCH,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.02f, 1), LARCH.SAPLING.get())
    );

    public static final Holder<PlacedFeature> CLEARING_VEGETATION = PlacementUtils.register(
        "clearing_vegetation", ModVegetationFeatures.CLEARING_VEGETATION,
        CountPlacement.of(2), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> TUNDRA_VEGETATION_NOISE = PlacementUtils.register(
        "tundra_vegetation_noise", ModVegetationFeatures.TUNDRA_VEGETATION_NOISE,
        CountPlacement.of(2), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> TUNDRA_VEGETATION_PATCH = PlacementUtils.register(
        "tundra_vegetation_patch", ModVegetationFeatures.TUNDRA_VEGETATION_PATCH,
        RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SOUR_BERRIES = PlacementUtils.register(
        "sour_berries", ModVegetationFeatures.SOUR_BERRIES,
        VegetationPlacements.worldSurfaceSquaredWithCount(3)
    );
}
