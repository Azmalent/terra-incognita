package azmalent.terraincognita.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class WorldGenUtil {
    public static ResourceKey<Biome> getBiomeKey(ResourceLocation location) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, location);
    }

    public static ResourceKey<Biome> getBiomeKey(Biome biome) {
        return getBiomeKey(biome.getRegistryName());
    }

    public static boolean hasAnyBiomeType(ResourceKey<Biome> biome, BiomeDictionary.Type... types) {
        for (var type : types) {
            if (BiomeDictionary.hasType(biome, type)) return true;
        }

        return false;
    }

    public static Biome.BiomeCategory getProperBiomeCategory(Biome biome) {
        Biome.BiomeCategory category = biome.getBiomeCategory();
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

    public static <T extends Entity> void addSpawner(MobSpawnSettingsBuilder spawns, RegistryObject<EntityType<T>> entityType, MobCategory classification, int weight, int minCount, int maxCount) {
        if (weight > 0) {
            spawns.addSpawn(classification, new MobSpawnSettings.SpawnerData(entityType.get(), weight, minCount, maxCount));
        }
    }

    public static void addVegetation(BiomeGenerationSettingsBuilder builder, PlacedFeature... features) {
        for (PlacedFeature feature : features) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, feature);
        }
    }

    public static void addOre(BiomeGenerationSettingsBuilder builder, PlacedFeature... features) {
        for (PlacedFeature feature : features) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature);
        }
    }

    public static void addModification(BiomeGenerationSettingsBuilder builder, PlacedFeature... features) {
        for (PlacedFeature feature : features) {
            builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, feature);
        }
    }
}
