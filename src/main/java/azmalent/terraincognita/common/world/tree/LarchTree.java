package azmalent.terraincognita.common.world.tree;

import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.world.configured.ModTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class LarchTree extends AbstractMegaTreeGrower {
    public static TreeConfiguration getTreeConfig(boolean tall) {
        int height = tall ? 12 : 7;
        int randomHeight1 = tall ? 5 : 2;
        int randomHeight2 = tall ? 4 : 1;

        int radius = tall ? 3 : 2;
        int randomRadius = tall ? 5 : 3;

        int bareHeight = tall ? 4 : 2;
        int randomBareHeight = tall ? 4 : 2;

        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModWoodTypes.LARCH.LOG.defaultBlockState()),
            new StraightTrunkPlacer(height, randomHeight1, randomHeight2),
            BlockStateProvider.simple(ModWoodTypes.LARCH.LEAVES.defaultBlockState()),
            new SpruceFoliagePlacer(UniformInt.of(radius, randomRadius), UniformInt.of(0, 2), UniformInt.of(bareHeight, randomBareHeight)),
            new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build();
    }

    public static TreeConfiguration getMegaTreeConfig() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModWoodTypes.LARCH.LOG.defaultBlockState()),
            new GiantTrunkPlacer(11, 2, 12),
            BlockStateProvider.simple(ModWoodTypes.LARCH.LEAVES.defaultBlockState()),
            new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
            new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().build();
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull Random random, boolean largeHive) {
        return (random.nextInt(10) == 0 ? ModTreeFeatures.LARCH_TALL : ModTreeFeatures.LARCH).getHolder().get();
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return ModTreeFeatures.MEGA_LARCH.getHolder().get();
    }
}
