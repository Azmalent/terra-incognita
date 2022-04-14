package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.world.configured.ModConfiguredVegetationFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class ModVegetationPlacements {
    public static final Holder<PlacedFeature> FOREST_FLOWERS = PlacementUtils.register(
        "forest_flowers", ModConfiguredVegetationFeatures.FOREST_FLOWERS,
        RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SWAMP_FLOWERS = PlacementUtils.register(
        "swamp_flowers", ModConfiguredVegetationFeatures.SWAMP_FLOWERS,
        RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> ALPINE_FLOWERS = PlacementUtils.register(
        "alpine_flowers", ModConfiguredVegetationFeatures.ALPINE_FLOWERS,
        RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SAVANNA_FLOWERS = PlacementUtils.register(
        "savanna_flowers", ModConfiguredVegetationFeatures.SAVANNA_FLOWERS,
        RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> ARCTIC_FLOWERS = PlacementUtils.register(
        "arctic_flowers", ModConfiguredVegetationFeatures.ARCTIC_FLOWERS,
        RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> CACTUS_FLOWERS = PlacementUtils.register(
        "cactus_flowers", ModConfiguredVegetationFeatures.CACTUS_FLOWERS,
        RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SWEET_PEAS = PlacementUtils.register(
        "sweet_peas", ModConfiguredVegetationFeatures.SWEET_PEAS,
        CountPlacement.of(127), InSquarePlacement.spread(),
        HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(100)), BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> WITHER_ROSES = PlacementUtils.register(
        "wither_roses", ModConfiguredVegetationFeatures.WITHER_ROSES,
        RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> LOTUS = PlacementUtils.register(
        "lotus", ModConfiguredVegetationFeatures.LOTUS,
        VegetationPlacements.worldSurfaceSquaredWithCount(4)
    );

    public static final Holder<PlacedFeature> SMALL_LILY_PADS = PlacementUtils.register(
        "small_lilypad", ModConfiguredVegetationFeatures.SMALL_LILY_PADS,
        VegetationPlacements.worldSurfaceSquaredWithCount(4)
    );

    public static final Holder<PlacedFeature> SEDGE = PlacementUtils.register(
        "sedge", ModConfiguredVegetationFeatures.SEDGE,
        RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> CARIBOU_MOSS = PlacementUtils.register(
        "caribou_moss", ModConfiguredVegetationFeatures.CARIBOU_MOSS,
        RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> SOUR_BERRIES = PlacementUtils.register(
        "sour_berries", ModConfiguredVegetationFeatures.SOUR_BERRIES,
        VegetationPlacements.worldSurfaceSquaredWithCount(3)
    );

    public static final Holder<PlacedFeature> HANGING_MOSS = PlacementUtils.register(
        "hanging_moss", ModConfiguredVegetationFeatures.HANGING_MOSS,
        RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread()
    );
}
