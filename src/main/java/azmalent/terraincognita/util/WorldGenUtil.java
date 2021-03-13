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
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.fml.RegistryObject;

import static net.minecraftforge.common.BiomeDictionary.Type.COLD;
import static net.minecraftforge.common.BiomeDictionary.Type.JUNGLE;
import static net.minecraftforge.common.BiomeDictionary.hasType;

public class WorldGenUtil {
    public static Biome.Category getProperBiomeCategory(Biome biome) {
        Biome.Category category = biome.getCategory();
        RegistryKey<Biome> key = BiomeUtil.getBiomeKey(biome);

        if (category == Biome.Category.PLAINS) {
            if (hasType(key, COLD)) return Biome.Category.ICY;
        }
        else if (category == Biome.Category.FOREST) {
            if (hasType(key, JUNGLE)) return Biome.Category.JUNGLE;
            else if (hasType(key, COLD)) return Biome.Category.TAIGA;
        }

        return category;
    }

    public static <T extends Entity> void addSpawner(MobSpawnInfo.Builder spawns, RegistryObject<EntityType<T>> entityType, EntityClassification classification, int weight, int minCount, int maxCount) {
        if (weight > 0) {
            spawns.withSpawner(classification, new MobSpawnInfo.Spawners(entityType.get(), weight, minCount, maxCount));
        }
    }

    public static void addVegetation(BiomeGenerationSettings.Builder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            if (feature != null) {
                builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
            }
        }
    }

    public static void addOre(BiomeGenerationSettings.Builder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            if (feature != null) {
                builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
            }
        }
    }
}
