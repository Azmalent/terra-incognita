package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModBiomes;
import azmalent.terraincognita.common.world.ModVegetation;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;

import static azmalent.cuneiform.lib.util.BiomeUtil.hasAnyType;
import static net.minecraftforge.common.BiomeDictionary.Type.*;
import static net.minecraftforge.common.BiomeDictionary.hasType;

public class BiomeHandler {
    public static void registerListeners() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, BiomeHandler::addFeaturesToCustomBiomes);
        MinecraftForge.EVENT_BUS.addListener(BiomeHandler::applyVanillaBiomeTweaks);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, BiomeHandler::addCustomFeatures);
    }

    public static void addCustomFeatures(BiomeLoadingEvent event) {
        RegistryKey<Biome> biome = BiomeUtil.getBiomeKey(event.getName());
        if (hasAnyType(biome, END, NETHER, VOID, OCEAN, BEACH)) return;

        boolean cold = hasType(biome, COLD);
        boolean hot = hasType(biome, HOT);

        if (!hasAnyType(biome, SANDY, WASTELAND)) {
            ModVegetation.addVegetation(event, ModVegetation.ROOTS);
        }

        switch (event.getCategory()) {
            case SWAMP:
                ModVegetation.addVegetation(event, ModVegetation.SMALL_LILYPADS);
                ModVegetation.addVegetation(event, ModVegetation.REEDS);
                if (!cold) {
                    ModVegetation.addVegetation(event, ModVegetation.SWAMP_FLOWERS);
                }
                break;
            case SAVANNA:
                ModVegetation.addVegetation(event, ModVegetation.SAVANNA_FLOWERS);
                break;
            case DESERT:
                ModVegetation.addVegetation(event, ModVegetation.DESERT_MARIGOLDS);
                break;
            case EXTREME_HILLS:
                if (!hot) {
                    ModVegetation.addVegetation(event, ModVegetation.ALPINE_FLOWERS);
                    ModVegetation.addVegetation(event, ModVegetation.EDELWEISS);
                }
                break;
            case JUNGLE:
                ModVegetation.addVegetation(event, ModVegetation.JUNGLE_FLOWERS);
                ModVegetation.addVegetation(event, ModVegetation.LOTUS);
                break;
            case TAIGA: case ICY:
                ModVegetation.addVegetation(event, ModVegetation.ARCTIC_FLOWERS);
                break;
        }
    }

    public static void addFeaturesToCustomBiomes(BiomeLoadingEvent event) {
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
            ModVegetation.addVegetation(event, ModVegetation.WITHER_ROSE);
        }
    }
}
