package azmalent.terraincognita.common.world.placement;

import azmalent.terraincognita.common.registry.ModPlacements;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

//fuck feature order cycles
public class VanillaVegetationPlacements {
    public static void init() {
        //Called to force static constructor
    }

    private static RegistryObject<PlacedFeature> copyFromVanilla(String id, Holder<PlacedFeature> original) {
        return ModPlacements.PLACED_FEATURES.register(id, () -> new PlacedFeature(original.value().feature(), original.value().placement()));
    }

    public static final RegistryObject<PlacedFeature> PLAIN_GRASS = copyFromVanilla(
        "vanilla_plain_grass", VegetationPlacements.PATCH_GRASS_PLAIN
    );

    public static final RegistryObject<PlacedFeature> PLAIN_TALL_GRASS = copyFromVanilla(
        "vanilla_plain_tall_grass", VegetationPlacements.PATCH_TALL_GRASS_2
    );

    public static final RegistryObject<PlacedFeature> TAIGA_GRASS = copyFromVanilla(
        "vanilla_taiga_grass", VegetationPlacements.PATCH_GRASS_TAIGA
    );

    public static final RegistryObject<PlacedFeature> JUNGLE_GRASS = copyFromVanilla(
        "vanilla_jungle_grass", VegetationPlacements.PATCH_GRASS_JUNGLE
    );

    public static final RegistryObject<PlacedFeature> FERNS = copyFromVanilla(
        "vanilla_ferns", VegetationPlacements.PATCH_LARGE_FERN
    );

    public static final RegistryObject<PlacedFeature> DEFAULT_FLOWERS = copyFromVanilla(
        "vanilla_default_flowers", VegetationPlacements.FLOWER_DEFAULT
    );

    public static RegistryObject<PlacedFeature> PLAIN_FLOWERS = copyFromVanilla(
        "vanilla_plain_flowers", VegetationPlacements.FLOWER_PLAINS
    );

    public static final RegistryObject<PlacedFeature> WARM_FLOWERS = copyFromVanilla(
        "vanilla_warm_flowers", VegetationPlacements.FLOWER_WARM
    );

    public static RegistryObject<PlacedFeature> PLAIN_TREES = copyFromVanilla(
        "vanilla_plain_trees", VegetationPlacements.TREES_PLAINS
    );

    public static RegistryObject<PlacedFeature> LIGHT_BAMBOO = copyFromVanilla(
        "vanilla_light_bamboo", VegetationPlacements.BAMBOO_LIGHT
    );

    public static RegistryObject<PlacedFeature> COMMON_BERRY_BUSHES = copyFromVanilla(
        "vanilla_common_berry_bushes", VegetationPlacements.PATCH_BERRY_COMMON
    );

    public static RegistryObject<PlacedFeature> RARE_BERRY_BUSHES = copyFromVanilla(
        "vanilla_rare_berry_bushes", VegetationPlacements.PATCH_BERRY_RARE
    );

    public static final RegistryObject<PlacedFeature> SPARSE_MELONS = copyFromVanilla(
        "vanilla_sparse_melons", VegetationPlacements.PATCH_MELON_SPARSE
    );

    public static RegistryObject<PlacedFeature> DEFAULT_BROWN_MUSHROOMS = copyFromVanilla(
        "vanilla_brown_mushrooms", VegetationPlacements.BROWN_MUSHROOM_NORMAL
    );

    public static RegistryObject<PlacedFeature> DEFAULT_RED_MUSHROOMS = copyFromVanilla(
        "vanilla_red_mushrooms", VegetationPlacements.RED_MUSHROOM_NORMAL
    );

    public static RegistryObject<PlacedFeature> SUGAR_CANE = copyFromVanilla(
        "vanilla_sugar_cane", VegetationPlacements.PATCH_SUGAR_CANE
    );

    public static RegistryObject<PlacedFeature> PUMPKINS = copyFromVanilla(
        "vanilla_pumpkins", VegetationPlacements.PATCH_PUMPKIN
    );
}
