package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.gen.feature.*;

import net.minecraft.data.worldgen.Features;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModConfiguredFeatures {
    public static class Configs {
        public static final DiskConfiguration PEAT = new DiskConfiguration(ModBlocks.PEAT.getBlock().defaultBlockState(), UniformInt.of(3, 2), 1, ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState()));
        public static final DiskConfiguration MOSSY_GRAVEL = new DiskConfiguration(ModBlocks.MOSSY_GRAVEL.getBlock().defaultBlockState(), UniformInt.of(3, 2), 1, ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()));
    }

    //Ores and such
    public static ConfiguredFeature<?, ?> PEAT_DISK;
    public static ConfiguredFeature<?, ?> MOSSY_GRAVEL_DISK;

    //Local decorations
    public static ConfiguredFeature<?, ?> TUNDRA_ROCK;
    public static ConfiguredFeature<?, ?> MUSKEG_LOG;

    public static <TConfig extends FeatureConfiguration> ConfiguredFeature<TConfig, ?> register(String id, ConfiguredFeature<TConfig, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }

    public static void registerFeatures() {
        ModFlowerFeatures.registerFeatures();
        ModVegetationFeatures.registerFeatures();
        ModTreeFeatures.registerFeatures();

        PEAT_DISK = register("peat_disk", Feature.DISK.configured(Configs.PEAT).decorated(Features.Decorators.TOP_SOLID_HEIGHTMAP_SQUARE).chance(2));
        MOSSY_GRAVEL_DISK = register("mossy_gravel_disk", Feature.DISK.configured(Configs.MOSSY_GRAVEL).decorated(Features.Decorators.TOP_SOLID_HEIGHTMAP_SQUARE).chance(2));

        TUNDRA_ROCK = register("tundra_rock", ModFeatures.MOSSY_BOULDER.get().configured(NoneFeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(2));
        MUSKEG_LOG = register("muskeg_log", ModFeatures.MUSKEG_LOG.get().configured(NoneFeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).chance(4));
    }
}
