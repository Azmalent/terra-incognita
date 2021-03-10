package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.world.ModOres;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.common.world.ModVegetation;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.cuneiform.lib.util.BiomeUtil.hasAnyType;
import static net.minecraftforge.common.BiomeDictionary.Type.*;
import static net.minecraftforge.common.BiomeDictionary.hasType;

public class BiomeHandler {
    public static void registerListeners() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH,   BiomeHandler::initCustomBiomes);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, BiomeHandler::applyVanillaBiomeTweaks);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW,    BiomeHandler::addCustomFeatures);
    }

    public static void initCustomBiomes(BiomeLoadingEvent event) {
        ResourceLocation id = event.getName();
        if (!id.getNamespace().equals(TerraIncognita.MODID)) return;

        if (id.equals(ModBiomes.TUNDRA.getId()) || id.equals(ModBiomes.ROCKY_TUNDRA.getId())) {
            ModBiomes.addTundraFeatures(event.getGeneration());

            if (id.equals(ModBiomes.ROCKY_TUNDRA.getId())) {
                DefaultBiomeFeatures.withForestRocks(event.getGeneration());
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void addCustomFeatures(BiomeLoadingEvent event) {
        RegistryKey<Biome> biomeKey = BiomeUtil.getBiomeKey(event.getName());
        if (hasAnyType(biomeKey, END, NETHER, VOID, OCEAN, BEACH, DEAD)) return;

        //Spawns
        if (hasAnyType(biomeKey, PLAINS, FOREST) && !hasAnyType(biomeKey, COLD, DENSE)) {
            WorldGenUtil.addSpawner(event, ModEntities.BUTTERFLY, EntityClassification.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
        }

        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        if (TIConfig.biomeBlacklist.get().contains(biome)) {
            return;
        }

        //Sweet peas
        if (biomeKey == Biomes.FLOWER_FOREST) {
            return;
        }

        boolean cold = hasType(biomeKey, COLD);
        boolean hot = hasType(biomeKey, HOT);

        //Roots and hanging moss
        if (!hasAnyType(biomeKey, SANDY, MESA, WASTELAND)) {
            WorldGenUtil.addVegetation(event, ModVegetation.ROOTS);
            if (!hasAnyType(biomeKey, COLD, SAVANNA, DRY)) {
                WorldGenUtil.addVegetation(event, ModVegetation.HANGING_MOSS);
            }
        }

        switch (WorldGenUtil.getProperBiomeCategory(biome)) {
            case PLAINS:
                WorldGenUtil.addVegetation(event, ModTrees.NATURAL_APPLE);
                break;
            case FOREST:
                WorldGenUtil.addVegetation(event, ModVegetation.FOREST_FLOWERS, ModTrees.NATURAL_APPLE, ModTrees.NATURAL_HAZEL);
                break;
            case SWAMP:
                WorldGenUtil.addVegetation(event, ModVegetation.SMALL_LILYPADS, ModVegetation.REEDS);
                WorldGenUtil.addOre(event, ModOres.PEAT_DISK, ModOres.MOSSY_GRAVEL_DISK);
                if (!cold) WorldGenUtil.addVegetation(event, ModVegetation.SWAMP_FLOWERS);
                break;
            case SAVANNA:
                WorldGenUtil.addVegetation(event, ModVegetation.SAVANNA_FLOWERS);
                break;
            case DESERT:
                WorldGenUtil.addVegetation(event, ModVegetation.DESERT_MARIGOLDS);
                break;
            case EXTREME_HILLS:
                if (!hot) WorldGenUtil.addVegetation(event, ModVegetation.ALPINE_FLOWERS);
                break;
            case JUNGLE:
                WorldGenUtil.addVegetation(event, ModVegetation.JUNGLE_FLOWERS, ModVegetation.LOTUS);
                break;
            case TAIGA: case ICY:
                WorldGenUtil.addVegetation(event, ModVegetation.ARCTIC_FLOWERS);
                break;
        }
    }

    public static void applyVanillaBiomeTweaks(BiomeLoadingEvent event) {
        ResourceLocation id = event.getName();
        if (!id.getNamespace().equals("minecraft")) {
            return;
        }

        RegistryKey<Biome> biome = BiomeUtil.getBiomeKey(id);

        if (TIConfig.Misc.betterTundras.get() && (biome == Biomes.SNOWY_TUNDRA || biome == Biomes.ICE_SPIKES)) {
            ModBiomes.addExtraTundraFeatures(event.getGeneration());
            ModBiomes.addExtraTundraSpawns(event.getSpawns());
        }
        else if (biome == Biomes.SOUL_SAND_VALLEY) {
            WorldGenUtil.addVegetation(event, ModVegetation.WITHER_ROSE);
        }
    }
}
