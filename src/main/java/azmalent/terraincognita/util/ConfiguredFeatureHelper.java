package azmalent.terraincognita.util;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public class ConfiguredFeatureHelper {
    public static <F extends Feature<NoneFeatureConfiguration>> Holder<ConfiguredFeature<NoneFeatureConfiguration, F>> registerFeature(String id, RegistryObject<F> feature) {
        return registerFeature(id, feature.get(), FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, F>> registerFeature(String id, F feature, FC config) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id).toString(), new ConfiguredFeature<>(feature, config));
    }

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> registerRandomPatchFeature(String id, int tries, BlockStateProvider stateProvider) {
        return registerFeature(id, Feature.RANDOM_PATCH, randomPatchConfig(tries, stateProvider));
    }

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> registerFlowerFeature(String id, int tries, BlockStateProvider stateProvider) {
        return registerFeature(id, Feature.FLOWER, randomPatchConfig(tries, stateProvider));
    }

    @SafeVarargs
    public static Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> registerRandomFeature(String id, Holder<PlacedFeature>... features) {
        return registerFeature(id, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(features)));
    }

    public static Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> registerWeightedRandomFeature(String id, Holder<PlacedFeature> defaultFeature, WeightedPlacedFeature... features) {
        return registerFeature(id, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(Arrays.stream(features).toList(), defaultFeature));
    }

    public static Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> registerTreeFeature(String id, TreeConfiguration config) {
        return registerFeature(id, Feature.TREE, config);
    }

    //Configs
    public static RandomPatchConfiguration randomPatchConfig(int tries, BlockStateProvider stateProvider) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(stateProvider)));
    }
}
