package azmalent.terraincognita.util;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.ModVegetation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.event.world.BiomeLoadingEvent;
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

    public static <T extends Entity> void addSpawner(BiomeLoadingEvent event, RegistryObject<EntityType<T>> entityType, EntityClassification classification, int weight, int minCount, int maxCount, BooleanOption condition) {
        if (condition.get()) {
            event.getSpawns().withSpawner(classification, new MobSpawnInfo.Spawners(entityType.get(), weight, minCount, maxCount));
        }
    }

    public static void addVegetation(BiomeLoadingEvent event, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            if (feature != null) {
                event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
            }
        }
    }

    public static void addOre(BiomeLoadingEvent event, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            if (feature != null) {
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
            }
        }
    }
}
