package azmalent.terraincognita.util;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldGenUtil {
    public static void addVegetation(BiomeGenerationSettings.Builder builder, ConfiguredFeature<?, ?>... features) {
        for (ConfiguredFeature<?, ?> feature : features) {
            if (feature != null) {
                builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
            }
        }
    }

    public static void addVegetation(BiomeLoadingEvent event, ConfiguredFeature<?, ?>... features) {
        addVegetation(event.getGeneration(), features);
    }
}
