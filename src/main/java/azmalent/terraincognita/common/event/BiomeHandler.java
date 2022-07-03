package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.ModDefaultFeatures;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.resources.ResourceKey;
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
        if (biome == null || event.getName().getNamespace().equals(TerraIncognita.MODID)) {
            return;
        }

        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(event.getName());

        if (BiomeDictionary.hasType(key, OVERWORLD)) {
            handleOverworldBiome(biome, event.getGeneration(), event.getSpawns());
        } else if (BiomeDictionary.hasType(key, NETHER)) {
            handleNetherBiome(biome, event.getGeneration());
        }
    }

    private static void handleOverworldBiome(Biome biome, BiomeGenerationSettingsBuilder generation, MobSpawnSettingsBuilder spawns) {
        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(biome);

        //Spawns
        if (WorldGenUtil.hasAnyBiomeType(key, PLAINS, FOREST) && !WorldGenUtil.hasAnyBiomeType(key, COLD, DENSE)) {
            ModDefaultFeatures.butterflies(spawns);
        }

        //Hanging moss
        if (!WorldGenUtil.hasAnyBiomeType(key, OCEAN, BEACH, SANDY, MESA, WASTELAND, COLD, SAVANNA, DRY)) {
            ModDefaultFeatures.addHangingMoss(generation);
        }

        //Sweet peas
        if (key == Biomes.FLOWER_FOREST) {
            ModDefaultFeatures.addSweetPeas(generation);
            return;
        }

        //Category specific
        var category = WorldGenUtil.getProperBiomeCategory(biome);
        switch (category) {
            case PLAINS:
                ModDefaultFeatures.addAppleTrees(generation);
                break;

            case FOREST:
                ModDefaultFeatures.addForestFlowers(generation);
                ModDefaultFeatures.addHazelTrees(generation);
                break;

            case SWAMP:
                ModDefaultFeatures.addSedge(generation);
                ModDefaultFeatures.addPeatAndMossyGravel(generation);
                if (!BiomeDictionary.hasType(key, COLD)) {
                    ModDefaultFeatures.addSwampFlowers(generation);
                }
                break;

            case DESERT:
                ModDefaultFeatures.addDesertVegetation(generation);
                break;

            case SAVANNA:
                ModDefaultFeatures.addSavannaFlowers(generation);
                break;

            case EXTREME_HILLS:
                if (!BiomeDictionary.hasType(key, HOT)) {
                    ModDefaultFeatures.addAlpineFlowers(generation);
                }
                break;

            case JUNGLE:
                ModDefaultFeatures.addJungleVegetation(generation);
                break;

            case ICY:
                ModDefaultFeatures.addCaribouMoss(generation);
                //Intentional fall-through!
            case TAIGA:
                ModDefaultFeatures.addArcticFlowers(generation);
                break;
        }
    }

    private static void handleNetherBiome(Biome biome, BiomeGenerationSettingsBuilder generation) {
        ResourceKey<Biome> key = WorldGenUtil.getBiomeKey(biome);

        if (key == Biomes.SOUL_SAND_VALLEY) {
            ModDefaultFeatures.addWitherRoses(generation);
        }
    }
}
