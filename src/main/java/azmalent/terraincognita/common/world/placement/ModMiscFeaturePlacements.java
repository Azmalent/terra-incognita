package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.world.configured.ModConfiguredMiscFeatures;
import azmalent.terraincognita.common.world.configured.ModConfiguredTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModMiscFeaturePlacements {
    public static final Holder<PlacedFeature> PEAT_DISK = PlacementUtils.register(
        "peat_disk", ModConfiguredMiscFeatures.PEAT_DISK, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> MOSSY_GRAVEL_DISK = PlacementUtils.register(
        "mossy_gravel_disk", ModConfiguredMiscFeatures.MOSSY_GRAVEL_DISK, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> TUNDRA_BOULDER = PlacementUtils.register(
        "tundra_boulder", ModConfiguredMiscFeatures.TUNDRA_BOULDER, PlacementUtils.countExtra(0, 0.1f, 4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
    );
}
