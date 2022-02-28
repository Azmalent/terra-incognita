package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.world.ModDefaultFeatures;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
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

@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeHandler {
    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void onLoadBiome(BiomeLoadingEvent event) {
        switch (event.getName().getNamespace()) {
            case TerraIncognita.MODID -> initCustomBiome(event);
            case "minecraft" -> applyVanillaBiomeTweaks(event);
        }


        ResourceKey<Biome> biomeKey = WorldGenUtil.getBiomeKey(event.getName());
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        if (biome == null || WorldGenUtil.hasAnyBiomeType(biomeKey, END, NETHER, VOID, OCEAN, BEACH, DEAD)) return;

        //Spawns
        MobSpawnSettingsBuilder spawns = event.getSpawns();
        if (WorldGenUtil.hasAnyBiomeType(biomeKey, PLAINS, FOREST) && !WorldGenUtil.hasAnyBiomeType(biomeKey, COLD, DENSE)) {
            WorldGenUtil.addSpawner(spawns, ModEntities.BUTTERFLY, MobCategory.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
        }

        //Features
        if (TIConfig.biomeBlacklist.contains(biome)) return;
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        //Roots and hanging moss
        if (!WorldGenUtil.hasAnyBiomeType(biomeKey, SANDY, MESA, WASTELAND)) {
            ModDefaultFeatures.withHangingRoots(builder);
            if (!WorldGenUtil.hasAnyBiomeType(biomeKey, COLD, SAVANNA, DRY)) {
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
        //TODO: init custom biome

        /*        ResourceLocation id = event.getName();

        BiomeEntry biome = ModBiomes.ID_TO_BIOME_MAP.get(id);
        if (biome == null) {
            TerraIncognita.LOGGER.warn("Attempting to load unregistered biome " + id);
            return;
        }

        biome.initFeatures(event.getGeneration());*/
    }

    private static void applyVanillaBiomeTweaks(BiomeLoadingEvent event) {
        ResourceKey<Biome> biome = WorldGenUtil.getBiomeKey(event.getName());
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        if (biome == Biomes.SOUL_SAND_VALLEY) {
            ModDefaultFeatures.withWitherRoses(builder);
        }
    }
}
