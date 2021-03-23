package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;

public class ModConfiguredFeatures {
    public static class Configs {
        public static final SphereReplaceConfig PEAT = new SphereReplaceConfig(ModBlocks.PEAT.getBlock().getDefaultState(), FeatureSpread.func_242253_a(3, 2), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState()));
        public static final SphereReplaceConfig MOSSY_GRAVEL = new SphereReplaceConfig(ModBlocks.MOSSY_GRAVEL.getBlock().getDefaultState(), FeatureSpread.func_242253_a(3, 2), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState()));
    }

    //Ores and such
    public static ConfiguredFeature<?, ?> PEAT_DISK;
    public static ConfiguredFeature<?, ?> MOSSY_GRAVEL_DISK;

    //Local decorations
    public static ConfiguredFeature<?, ?> TUNDRA_ROCK;
    public static ConfiguredFeature<?, ?> MUSKEG_LOG;

    public static <TConfig extends IFeatureConfig> ConfiguredFeature<TConfig, ?> register(String id, ConfiguredFeature<TConfig, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }

    public static void registerFeatures() {
        ModFlowerFeatures.registerFeatures();
        ModVegetationFeatures.registerFeatures();
        ModTreeFeatures.registerFeatures();

        PEAT_DISK = register("peat_disk", Feature.DISK.withConfiguration(Configs.PEAT).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).chance(2));
        MOSSY_GRAVEL_DISK = register("mossy_gravel_disk", Feature.DISK.withConfiguration(Configs.MOSSY_GRAVEL).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).chance(2));

        TUNDRA_ROCK = register("tundra_rock", ModFeatures.MOSSY_BOULDER.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242732_c(2));
        MUSKEG_LOG = register("muskeg_log", ModFeatures.MUSKEG_LOG.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(4));
    }
}
