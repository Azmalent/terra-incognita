package azmalent.terraincognita.common.init;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.ModTrees;
import com.google.common.collect.Maps;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.BiFunction;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, TerraIncognita.MODID);

    public static final Map<ResourceLocation, BiFunction<Double, Double, Integer>> CUSTOM_GRASS_MODIFIERS = Maps.newHashMap();

    public static RegistryObject<Biome> TUNDRA;
    public static RegistryObject<Biome> ROCKY_TUNDRA;

    static {
        if (TIConfig.Biomes.tundraVariants.get()) {
            MobSpawnInfo.Builder spawns = (new MobSpawnInfo.Builder()).withCreatureSpawnProbability(0.07F);
            DefaultBiomeFeatures.withSnowyBiomeMobs(spawns);

            if (TIConfig.Misc.betterTundras.get()) {
                addExtraTundraSpawns(spawns);
            }

            TUNDRA = BIOMES.register("tundra", () -> {
                BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);

                return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY)
                        .depth(0.125F).scale(0.05F).temperature(0.2F).downfall(0.5F)
                        .setEffects((new BiomeAmbience.Builder())
                                .setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463)
                                .withSkyColor(getSkyColorWithTemperatureModifier(0.2F))
                                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
                        .withMobSpawnSettings(spawns.copy()).withGenerationSettings(builder.build()).build();
            });

            ROCKY_TUNDRA = BIOMES.register("rocky_tundra", () -> {
                BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);

                return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY)
                        .depth(0.5F).scale(0.05F).temperature(0.2F).downfall(0.5F)
                        .setEffects((new BiomeAmbience.Builder())
                                .setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463)
                                .withSkyColor(getSkyColorWithTemperatureModifier(0.2F))
                                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
                        .withMobSpawnSettings(spawns.copy()).withGenerationSettings(builder.build()).build();
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void initGrassModifiers() {
        if (TIConfig.Biomes.tundraVariants.get()) {
            CUSTOM_GRASS_MODIFIERS.put(TUNDRA.getId(), (x, z) -> {
                double d0 = Biome.INFO_NOISE.noiseAt(x * 0.0225D, z * 0.0225D, false);
                return d0 < -0.1D ? 0xAB853E : 0xB46438;
            });

            CUSTOM_GRASS_MODIFIERS.put(ROCKY_TUNDRA.getId(), (x, z) -> {
                double d0 = Biome.INFO_NOISE.noiseAt(x * 0.0225D, z * 0.0225D, false);
                return d0 < -0.1D ? 0xAA3539 : 0xB46438;
            });
        }

        for (ResourceLocation id : CUSTOM_GRASS_MODIFIERS.keySet()) {
            TerraIncognita.LOGGER.info("Registered grass modifier for biome " + id);
        }
    }

    public static void registerBiomes() {
        if (TIConfig.Biomes.tundraVariants.get()) {
            registerBiome(BiomeManager.BiomeType.ICY, TUNDRA, 5);
            BiomeDictionary.addTypes(BiomeUtil.getBiomeKey(TUNDRA.getId()), Type.COLD, Type.WASTELAND, Type.OVERWORLD);

            registerBiome(BiomeManager.BiomeType.ICY, ROCKY_TUNDRA, 2);
            BiomeDictionary.addTypes(BiomeUtil.getBiomeKey(ROCKY_TUNDRA.getId()), Type.COLD, Type.WASTELAND, Type.OVERWORLD);
        }
    }

    private static void registerBiome(BiomeManager.BiomeType type, RegistryObject<Biome> biome, int weight) {
        RegistryKey<Biome> key = BiomeUtil.getBiomeKey(biome.getId());
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));
    }

    public static void addTundraFeatures(BiomeGenerationSettings.Builder builder) {
        builder.withStructure(StructureFeatures.VILLAGE_SNOWY)
                .withStructure(StructureFeatures.PILLAGER_OUTPOST)
                .withStructure(StructureFeatures.RUINED_PORTAL);

        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withSnowySpruces(builder);
        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withBadlandsGrass(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);

        if (TIConfig.Misc.betterTundras.get()) {
            addExtraTundraFeatures(builder);
        }
    }

    public static void addExtraTundraSpawns(MobSpawnInfo.Builder spawns) {
        spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 2, 4, 4));
        spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 1, 2, 4));
    }

    public static void addExtraTundraFeatures(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.withSparseBerries(builder);

        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModTrees.RARE_BIRCHES);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModTrees.SPRUCE_SHRUB);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModTrees.DWARF_BIRCH);
    }

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}
