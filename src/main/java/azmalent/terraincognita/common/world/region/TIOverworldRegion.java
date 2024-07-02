package azmalent.terraincognita.common.world.region;

import azmalent.terraincognita.TerraIncognita;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class TIOverworldRegion extends Region {
    public TIOverworldRegion(int weight) {
        super(TerraIncognita.prefix("overworld"), RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        var builder = new TIOverworldBiomeBuilder();
        builder.addBiomes(mapper);
    }
}
