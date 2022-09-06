package azmalent.terraincognita.common.world.tree;

import azmalent.terraincognita.common.world.configured.ModConfiguredTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
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
            new FancyTrunkPlacer(8, 4, 4),
            SimpleStateProvider.simple(GINKGO.LEAVES.defaultBlockState()),
            new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(3), 100),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(6)))
            .ignoreVines()
            .build();
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull Random random, boolean largeHive) {
        return ModConfiguredTreeFeatures.GINKGO;
    }
}
