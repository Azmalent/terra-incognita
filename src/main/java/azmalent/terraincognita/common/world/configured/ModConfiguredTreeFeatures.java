package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.world.tree.AppleTree;
import azmalent.terraincognita.common.world.tree.GinkgoTree;
import azmalent.terraincognita.common.world.tree.HazelTree;
import azmalent.terraincognita.common.world.tree.LarchTree;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import static azmalent.terraincognita.util.ConfiguredFeatureHelper.registerTreeFeature;

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

    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> TUNDRA_SHRUB = registerTreeFeature(
        "tundra_shrub", new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(Blocks.OAK_LOG),
            new StraightTrunkPlacer(1, 0, 0),
            BlockStateProvider.simple(Blocks.OAK_LEAVES),
            new BushFoliagePlacer(UniformInt.of(1, 2), ConstantInt.of(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)
        ).dirt(BlockStateProvider.simple(Blocks.ROOTED_DIRT)).forceDirt().build()
    );
}
