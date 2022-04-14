package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.world.tree.AppleTree;
import azmalent.terraincognita.common.world.tree.GinkgoTree;
import azmalent.terraincognita.common.world.tree.HazelTree;
import azmalent.terraincognita.common.world.tree.LarchTree;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import static azmalent.terraincognita.util.ConfiguredFeatureHelper.registerTreeFeature;
import static azmalent.terraincognita.util.ConfiguredFeatureHelper.registerWeightedRandomFeature;

public class ModConfiguredTreeFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> APPLE_GROWN = registerTreeFeature(
        "apple_grown", AppleTree.getTreeConfig(true)
    );

    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> APPLE_NATURAL = registerTreeFeature(
        "apple_natural", AppleTree.getTreeConfig(false)
    );

    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> HAZEL_GROWN = registerTreeFeature(
        "hazel_grown", HazelTree.getTreeConfig(true)
    );

    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> HAZEL_NATURAL = registerTreeFeature(
        "hazel_natural", HazelTree.getTreeConfig(false)
    );

    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> LARCH = registerTreeFeature(
        "larch", LarchTree.getTreeConfig()
    );

    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> GINKGO = registerTreeFeature(
        "ginkgo", GinkgoTree.getTreeConfig()
    );

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> MUSKEG_TREES = registerWeightedRandomFeature(
        "muskeg_trees", TreePlacements.PINE_CHECKED,
        new WeightedPlacedFeature(TreePlacements.MEGA_PINE_CHECKED, 0.05f),
        new WeightedPlacedFeature(TreePlacements.BIRCH_CHECKED, 0.1f)
    );
}
