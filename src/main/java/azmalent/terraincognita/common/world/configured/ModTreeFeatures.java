package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.registry.ModConfiguredFeatures;
import azmalent.terraincognita.common.world.tree.AppleTree;
import azmalent.terraincognita.common.world.tree.GinkgoTree;
import azmalent.terraincognita.common.world.tree.HazelTree;
import azmalent.terraincognita.common.world.tree.LarchTree;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModTreeFeatures {
    public static void init() {
        //Called to force static constructor
    }

    public static final RegistryObject<ConfiguredFeature<?, ?>> APPLE_GROWN = register(
        "apple_grown", () -> AppleTree.getTreeConfig(true)
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> APPLE_NATURAL = register(
        "apple_natural", () -> AppleTree.getTreeConfig(false)
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> HAZEL_GROWN = register(
        "hazel_grown", () -> HazelTree.getTreeConfig(true)
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> HAZEL_NATURAL = register(
        "hazel_natural", () -> HazelTree.getTreeConfig(false)
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> LARCH = register(
        "larch", () -> LarchTree.getTreeConfig(false)
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> LARCH_TALL = register(
        "larch_tall", () -> LarchTree.getTreeConfig(true)
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> MEGA_LARCH = register(
        "mega_larch", LarchTree::getMegaTreeConfig
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> GINKGO = register(
        "ginkgo", GinkgoTree::getTreeConfig
    );

    public static RegistryObject<ConfiguredFeature<?, ?>> register(String id, Supplier<TreeConfiguration> configSupplier) {
        return ModConfiguredFeatures.register(id, Feature.TREE, configSupplier);
    }
}
