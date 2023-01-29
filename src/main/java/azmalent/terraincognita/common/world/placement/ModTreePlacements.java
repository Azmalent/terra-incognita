package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.*;

import static azmalent.terraincognita.common.registry.ModWoodTypes.*;

public class ModTreePlacements {
    //Single trees
    public static final Holder<PlacedFeature> LARCH_CHECKED = PlacementUtils.register(
        "larch_checked", ModTreeFeatures.LARCH, PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.get())
    );

    public static final Holder<PlacedFeature> LARCH_TALL_CHECKED = PlacementUtils.register(
        "larch_tall_checked", ModTreeFeatures.LARCH_TALL, PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.get())
    );

    public static final Holder<PlacedFeature> MEGA_LARCH_CHECKED = PlacementUtils.register(
        "mega_larch_checked", ModTreeFeatures.MEGA_LARCH, PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.get())
    );

    public static final Holder<PlacedFeature> GINKGO_CHECKED = PlacementUtils.register(
        "ginkgo_checked", ModTreeFeatures.GINKGO, PlacementUtils.filteredByBlockSurvival(GINKGO.SAPLING.get())
    );

    public static final Holder<PlacedFeature> TUNDRA_SHRUB_CHECKED = PlacementUtils.register(
        "tundra_shrub_checked", ModTreeFeatures.TUNDRA_SHRUB, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );
}
