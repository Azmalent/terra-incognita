package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.valueproviders.UniformInt;
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
    public static <TConfig extends FeatureConfiguration> ConfiguredFeature<TConfig, ?> register(String id, ConfiguredFeature<TConfig, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }

    public static void registerFeatures() {
        ModFlowerFeatures.registerFeatures();
        ModVegetationFeatures.registerFeatures();
        ModTreeFeatures.registerFeatures();
        ModMiscFeatures.registerFeatures();
    }
}
