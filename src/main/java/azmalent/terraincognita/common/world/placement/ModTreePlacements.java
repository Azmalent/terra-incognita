package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.world.configured.ModConfiguredTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static azmalent.terraincognita.common.registry.ModWoodTypes.*;

public class ModTreePlacements {
    public static final Holder<PlacedFeature> APPLE_CHECKED = PlacementUtils.register(
        "apple_checked", ModConfiguredTreeFeatures.APPLE_NATURAL, PlacementUtils.filteredByBlockSurvival(APPLE.SAPLING.getBlock())
    );

    public static final Holder<PlacedFeature> HAZEL_CHECKED = PlacementUtils.register(
        "hazel_checked", ModConfiguredTreeFeatures.HAZEL_NATURAL, PlacementUtils.filteredByBlockSurvival(HAZEL.SAPLING.getBlock())
    );

    public static final Holder<PlacedFeature> LARCH_CHECKED = PlacementUtils.register(
        "larch_checked", ModConfiguredTreeFeatures.LARCH, PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.getBlock())
    );

    public static final Holder<PlacedFeature> GINKGO_CHECKED = PlacementUtils.register(
        "ginkgo_checked", ModConfiguredTreeFeatures.GINKGO, PlacementUtils.filteredByBlockSurvival(GINKGO.SAPLING.getBlock())
    );
}
