package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import azmalent.terraincognita.common.world.feature.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;

import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import static azmalent.terraincognita.util.ConfiguredFeatureHelper.*;
import static net.minecraft.data.worldgen.placement.PlacementUtils.inlinePlaced;

public class ModConfiguredMiscFeatures {
    public static final Holder<ConfiguredFeature<DiskConfiguration, Feature<DiskConfiguration>>> PEAT_DISK = registerFeature(
        "peat", Feature.DISK, new DiskConfiguration(
            ModBlocks.PEAT.defaultBlockState(),
            UniformInt.of(2, 3), 1,
            ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState())
        )
    );

    public static final Holder<ConfiguredFeature<DiskConfiguration, Feature<DiskConfiguration>>> MOSSY_GRAVEL_DISK = registerFeature(
        "mossy_gravel", Feature.DISK, new DiskConfiguration(
            ModBlocks.PEAT.defaultBlockState(),
            UniformInt.of(2, 3), 1,
            ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState())
        )
    );

    public static final Holder<ConfiguredFeature<FallenLogFeature.Config, FallenLogFeature>> FALLEN_LOG = registerFeature(
        "fallen_log", ModFeatures.FALLEN_LOG.get(), new FallenLogFeature.Config(
            new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.SPRUCE_LOG.defaultBlockState(), 2)
                    .add(Blocks.BIRCH_LOG.defaultBlockState(), 1)
                    .build()
            ), true, true
        )
    );
}