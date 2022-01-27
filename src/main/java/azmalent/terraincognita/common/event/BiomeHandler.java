package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.ModTweaks;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.world.ModDefaultFeatures;
import azmalent.terraincognita.common.world.biome.BiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class BiomeHandler {
    public static void onLoadBiome(BiomeLoadingEvent event) {
        if (event.getName().getNamespace().equals(TerraIncognita.MODID)) {
            initCustomBiome(event);
        } else if (event.getName().getNamespace().equals("minecraft")) {
            applyVanillaBiomeTweaks(event);
        }

        ResourceKey<Biome> biomeKey = BiomeUtil.getBiomeKey(event.getName());
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        if (biome == null || BiomeUtil.hasAnyType(biomeKey, END, NETHER, VOID, OCEAN, BEACH, DEAD)) return;

        //Spawns
        MobSpawnInfoBuilder spawns = event.getSpawns();
        if (BiomeUtil.hasAnyType(biomeKey, PLAINS, FOREST) && BiomeUtil.hasNoneOfTypes(biomeKey, COLD, DENSE)) {
            WorldGenUtil.addSpawner(spawns, ModEntities.BUTTERFLY, MobCategory.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
        }

        //Features
        if (TIConfig.biomeBlacklist.contains(biome)) return;
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        //Roots and hanging moss
        if (BiomeUtil.hasNoneOfTypes(biomeKey, SANDY, MESA, WASTELAND)) {
            ModDefaultFeatures.withHangingRoots(builder);
            if (BiomeUtil.hasNoneOfTypes(biomeKey, COLD, SAVANNA, DRY)) {
                ModDefaultFeatures.withHangingMoss(builder);
            }
        }

        //Sweet peas
        if (biomeKey == Biomes.FLOWER_FOREST) {
            ModDefaultFeatures.withSweetPeas(builder);
            return;
        }

        switch (WorldGenUtil.getProperBiomeCategory(biome)) {
            case PLAINS:
                ModDefaultFeatures.withAppleTrees(builder);
                break;

            case FOREST:
                ModDefaultFeatures.withForestFlowers(builder);
                ModDefaultFeatures.withAppleTrees(builder);
                ModDefaultFeatures.withHazelTrees(builder);
                break;

            case SWAMP:
                ModDefaultFeatures.withSwampVegetation(builder);
                ModDefaultFeatures.withPeatAndMossyGravel(builder);
                if (!BiomeDictionary.hasType(biomeKey, COLD)) {
                    ModDefaultFeatures.withSwampFlowers(builder);
                }
                break;

            case DESERT:
                ModDefaultFeatures.withDesertVegetation(builder);
                break;

            case SAVANNA:
                ModDefaultFeatures.withSavannaFlowers(builder);
                break;

            case EXTREME_HILLS:
                if (!BiomeDictionary.hasType(biomeKey, HOT)) {
                    ModDefaultFeatures.withAlpineFlowers(builder);
                }
                break;

            case JUNGLE:
                ModDefaultFeatures.withJungleVegetation(builder);
                break;

            case ICY:
                ModDefaultFeatures.withCaribouMoss(builder);
            case TAIGA:
                ModDefaultFeatures.withArcticFlowers(builder);
                break;
        }
    }

    private static void initCustomBiome(BiomeLoadingEvent event) {
        ResourceLocation id = event.getName();

        BiomeEntry biome = ModBiomes.ID_TO_BIOME_MAP.get(id);
        if (biome == null) {
            TerraIncognita.LOGGER.warn("Attempting to load unregistered biome " + id);
            return;
        }

        biome.initFeatures(event.getGeneration());
    }

    private static void applyVanillaBiomeTweaks(BiomeLoadingEvent event) {
        ResourceKey<Biome> biome = BiomeUtil.getBiomeKey(event.getName());
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        if (biome == Biomes.SNOWY_TUNDRA || biome == Biomes.ICE_SPIKES) {
            ModDefaultFeatures.withExtraTundraFeatures(builder);
            ModTweaks.addExtraTundraSpawns(event.getSpawns());
        }
        else if (biome == Biomes.SOUL_SAND_VALLEY) {
            ModDefaultFeatures.withWitherRoses(builder);
        }
    }
}
