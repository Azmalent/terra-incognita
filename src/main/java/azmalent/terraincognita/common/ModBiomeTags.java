package azmalent.terraincognita.common;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {
    public static void init() {
        //Called to force static constructor
    }

    //TODO: utilize this tag for spawns
    public static final TagKey<Biome> SPAWNS_BUTTERFLIES = createTag("spawns_butterflies");
    public static final TagKey<Biome> SPAWNS_PLAINS_BUTTERFLIES = createTag("spawns_plains_butterflies");
    public static final TagKey<Biome> SPAWNS_FOREST_BUTTERFLIES = createTag("spawns_forest_butterflies");
    public static final TagKey<Biome> SPAWNS_MOUNTAIN_BUTTERFLIES = createTag("spawns_mountain_butterflies");

    private static TagKey<Biome> createTag(String name) {
        return TagKey.create(Registry.BIOME_REGISTRY, TerraIncognita.prefix(name));
    }
}
