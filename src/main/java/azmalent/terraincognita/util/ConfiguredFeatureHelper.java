package azmalent.terraincognita.util;

import azmalent.terraincognita.core.registry.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ConfiguredFeatureHelper {
    public static RegistryObject<ConfiguredFeature<?, ?>> registerRandomPatchFeature(String id, int tries, Supplier<BlockStateProvider> stateProvider) {
        return ModConfiguredFeatures.register(id, Feature.RANDOM_PATCH, () -> randomPatchConfig(tries, stateProvider.get()));
    }

    public static RegistryObject<ConfiguredFeature<?, ?>> registerFlowerFeature(String id, int tries, Supplier<BlockStateProvider> stateProvider) {
        return ModConfiguredFeatures.register(id, Feature.FLOWER, () -> randomPatchConfig(tries, stateProvider.get()));
    }

    public static RegistryObject<ConfiguredFeature<?, ?>> registerNoBonemealFlowerFeature(String id, int tries, Supplier<BlockStateProvider> stateProvider) {
        return ModConfiguredFeatures.register(id, Feature.NO_BONEMEAL_FLOWER, () -> randomPatchConfig(tries, stateProvider.get()));
    }

    public static RegistryObject<ConfiguredFeature<?, ?>> registerRandomFeature(String id, Supplier<List<Holder<PlacedFeature>>> features) {
        return ModConfiguredFeatures.register(id, Feature.SIMPLE_RANDOM_SELECTOR, () -> new SimpleRandomFeatureConfiguration(HolderSet.direct(features.get())));
    }

    //Default feature is passed through supplier so that we can use it before it's initialized
    public static RegistryObject<ConfiguredFeature<?, ?>> registerWeightedRandomFeature(String id, Supplier<RegistryObject<PlacedFeature>> defaultFeature, Supplier<List<WeightedPlacedFeature>> features) {
        return ModConfiguredFeatures.register(id, Feature.RANDOM_SELECTOR, () -> new RandomFeatureConfiguration(features.get(), defaultFeature.get().getHolder().get()));
    }

    //Configs
    public static RandomPatchConfiguration randomPatchConfig(int tries, BlockStateProvider stateProvider) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(stateProvider)));
    }
}
