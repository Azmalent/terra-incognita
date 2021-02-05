package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModBiomes;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;

import static azmalent.cuneiform.lib.util.BiomeUtil.hasAnyType;
import static azmalent.terraincognita.common.world.ModVegetation.*;
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
        if (!id.getNamespace().equals(TerraIncognita.MODID)) {
            return;
        }

        if (TIConfig.Biomes.tundraVariants.get() && id.equals(ModBiomes.TUNDRA.getId()) || id.equals(ModBiomes.ROCKY_TUNDRA.getId())) {
            ModBiomes.addTundraFeatures(event.getGeneration());

            if (id.equals(ModBiomes.ROCKY_TUNDRA.getId())) {
                DefaultBiomeFeatures.withForestRocks(event.getGeneration());
            }
        }
    }

    public static void addCustomFeatures(BiomeLoadingEvent event) {
        RegistryKey<Biome> biome = BiomeUtil.getBiomeKey(event.getName());
        if (hasAnyType(biome, END, NETHER, VOID, OCEAN, BEACH)) return;

        boolean cold = hasType(biome, COLD);
        boolean hot = hasType(biome, HOT);

        if (!hasAnyType(biome, SANDY, WASTELAND)) {
            WorldGenUtil.addVegetation(event, ROOTS);
        }

        switch (event.getCategory()) {
            case PLAINS:
                WorldGenUtil.addVegetation(event, FIELD_FLOWERS, ModTrees.RARE_APPLE);
                break;
            case FOREST:
                if (hot) {
                    WorldGenUtil.addVegetation(event, JUNGLE_FLOWERS, LOTUS);
                } else if (cold) {
                    WorldGenUtil.addVegetation(event, ARCTIC_FLOWERS);
                }
                else {
                    WorldGenUtil.addVegetation(event, ModTrees.RARE_APPLE);
                }
                break;
            case SWAMP:
                WorldGenUtil.addVegetation(event, SMALL_LILYPADS, REEDS);
                if (!cold) {
                    WorldGenUtil.addVegetation(event, SWAMP_FLOWERS);
                }
                break;
            case SAVANNA:
                WorldGenUtil.addVegetation(event, SAVANNA_FLOWERS);
                break;
            case DESERT:
                WorldGenUtil.addVegetation(event, DESERT_MARIGOLDS);
                break;
            case EXTREME_HILLS:
                if (!hot) {
                    WorldGenUtil.addVegetation(event, ALPINE_FLOWERS, EDELWEISS);
                }
                break;
            case JUNGLE:
                WorldGenUtil.addVegetation(event, JUNGLE_FLOWERS, LOTUS);
                break;
            case TAIGA: case ICY:
                WorldGenUtil.addVegetation(event, ARCTIC_FLOWERS);
                break;
        }
    }

    public static void applyVanillaBiomeTweaks(BiomeLoadingEvent event) {
        ResourceLocation id = event.getName();
        if (!id.getNamespace().equals("minecraft")) {
            return;
        }

        RegistryKey<Biome> biome = BiomeUtil.getBiomeKey(id);

        if (TIConfig.Tweaks.betterTundras.get() && (biome == Biomes.SNOWY_TUNDRA || biome == Biomes.ICE_SPIKES)) {
            ModBiomes.addExtraTundraFeatures(event.getGeneration());
            ModBiomes.addExtraTundraSpawns(event.getSpawns());
        }
        else if (biome == Biomes.SOUL_SAND_VALLEY) {
            WorldGenUtil.addVegetation(event, WITHER_ROSE);
        }
    }
}
