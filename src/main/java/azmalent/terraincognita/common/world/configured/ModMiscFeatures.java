package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.world.feature.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import static azmalent.terraincognita.util.ConfiguredFeatureHelper.*;

public class ModMiscFeatures {
    //Disks
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

    //Boulders
    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, Feature<SimpleRandomFeatureConfiguration>>> TUNDRA_ROCK = registerRandomFeature(
        "tundra_boulder",
        PlacementUtils.inlinePlaced(Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.COBBLESTONE.defaultBlockState())),
        PlacementUtils.inlinePlaced(Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.MOSSY_COBBLESTONE.defaultBlockState())),
        PlacementUtils.inlinePlaced(Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.ANDESITE.defaultBlockState()))
    );
}
