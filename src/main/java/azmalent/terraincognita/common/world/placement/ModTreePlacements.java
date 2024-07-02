package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.core.registry.ModPlacements;
import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static azmalent.terraincognita.core.registry.ModWoodTypes.*;

public class ModTreePlacements {
    public static void init() {
        //Called to force static constructor
    }

    //Single trees
    public static final RegistryObject<PlacedFeature> LARCH_CHECKED = ModPlacements.register(
        "larch_checked", ModTreeFeatures.LARCH, () -> List.of(
            PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.get())
        )
    );

    public static final RegistryObject<PlacedFeature> LARCH_TALL_CHECKED = ModPlacements.register(
        "larch_tall_checked", ModTreeFeatures.LARCH_TALL, () -> List.of(
            PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.get())
        )
    );

    public static final RegistryObject<PlacedFeature> MEGA_LARCH_CHECKED = ModPlacements.register(
        "mega_larch_checked", ModTreeFeatures.MEGA_LARCH, () -> List.of(
            PlacementUtils.filteredByBlockSurvival(LARCH.SAPLING.get())
        )
    );

    public static final RegistryObject<PlacedFeature> GINKGO_CHECKED = ModPlacements.register(
        "ginkgo_checked", ModTreeFeatures.GINKGO, () -> List.of(
            PlacementUtils.filteredByBlockSurvival(GINKGO.SAPLING.get())
        )
    );
}
