package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.world.ModTreeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class AppleTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@Nonnull Random randomIn, boolean largeHive) {
        return ModTreeFeatures.APPLE_TREE_GROWN;
    }
}
