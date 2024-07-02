package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.core.registry.ModPlacements;
import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import azmalent.terraincognita.common.world.configured.ModVegetationFeatures;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static azmalent.terraincognita.core.registry.ModWoodTypes.*;

public class ModVegetationPlacements {
    public static void init() {
        //Called to force static constructor
    }

    //Common biome flowers
    public static final RegistryObject<PlacedFeature> ALPINE_FLOWERS = ModPlacements.register(
        "alpine_flowers", ModVegetationFeatures.ALPINE_FLOWERS, () -> List.of(
            RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> EDELWEISS = ModPlacements.register(
        "edelweiss", ModVegetationFeatures.EDELWEISS, () -> List.of(
            RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
            HeightBiomeFilter.above(96)
        )
    );

    public static final RegistryObject<PlacedFeature> SAXIFRAGE_PATCH = ModPlacements.register(
        "saxifrage_patch", ModVegetationFeatures.SAXIFRAGE_PATCH, () -> List.of(
            RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> ARCTIC_FLOWERS = ModPlacements.register(
        "arctic_flowers", ModVegetationFeatures.ARCTIC_FLOWERS, () -> List.of(
            RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> FOREST_FLOWERS = ModPlacements.register(
        "forest_flowers", ModVegetationFeatures.FOREST_FLOWERS, () -> List.of(
            RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> SAVANNA_FLOWERS = ModPlacements.register(
        "savanna_flowers", ModVegetationFeatures.SAVANNA_FLOWERS, () -> List.of(
            RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> SWAMP_FLOWERS = ModPlacements.register(
        "swamp_flowers", ModVegetationFeatures.SWAMP_FLOWERS, () -> List.of(
            RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );


    //Misc vegetation
    public static final RegistryObject<PlacedFeature> CACTUS_FLOWERS = ModPlacements.register(
        "cactus_flowers", ModVegetationFeatures.CACTUS_FLOWERS, () -> List.of(BiomeFilter.biome())
    );

    public static final RegistryObject<PlacedFeature> CARIBOU_MOSS = ModPlacements.register(
        "caribou_moss", ModVegetationFeatures.CARIBOU_MOSS, () -> List.of(
            RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> HANGING_MOSS_PATCH = ModPlacements.register(
        "hanging_moss_patch", ModVegetationFeatures.HANGING_MOSS_PATCH, () -> List.of(
            CountPlacement.of(64), InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> LOTUS = ModPlacements.register(
        "lotus", ModVegetationFeatures.LOTUS, () -> VegetationPlacements.worldSurfaceSquaredWithCount(4)
    );

    public static final RegistryObject<PlacedFeature> SWAMP_REEDS = ModPlacements.register(
        "swamp_reeds", ModVegetationFeatures.SWAMP_REEDS, () -> List.of(
            RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> SWEET_PEAS = ModPlacements.register(
        "sweet_peas", ModVegetationFeatures.SWEET_PEAS, () -> List.of(
            CountPlacement.of(8), InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(100)),
            BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> WITHER_ROSES = ModPlacements.register(
        "wither_roses", ModVegetationFeatures.WITHER_ROSES, () -> List.of(
            RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome(),
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockTags.SOUL_FIRE_BASE_BLOCKS))
        )
    );

    //Biome specific
    public static final RegistryObject<PlacedFeature> PLAINS_APPLE_TREES = ModPlacements.register(
        "plains_apple_trees", ModTreeFeatures.APPLE_NATURAL,
            () -> VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.02f, 1), APPLE.SAPLING.get())
    );

    public static final RegistryObject<PlacedFeature> FOREST_HAZEL_TREES = ModPlacements.register(
        "forest_hazel_trees", ModTreeFeatures.HAZEL_NATURAL,
            () -> VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.04f, 1), HAZEL.SAPLING.get())
    );

    public static final RegistryObject<PlacedFeature> BOREAL_FOREST_TREES = ModPlacements.register(
        "boreal_forest_trees", ModVegetationFeatures.BOREAL_FOREST_TREES,
            () -> VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1), LARCH.SAPLING.get())
    );

    public static final RegistryObject<PlacedFeature> SPARSE_LARCH_TREES = ModPlacements.register(
        "sparse_larch_trees", ModTreeFeatures.LARCH,
            () -> VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.5f, 1), LARCH.SAPLING.get())
    );

    public static final RegistryObject<PlacedFeature> RARE_LARCH_TREES = ModPlacements.register(
        "rare_larch_trees", ModTreeFeatures.LARCH,
        () -> VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.02f, 1), LARCH.SAPLING.get())
    );

    public static final RegistryObject<PlacedFeature> CLEARING_VEGETATION = ModPlacements.register(
        "clearing_vegetation", ModVegetationFeatures.CLEARING_VEGETATION, () -> List.of(
            CountPlacement.of(2), RarityFilter.onAverageOnceEvery(2),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> GINKGO_GROVE_FLOWERS = ModPlacements.register(
        "ginkgo_grove_flowers", ModVegetationFeatures.GINKGO_GROVE_FLOWERS, () -> List.of(
            RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> GINKGO_GROVE_TREES = ModPlacements.register(
        "ginkgo_grove_trees", ModVegetationFeatures.GINKGO_GROVE_TREES,
            () -> VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))
    );

    public static final RegistryObject<PlacedFeature> SOUR_BERRIES = ModPlacements.register(
        "sour_berries", ModVegetationFeatures.SOUR_BERRIES, () -> VegetationPlacements.worldSurfaceSquaredWithCount(3)
    );
}
