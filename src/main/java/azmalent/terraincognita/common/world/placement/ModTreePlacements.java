package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.world.configured.ModConfiguredTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.*;

import static azmalent.terraincognita.common.registry.ModWoodTypes.*;

public class ModTreePlacements {
    //Single trees
    public static final Holder<PlacedFeature> LARCH_CHECKED = PlacementUtils.register(
        "larch_checked", ModConfiguredTreeFeatures.LARCH, PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.get())
    );

    public static final Holder<PlacedFeature> GINKGO_CHECKED = PlacementUtils.register(
        "ginkgo_checked", ModConfiguredTreeFeatures.GINKGO, PlacementUtils.filteredByBlockSurvival(GINKGO.SAPLING.get())
    );

    public static final Holder<PlacedFeature> TUNDRA_SHRUB_CHECKED = PlacementUtils.register(
        "tundra_shrub_checked", ModConfiguredTreeFeatures.TUNDRA_SHRUB, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );

    //Biome tree configs
    public static final Holder<PlacedFeature> PLAINS_APPLE_TREES = PlacementUtils.register(
        "plains_apple_trees", ModConfiguredTreeFeatures.APPLE_NATURAL,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.02f, 1), APPLE.SAPLING.get())
    );

    public static final Holder<PlacedFeature> FOREST_HAZEL_TREES = PlacementUtils.register(
        "forest_hazel_trees", ModConfiguredTreeFeatures.HAZEL_NATURAL,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.04f, 1), HAZEL.SAPLING.get())
    );
}
