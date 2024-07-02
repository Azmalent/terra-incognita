package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.core.registry.ModPlacements;
import azmalent.terraincognita.common.world.configured.ModMiscFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModMiscFeaturePlacements {
    public static void init() {
        //Called to force static constructor
    }

    public static final RegistryObject<PlacedFeature> PEAT_DISK = ModPlacements.register(
        "peat_disk", ModMiscFeatures.PEAT_DISK, () -> List.of(
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> MOSSY_GRAVEL_DISK = ModPlacements.register(
        "mossy_gravel_disk", ModMiscFeatures.MOSSY_GRAVEL_DISK, () -> List.of(
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
        )
    );

    public static final RegistryObject<PlacedFeature> BOREAL_FOREST_ROCK = ModPlacements.register(
        "boreal_forest_rock", Holder.hackyErase(MiscOverworldFeatures.FOREST_ROCK), () -> List.of(
            PlacementUtils.countExtra(0, 0.25f, 2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        )
    );
}
