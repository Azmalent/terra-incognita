package azmalent.terraincognita.util;

import azmalent.cuneiform.lib.util.BiomeUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.fml.RegistryObject;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class WorldGenUtil {
    public static Biome.Category getProperBiomeCategory(Biome biome) {
        Biome.Category category = biome.getCategory();
        RegistryKey<Biome> key = BiomeUtil.getBiomeKey(biome);

        if (category == Biome.Category.PLAINS) {
            if (BiomeDictionary.hasType(key, COLD)) return Biome.Category.ICY;
        }
        else if (category == Biome.Category.FOREST) {
            if (BiomeUtil.hasAnyType(key, JUNGLE, HOT)) return Biome.Category.JUNGLE;
            else if (BiomeDictionary.hasType(key, COLD)) return Biome.Category.TAIGA;
        }

        return category;
    }

    public static <T extends Entity> void addSpawner(MobSpawnInfoBuilder spawns, RegistryObject<EntityType<T>> entityType, EntityClassification classification, int weight, int minCount, int maxCount) {
        if (weight > 0) {
            spawns.withSpawner(classification, new MobSpawnInfo.Spawners(entityType.get(), weight, minCount, maxCount));
        }
    }

    public static void addVegetation(BiomeGenerationSettingsBuilder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
        }
    }

    public static void addOre(BiomeGenerationSettingsBuilder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
        }
    }

    public static void addModification(BiomeGenerationSettingsBuilder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, feature);
        }
    }
}
