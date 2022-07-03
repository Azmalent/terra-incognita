package azmalent.terraincognita.common.world.tree;

import azmalent.terraincognita.common.world.configured.ModConfiguredTreeFeatures;
import azmalent.terraincognita.common.world.tree.decorator.AppleDecorator;
import net.minecraft.core.Holder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static azmalent.terraincognita.common.registry.ModWoodTypes.APPLE;

public class AppleTree extends AbstractTreeGrower {
    public static TreeConfiguration getTreeConfig(boolean grown) {
        var builder = new TreeConfiguration.TreeConfigurationBuilder(
            SimpleStateProvider.simple(APPLE.LOG.defaultBlockState()),
            new StraightTrunkPlacer(5, 2, 0),
            new WeightedStateProvider(
                new SimpleWeightedRandomList.Builder<BlockState>()
                    .add(APPLE.LEAVES.defaultBlockState(), 1)
                    .add(APPLE.BLOSSOMING_LEAVES.defaultBlockState(), 1)
                    .build()
            ),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .ignoreVines();

        if (!grown) {
            builder = builder.decorators(List.of(AppleDecorator.INSTANCE));
        }

        return builder.build();
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull Random random, boolean largeHive) {
        return ModConfiguredTreeFeatures.APPLE_GROWN;
    }
}
