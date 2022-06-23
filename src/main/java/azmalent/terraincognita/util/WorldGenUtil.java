package azmalent.terraincognita.util;

import azmalent.cuneiform.registry.EntityEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class WorldGenUtil {
    public static ResourceKey<Biome> getBiomeKey(ResourceLocation location) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, location);
    }

    public static ResourceKey<Biome> getBiomeKey(Holder<Biome> biome) {
        return getBiomeKey(biome.value().getRegistryName());
    }

    public static boolean hasAnyBiomeType(ResourceKey<Biome> biome, BiomeDictionary.Type... types) {
        for (var type : types) {
            if (BiomeDictionary.hasType(biome, type)) return true;
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    public static Biome.BiomeCategory getProperBiomeCategory(Holder<Biome> biome) {
        Biome.BiomeCategory category = Biome.getBiomeCategory(biome);
        ResourceKey<Biome> key = getBiomeKey(biome);

        if (category == Biome.BiomeCategory.PLAINS) {
            if (BiomeDictionary.hasType(key, COLD)) return Biome.BiomeCategory.ICY;
        }
        else if (category == Biome.BiomeCategory.FOREST) {
            if (hasAnyBiomeType(key, JUNGLE, HOT)) return Biome.BiomeCategory.JUNGLE;
            else if (BiomeDictionary.hasType(key, COLD)) return Biome.BiomeCategory.TAIGA;
        }

        return category;
    }

    public static <T extends Entity> void addSpawner(MobSpawnSettingsBuilder spawns, EntityEntry<T> entity, MobCategory classification, int weight, int minCount, int maxCount) {
        if (weight > 0) {
            spawns.addSpawn(classification, new MobSpawnSettings.SpawnerData(entity.get(), weight, minCount, maxCount));
        }
    }

    @SafeVarargs
    public static void addVegetation(BiomeGenerationSettingsBuilder builder, Holder<PlacedFeature>... features) {
        for (Holder<PlacedFeature> feature : features) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, feature);
        }
    }

    @SafeVarargs
    public static void addOre(BiomeGenerationSettingsBuilder builder, Holder<PlacedFeature>... features) {
        for (Holder<PlacedFeature> feature : features) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature);
        }
    }

    @SafeVarargs
    public static void addModification(BiomeGenerationSettingsBuilder builder, Holder<PlacedFeature>... features) {
        for (Holder<PlacedFeature> feature : features) {
            builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, feature);
        }
    }
}
