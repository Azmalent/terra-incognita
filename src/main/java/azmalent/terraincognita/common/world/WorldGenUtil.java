package azmalent.terraincognita.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;

public class WorldGenUtil {
    public static <T extends Entity> void addSpawner(BiomeLoadingEvent event, RegistryObject<EntityType<T>> entityType, EntityClassification classification, int weight, int minCount, int maxCount) {
        if (entityType != null) {
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
