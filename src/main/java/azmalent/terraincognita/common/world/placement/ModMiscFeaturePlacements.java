package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.world.configured.ModMiscFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModMiscFeaturePlacements {
    public static final Holder<PlacedFeature> PEAT_DISK = PlacementUtils.register(
        "peat_disk", ModMiscFeatures.PEAT_DISK, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> MOSSY_GRAVEL_DISK = PlacementUtils.register(
        "mossy_gravel_disk", ModMiscFeatures.MOSSY_GRAVEL_DISK, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> BOREAL_FOREST_ROCK = PlacementUtils.register(
        "boreal_forest_rock", MiscOverworldFeatures.FOREST_ROCK, PlacementUtils.countExtra(0, 0.25f, 2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> TUNDRA_ROCK = PlacementUtils.register(
        "tundra_rock", ModMiscFeatures.TUNDRA_ROCK, PlacementUtils.countExtra(0, 0.1f, 4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );
}
