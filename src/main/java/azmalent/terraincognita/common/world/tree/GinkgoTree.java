package azmalent.terraincognita.common.world.tree;

import azmalent.terraincognita.common.world.configured.ModConfiguredTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;
import java.util.Random;

import static azmalent.terraincognita.common.registry.ModWoodTypes.GINKGO;

public class GinkgoTree extends AbstractTreeGrower {
    public static TreeConfiguration getTreeConfig() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            SimpleStateProvider.simple(GINKGO.LOG.defaultBlockState()),
            new FancyTrunkPlacer(5, 8, 0),
            SimpleStateProvider.simple(GINKGO.LEAVES.defaultBlockState()),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
            .ignoreVines()
            .build();
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull Random random, boolean largeHive) {
        return ModConfiguredTreeFeatures.GINKGO;
    }
}
