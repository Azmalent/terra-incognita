package azmalent.terraincognita.util;

import azmalent.cuneiform.lib.util.BiomeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.fml.RegistryObject;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class WorldGenUtil {
    public static Biome.BiomeCategory getProperBiomeCategory(Biome biome) {
        Biome.BiomeCategory category = biome.getBiomeCategory();
        ResourceKey<Biome> key = BiomeUtil.getBiomeKey(biome);

        if (category == Biome.BiomeCategory.PLAINS) {
            if (BiomeDictionary.hasType(key, COLD)) return Biome.BiomeCategory.ICY;
        }
        else if (category == Biome.BiomeCategory.FOREST) {
            if (BiomeUtil.hasAnyType(key, JUNGLE, HOT)) return Biome.BiomeCategory.JUNGLE;
            else if (BiomeDictionary.hasType(key, COLD)) return Biome.BiomeCategory.TAIGA;
        }

        return category;
    }

    public static <T extends Entity> void addSpawner(MobSpawnInfoBuilder spawns, RegistryObject<EntityType<T>> entityType, MobCategory classification, int weight, int minCount, int maxCount) {
        if (weight > 0) {
            spawns.addSpawn(classification, new MobSpawnSettings.SpawnerData(entityType.get(), weight, minCount, maxCount));
        }
    }

    public static void addVegetation(BiomeGenerationSettingsBuilder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, feature);
        }
    }

    public static void addOre(BiomeGenerationSettingsBuilder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature);
        }
    }

    public static void addModification(BiomeGenerationSettingsBuilder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, feature);
        }
    }
}
