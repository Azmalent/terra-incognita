package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.ModTweaks;
import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.world.ModMiscFeatures;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.common.world.ModVegetation;
import azmalent.terraincognita.common.world.biome.BiomeEntry;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.cuneiform.lib.util.BiomeUtil.hasAnyType;
import static azmalent.cuneiform.lib.util.BiomeUtil.hasNoneOfTypes;
import static net.minecraftforge.common.BiomeDictionary.Type.*;
import static net.minecraftforge.common.BiomeDictionary.hasType;

public class BiomeHandler {
    @SuppressWarnings("ConstantConditions")
    public static void onLoadBiome(BiomeLoadingEvent event) {
        if (event.getName().getNamespace().equals(TerraIncognita.MODID)) {
            initCustomBiome(event);
        } else if (event.getName().getNamespace().equals("minecraft")) {
            applyVanillaBiomeTweaks(event);
        }

        RegistryKey<Biome> biomeKey = BiomeUtil.getBiomeKey(event.getName());
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        if (biome == null || hasAnyType(biomeKey, END, NETHER, VOID, OCEAN, BEACH, DEAD)) return;

        //Spawns
        MobSpawnInfoBuilder spawns = event.getSpawns();
        if (hasAnyType(biomeKey, PLAINS, FOREST) && !hasAnyType(biomeKey, COLD, DENSE)) {
            WorldGenUtil.addSpawner(spawns, ModEntities.BUTTERFLY, EntityClassification.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
        }

        //Features
        if (TIConfig.biomeBlacklist.contains(biome)) return;
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        //Roots and hanging moss
        if (hasNoneOfTypes(biomeKey, SANDY, MESA, WASTELAND)) {
            WorldGenUtil.addVegetation(builder, ModVegetation.ROOTS);
            if (hasNoneOfTypes(biomeKey, COLD, SAVANNA, DRY)) {
                WorldGenUtil.addVegetation(builder, ModVegetation.HANGING_MOSS);
            }
        }

        //Sweet peas
        if (biomeKey == Biomes.FLOWER_FOREST) {
            WorldGenUtil.addVegetation(builder, ModVegetation.SWEET_PEAS);
            return;
        }

        switch (WorldGenUtil.getProperBiomeCategory(biome)) {
            case PLAINS:
                WorldGenUtil.addVegetation(builder, ModTrees.NATURAL_APPLE);
                break;
            case FOREST:
                WorldGenUtil.addVegetation(builder, ModVegetation.FOREST_FLOWERS, ModTrees.NATURAL_APPLE, ModTrees.NATURAL_HAZEL);
                break;
            case SWAMP:
                WorldGenUtil.addVegetation(builder, ModVegetation.SMALL_LILYPADS, ModVegetation.REEDS);
                WorldGenUtil.addOre(builder, ModMiscFeatures.PEAT_DISK, ModMiscFeatures.MOSSY_GRAVEL_DISK);
                if (!hasType(biomeKey, COLD)) WorldGenUtil.addVegetation(builder, ModVegetation.SWAMP_FLOWERS);
                break;
            case SAVANNA:
                WorldGenUtil.addVegetation(builder, ModVegetation.SAVANNA_FLOWERS);
                break;
            case DESERT:
                WorldGenUtil.addVegetation(builder, ModVegetation.DESERT_MARIGOLDS);
                break;
            case EXTREME_HILLS:
                if (!hasType(biomeKey, HOT)) WorldGenUtil.addVegetation(builder, ModVegetation.ALPINE_FLOWERS);
                break;
            case JUNGLE:
                WorldGenUtil.addVegetation(builder, ModVegetation.JUNGLE_FLOWERS, ModVegetation.LOTUS);
                break;
            case ICY:
                WorldGenUtil.addVegetation(builder, ModVegetation.CARIBOU_MOSS);
            case TAIGA:
                WorldGenUtil.addVegetation(builder, ModVegetation.ARCTIC_FLOWERS);
                break;
        }
    }

    public static void initCustomBiome(BiomeLoadingEvent event) {
        ResourceLocation id = event.getName();

        BiomeEntry biome = ModBiomes.ID_TO_BIOME_MAP.get(id);
        if (biome == null) {
            TerraIncognita.LOGGER.warn("Attempting to load unregistered biome " + id);
            return;
        }

        biome.initFeatures(event.getGeneration());
    }

    public static void applyVanillaBiomeTweaks(BiomeLoadingEvent event) {
        RegistryKey<Biome> biome = BiomeUtil.getBiomeKey(event.getName());

        if (TIConfig.Misc.betterTundras.get() && (biome == Biomes.SNOWY_TUNDRA || biome == Biomes.ICE_SPIKES)) {
            ModTweaks.addExtraTundraFeatures(event.getGeneration());
            ModTweaks.addExtraTundraSpawns(event.getSpawns());
        }
        else if (biome == Biomes.SOUL_SAND_VALLEY) {
            WorldGenUtil.addVegetation(event.getGeneration(), ModVegetation.WITHER_ROSE);
        }
    }
}
