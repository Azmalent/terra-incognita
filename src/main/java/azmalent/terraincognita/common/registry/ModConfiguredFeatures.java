package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.configured.ModMiscFeatures;
import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import azmalent.terraincognita.common.world.configured.ModVegetationFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModConfiguredFeatures {
    //TODO: fix cuneiform's registry helper not working here
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
        DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, TerraIncognita.MODID);

    static {
        ModVegetationFeatures.init();
        ModTreeFeatures.init();
        ModMiscFeatures.init();
    }

    public static <F extends Feature<NoneFeatureConfiguration>> RegistryObject<ConfiguredFeature<?, ?>> register(String id, RegistryObject<F> feature) {
        return CONFIGURED_FEATURES.register(id, () -> new ConfiguredFeature<>(feature.get(), NoneFeatureConfiguration.NONE));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<?, ?>> register(String id, F feature, Supplier<FC> configSupplier) {
        return CONFIGURED_FEATURES.register(id, () -> new ConfiguredFeature<>(feature, configSupplier.get()));
    }
}
