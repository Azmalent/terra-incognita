package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModBiomes;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.common.world.ModVegetation;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;

import static azmalent.cuneiform.lib.util.BiomeUtil.hasAllTypes;
import static azmalent.cuneiform.lib.util.BiomeUtil.hasAnyType;
import static net.minecraftforge.common.BiomeDictionary.Type.*;
import static net.minecraftforge.common.BiomeDictionary.hasType;

public class BiomeHandler {
    public static void registerListeners() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, BiomeHandler::addCustomFeatures);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, BiomeHandler::addFeaturesToCustomBiomes);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, BiomeHandler::applyVanillaBiomeTweaks);
    }

    public static void addCustomFeatures(BiomeLoadingEvent event) {
        RegistryKey<Biome> biome = BiomeUtil.getBiomeKey(event.getName());
        if (hasAnyType(biome, END, NETHER, VOID, OCEAN, BEACH)) return;

        boolean cold = hasType(biome, COLD);
        boolean hot = hasType(biome, HOT);

        switch (event.getCategory()) {
            case SWAMP:
                ModVegetation.addVegetation(event, ModVegetation.SMALL_LILYPADS);
                ModVegetation.addVegetation(event, ModVegetation.REEDS);
            case PLAINS: case FOREST:
                if (!cold) ModVegetation.addVegetation(event, ModVegetation.FORGET_ME_NOT);
                break;
            case SAVANNA: case DESERT:
                ModVegetation.addVegetation(event, ModVegetation.MARIGOLD);
                break;
            case EXTREME_HILLS:
                if (!hot) ModVegetation.addVegetation(event, ModVegetation.EDELWEISS);
                break;
            case JUNGLE:
                ModVegetation.addVegetation(event, ModVegetation.IRIS);
                ModVegetation.addVegetation(event, ModVegetation.LOTUS);
                break;
            case TAIGA: case ICY:
                ModVegetation.addVegetation(event, ModVegetation.FIREWEED);
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
