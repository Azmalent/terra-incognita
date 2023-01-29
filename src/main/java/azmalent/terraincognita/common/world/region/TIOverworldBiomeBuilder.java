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
    /*
    Vanilla biome mapping for reference:

    private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{
        {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.TAIGA},
        {Biomes.PLAINS, Biomes.PLAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
        {Biomes.FLOWER_FOREST, Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST},
        {Biomes.SAVANNA, Biomes.SAVANNA, Biomes.FOREST, Biomes.JUNGLE, Biomes.JUNGLE},
        {Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT}
    };

    private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
        {Biomes.ICE_SPIKES, null, Biomes.SNOWY_TAIGA, null, null},
        {null, null, null, null, Biomes.OLD_GROWTH_PINE_TAIGA},
        {Biomes.SUNFLOWER_PLAINS, null, null, Biomes.OLD_GROWTH_BIRCH_FOREST, null},
        {null, null, Biomes.PLAINS, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE},
        {null, null, null, null, null}
    };

    private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{
        {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA},
        {Biomes.MEADOW, Biomes.MEADOW, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
        {Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.DARK_FOREST},
        {Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA_PLATEAU, Biomes.FOREST, Biomes.FOREST, Biomes.JUNGLE},
        {Biomes.BADLANDS, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
    };

    private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
        {Biomes.ICE_SPIKES, null, null, null, null},
        {null, null, Biomes.MEADOW, Biomes.MEADOW, Biomes.OLD_GROWTH_PINE_TAIGA},
        {null, null, Biomes.FOREST, Biomes.BIRCH_FOREST, null},
        {null, null, null, null, null},
        {Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, null, null, null}
    };
     */

    public void addBiomes(@NotNull Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        super.addBiomes(mapper);
    }

    private static final TIBiomeEntry[][] MIDDLE_BIOMES = {
        { null, ModBiomes.TUNDRA, ModBiomes.SNOWY_BOREAL_FOREST, ModBiomes.SNOWY_BOREAL_FOREST, ModBiomes.BOREAL_FOREST },
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

    //TODO: variant plateau biomes
    private static final TIBiomeEntry[][] PLATEAU_BIOMES = {
        {null, ModBiomes.TUNDRA, ModBiomes.SNOWY_BOREAL_FOREST, null, null},
        {null, null, ModBiomes.BOREAL_FOREST, null, null},
        {null, null, null, ModBiomes.GINKGO_GROVE, null},
        {null, null, null, ModBiomes.GINKGO_GROVE, null},
        {null, null, null, null, null},
    };

    private static final TIBiomeEntry[][] PLATEAU_BIOMES_VARIANT = {
        {null, null, ModBiomes.SNOWY_BOREAL_FOREST_CLEARING, null, null},
        {null, null, ModBiomes.BOREAL_FOREST_CLEARING, null, null},
        {null, null, ModBiomes.GINKGO_GROVE, null, null},
        {null, null, ModBiomes.GINKGO_GROVE, null, null},
        {null, null, null, null, null},
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

    @Override
    public @NotNull ResourceKey<Biome> pickPlateauBiome(int temp, int humidity, @NotNull Climate.Parameter weirdness) {
        var biomeEntry = PLATEAU_BIOMES[temp][humidity];
        if (weirdness.max() >= 0) {
            var variantBiome = PLATEAU_BIOMES_VARIANT[temp][humidity];
            if (variantBiome != null) {
                biomeEntry = variantBiome;
            }
        }

        if (biomeEntry != null && biomeEntry.isEnabled()) {
            return biomeEntry.biome.getKey();
        }

        return super.pickPlateauBiome(temp, humidity, weirdness);
    }
}
