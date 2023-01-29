package azmalent.terraincognita.common.world.tree;

import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import azmalent.terraincognita.common.world.tree.decorator.HazelnutDecorator;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;

import static azmalent.terraincognita.common.registry.ModWoodTypes.HAZEL;

public class HazelTree extends AbstractTreeGrower {
    public static TreeConfiguration getTreeConfig(boolean grown) {
        var builder = new TreeConfiguration.TreeConfigurationBuilder(
            SimpleStateProvider.simple(HAZEL.LOG.defaultBlockState()),
            new FancyTrunkPlacer(5, 8, 0),
            SimpleStateProvider.simple(HAZEL.LEAVES.defaultBlockState()),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
            .ignoreVines();

        if (!grown) {
            builder = builder.decorators(List.of(HazelnutDecorator.INSTANCE));
        }

        return builder.build();
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull Random random, boolean largeHive) {
        return ModTreeFeatures.HAZEL_GROWN;
    }
}
