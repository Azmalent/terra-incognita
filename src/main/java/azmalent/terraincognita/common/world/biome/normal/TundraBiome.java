package azmalent.terraincognita.common.world.biome.normal;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.ModTweaks;
import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.biome.Biome.*;

public class TundraBiome extends NormalBiomeEntry {
    public TundraBiome(String id, Supplier<Integer> spawnWeight) {
        super(id, spawnWeight);
    }

    @Override
    protected Category getCategory() {
        return Category.ICY;
    }

    @Override
    protected Climate getClimate() {
        return new Climate(RainType.SNOW, 0.2f, TemperatureModifier.NONE, 0.5f);
    }

    @Override
    protected float getDepth() {
        return 0.125f;
    }

    @Override
    protected float getScale() {
        return 0.1f;
    }

    @Override
    protected BiomeAmbience getAmbience() {
        return (new BiomeAmbience.Builder())
            .setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463)
            .withSkyColor(getSkyColorWithTemperatureModifier(0.2F))
            .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build();
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> getSurfaceBuilder() {
        return ConfiguredSurfaceBuilders.field_244178_j;
    }

    @Override
    protected MobSpawnInfo.Builder initSpawns() {
        MobSpawnInfo.Builder spawns = (new MobSpawnInfo.Builder()).withCreatureSpawnProbability(0.07F);
        DefaultBiomeFeatures.withSnowyBiomeMobs(spawns);

        if (TIConfig.Misc.betterTundras.get()) {
            ModTweaks.addExtraTundraSpawns(spawns);
        }

        return spawns;
    }

    @Override
    protected BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.ICY;
    }

    @Override
    protected List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return Lists.newArrayList(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.COLD, BiomeDictionary.Type.WASTELAND);
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
        initDefaultFeatures(builder);

        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
        builder.withStructure(StructureFeatures.VILLAGE_SNOWY)
               .withStructure(StructureFeatures.PILLAGER_OUTPOST)
               .withStructure(StructureFeatures.RUINED_PORTAL);

        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withSnowySpruces(builder);
        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withBadlandsGrass(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);

        if (TIConfig.Misc.betterTundras.get()) {
            ModTweaks.addExtraTundraFeatures(builder);
        }
    }

    @Override
    public boolean hasCustomGrassModifier() {
        return true;
    }

    @Override
    public int getCustomGrassColor(double x, double z) {
        double d0 = INFO_NOISE.noiseAt(x * 0.0225D, z * 0.0225D, false);
        return d0 < -0.1D ? 0xADA258 : 0x80B497;
    }
}
