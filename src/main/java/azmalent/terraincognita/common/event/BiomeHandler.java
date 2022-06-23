package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.world.ModDefaultFeatures;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.core.Holder;
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

@SuppressWarnings("deprecation")
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
        if (biome == null || WorldGenUtil.hasAnyBiomeType(biomeKey, BiomeDictionary.Type.END, BiomeDictionary.Type.NETHER, BiomeDictionary.Type.VOID, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.DEAD)) {
            return;
        }

        MobSpawnSettingsBuilder spawns = event.getSpawns();
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        //Spawns
        if (WorldGenUtil.hasAnyBiomeType(biomeKey, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST) && !WorldGenUtil.hasAnyBiomeType(biomeKey, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DENSE)) {
            WorldGenUtil.addSpawner(spawns, ModEntities.BUTTERFLY, MobCategory.AMBIENT, TIConfig.Fauna.butterflySpawnWeight.get(), 4, 8);
        }

        //Hanging moss
        if (!WorldGenUtil.hasAnyBiomeType(biomeKey, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.MESA, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.DRY)) {
            ModDefaultFeatures.withHangingMoss(builder);
        }

        //Sweet peas
        if (biomeKey == Biomes.FLOWER_FOREST) {
            ModDefaultFeatures.withSweetPeas(builder);
            return;
        }

        switch (WorldGenUtil.getProperBiomeCategory(Holder.direct(biome))) {
            case PLAINS:
                ModDefaultFeatures.withAppleTrees(builder);
                break;

            case FOREST:
                ModDefaultFeatures.withForestFlowers(builder);
                ModDefaultFeatures.withHazelTrees(builder);
                break;

            case SWAMP:
                ModDefaultFeatures.withSwampVegetation(builder);
                ModDefaultFeatures.withPeatAndMossyGravel(builder);
                if (!BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.COLD)) {
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
                if (!BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.HOT)) {
                    ModDefaultFeatures.withAlpineFlowers(builder);
                }
                break;

            case JUNGLE:
                ModDefaultFeatures.withJungleVegetation(builder);
                break;

            case ICY:
                ModDefaultFeatures.withCaribouMoss(builder);
                //Intentional fall-through!
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
