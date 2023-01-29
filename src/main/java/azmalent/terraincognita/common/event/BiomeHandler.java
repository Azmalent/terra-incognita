package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.world.ModDefaultFeatures;
import azmalent.terraincognita.common.world.biome.TIBiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeHandler {
    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void onLoadBiome(BiomeLoadingEvent event) {
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        if (biome == null) {
            return;
        }

        ResourceLocation id = event.getName();
        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(event.getName());

        if (id.getNamespace().equals(TerraIncognita.MODID)) {
            handleTIBiome(ModBiomes.BIOMES_BY_NAME.get(id), event.getGeneration(), event.getSpawns());
        } else if (BiomeDictionary.hasType(key, OVERWORLD)) {
            handleOverworldBiome(biome, event.getGeneration(), event.getSpawns());
        } else if (BiomeDictionary.hasType(key, NETHER)) {
            handleNetherBiome(biome, event.getGeneration());
        }
    }

    private static void handleTIBiome(TIBiomeEntry biome, BiomeGenerationSettingsBuilder generation, MobSpawnSettingsBuilder spawns) {
        biome.initFeatures(generation);
        biome.initSpawns(spawns);
    }

    private static void handleOverworldBiome(Biome biome, BiomeGenerationSettingsBuilder generation, MobSpawnSettingsBuilder spawns) {
        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(biome);

        //Spawns
        if (WorldGenUtil.hasAnyBiomeType(key, PLAINS, FOREST) && !WorldGenUtil.hasAnyBiomeType(key, COLD, DENSE)) {
            ModDefaultFeatures.butterflies(spawns);
        }

        if (key == Biomes.FLOWER_FOREST) {
            ModDefaultFeatures.sweetPeas(generation);
        } else if (key == Biomes.LUSH_CAVES) {
            ModDefaultFeatures.hangingMoss(generation);
        } else {
            var category = WorldGenUtil.getProperBiomeCategory(biome);
            switch (category) {
                case PLAINS:
                    ModDefaultFeatures.appleTrees(generation);
                    break;

                case FOREST:
                    ModDefaultFeatures.forestFlowers(generation);
                    ModDefaultFeatures.hazelTrees(generation);
                    break;

                case SWAMP:
                    ModDefaultFeatures.swampReeds(generation);
                    ModDefaultFeatures.peatAndMossyGravel(generation);
                    if (!BiomeDictionary.hasType(key, COLD)) {
                        ModDefaultFeatures.swampFlowers(generation);
                    }
                    break;

                case DESERT:
                    ModDefaultFeatures.desertVegetation(generation);
                    break;

                case SAVANNA:
                    ModDefaultFeatures.savannaFlowers(generation);
                    break;

                case EXTREME_HILLS:
                    if (!BiomeDictionary.hasType(key, HOT)) {
                        ModDefaultFeatures.alpineFlowers(generation);
                        ModDefaultFeatures.sparseLarchTrees(generation);
                    }
                    break;

                case JUNGLE:
                    ModDefaultFeatures.jungleVegetation(generation);
                    break;

                case ICY:
                    ModDefaultFeatures.caribouMoss(generation);
                    ModDefaultFeatures.rareLarchTrees(generation);
                    //Intentional fall-through!

                case TAIGA:
                    ModDefaultFeatures.arcticFlowers(generation);
                    break;
            }
        }
    }

    private static void handleNetherBiome(Biome biome, BiomeGenerationSettingsBuilder generation) {
        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(biome);

        if (key == Biomes.SOUL_SAND_VALLEY) {
            ModDefaultFeatures.witherRoses(generation);
        }
    }
}
