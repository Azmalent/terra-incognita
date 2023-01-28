package azmalent.terraincognita.common.world.region;

import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.world.biome.TIBiomeEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.jetbrains.annotations.NotNull;
import terrablender.api.ParameterUtils;

import java.util.function.Consumer;

@SuppressWarnings("ConstantConditions")
public class TIOverworldBiomeBuilder extends OverworldBiomeBuilder {
    public void addBiomes(@NotNull Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        super.addBiomes(mapper);
    }

    private static final TIBiomeEntry[][] MIDDLE_BIOMES = {
        { null, ModBiomes.TUNDRA, null, ModBiomes.SNOWY_BOREAL_FOREST, ModBiomes.BOREAL_FOREST },
        { null, null, null, ModBiomes.BOREAL_FOREST, ModBiomes.BOREAL_FOREST },
        { null, null, ModBiomes.LUSH_PLAINS, ModBiomes.LUSH_PLAINS, null },
        { null, null, null, null, null },
        { null, null, null, null, null }
    };

    private static final TIBiomeEntry[][] MIDDLE_BIOMES_VARIANT = {
        { null, null, null, ModBiomes.SNOWY_BOREAL_FOREST_CLEARING, null },
        { null, null, null, ModBiomes.BOREAL_FOREST_CLEARING, null },
        { null, null, null, null, null },
        { null, null, null, null, null },
        { null, null, null, null, null }
    };

    @Override
    public @NotNull ResourceKey<Biome> pickMiddleBiome(int temp, int humidity, @NotNull Climate.Parameter weirdness) {
        var biomeEntry = MIDDLE_BIOMES[temp][humidity];
        //TODO: allow different weirdness requirements per biome
        if (weirdness.max() >= 0.7666667F) {
            var variantBiome = MIDDLE_BIOMES_VARIANT[temp][humidity];
            if (variantBiome != null) {
                biomeEntry = variantBiome;
            }
        }

        if (biomeEntry != null && biomeEntry.isEnabled()) {
            return biomeEntry.biome.getKey();
        }

        return super.pickMiddleBiome(temp, humidity, weirdness);
    }
}
