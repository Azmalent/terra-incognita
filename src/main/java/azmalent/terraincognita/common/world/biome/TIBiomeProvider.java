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

        }

        if (TIConfig.Biomes.muskeg.get()) {

        }

/*        if (TIConfig.Biomes.tundra.get()) {
            addBiomeSimilar(mapper, Biomes.SNOWY_PLAINS, ModBiomes.TUNDRA.key);
            TerraIncognita.LOGGER.info("Registered tundra biome");
        }*/
    }
}
