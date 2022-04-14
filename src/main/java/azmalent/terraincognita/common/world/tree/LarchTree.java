package azmalent.terraincognita.common.world.tree;

import azmalent.terraincognita.common.world.configured.ModConfiguredTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static azmalent.terraincognita.common.registry.ModWoodTypes.LARCH;

public class LarchTree extends AbstractTreeGrower {
    public static TreeConfiguration getTreeConfig() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(LARCH.LOG.defaultBlockState()),
            new StraightTrunkPlacer(6, 4, 0),
            BlockStateProvider.simple(LARCH.LEAVES.defaultBlockState()),
            new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)),
            new TwoLayersFeatureSize(2, 0, 2))
            .ignoreVines()
            .build();
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull Random random, boolean largeHive) {
        return ModConfiguredTreeFeatures.LARCH;
    }
}
