package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.ParameterUtils.*;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class TIBiomeProvider extends Region {
    public TIBiomeProvider(int weight) {
        super(TerraIncognita.prefix("biome_provider"), RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        if (TIConfig.Biomes.borealForest.get()) {

        }

        if (TIConfig.Biomes.ginkgoGrove.get()) {

        }

        if (TIConfig.Biomes.lushPlains.get()) {
            addBiomeSimilar(mapper, Biomes.SUNFLOWER_PLAINS, ModBiomes.LUSH_PLAINS.biome.getKey());
            TerraIncognita.LOGGER.info("Registered lush plains biome");
        }

        if (TIConfig.Biomes.muskeg.get()) {
            var points = new ParameterUtils.ParameterPointListBuilder()
                .temperature(Temperature.COOL)
                .humidity(Humidity.WET, Humidity.HUMID)
                .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_4, Erosion.EROSION_6))
                .depth(Depth.SURFACE)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING))
                .build();

            points.forEach(point -> addBiome(mapper, point, ModBiomes.MUSKEG.biome.getKey()));
            TerraIncognita.LOGGER.info("Registered muskeg biome");
        }

        if (TIConfig.Biomes.tundra.get()) {
/*            var points = new ParameterUtils.ParameterPointListBuilder()
                .temperature(Temperature.ICY, Temperature.COOL)
                .humidity(Humidity.span(Humidity.NEUTRAL, Humidity.HUMID))
                .continentalness(Continentalness.COAST, Continentalness.NEAR_INLAND)
                .erosion(Erosion.span(Erosion.EROSION_4, Erosion.EROSION_6))
                .depth(Depth.SURFACE)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING))
                .build();

            points.forEach(point -> addBiome(mapper, point, ModBiomes.TUNDRA.biome.getKey()));*/
            addBiomeSimilar(mapper, Biomes.SNOWY_PLAINS, ModBiomes.TUNDRA.biome.getKey());
            TerraIncognita.LOGGER.info("Registered tundra biome");
        }
    }
}
