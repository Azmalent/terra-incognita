package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.block.plant.SmallLilyPadBlock;
import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.blockplacers.DoublePlantPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.gen.feature.*;

import net.minecraft.data.worldgen.Features;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class ModVegetationFeatures {
    public static class States {
        static final BlockState LILY_PAD = Blocks.LILY_PAD.defaultBlockState();

        static final BlockState WHITE_LOTUS  = ModBlocks.WHITE_LOTUS.defaultBlockState();
        static final BlockState PINK_LOTUS   = ModBlocks.PINK_LOTUS.defaultBlockState();
        static final BlockState YELLOW_LOTUS = ModBlocks.YELLOW_LOTUS.defaultBlockState();

        static final BlockState SMALL_CACTUS = ModBlocks.SMALL_CACTUS.getBlock().defaultBlockState();
    }

    public static class StateProviders {
        static final WeightedStateProvider LOTUS = new WeightedStateProvider().add(States.LILY_PAD, 3).add(States.WHITE_LOTUS, 1).add(States.PINK_LOTUS, 1).add(States.YELLOW_LOTUS, 1);
        static final WeightedStateProvider SMALL_LILY_PAD = new WeightedStateProvider();
        static {
            for (int i = 0; i < 4; i++) {
                BlockState state = ModBlocks.SMALL_LILY_PAD.defaultBlockState().setValue(SmallLilyPadBlock.LILY_PADS, i + 1);
                SMALL_LILY_PAD.add(state, 1);
            }
        }

        static final SimpleStateProvider SOUR_BERRIES = new SimpleStateProvider(ModBlocks.SOUR_BERRY_BUSH.getDefaultState().setValue(SourBerryBushBlock.AGE, 3));
    }

    public static class Configs {
        static final RandomPatchConfiguration SMALL_CACTUS = new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(States.SMALL_CACTUS), SimpleBlockPlacer.INSTANCE).tries(4).build();
    }

    public static ConfiguredFeature<?, ?> LOTUS;
    public static ConfiguredFeature<?, ?> SMALL_LILY_PADS;
    public static ConfiguredFeature<?, ?> SOUR_BERRIES;
    public static ConfiguredFeature<?, ?> REEDS;
    public static ConfiguredFeature<?, ?> HANGING_MOSS;
    public static ConfiguredFeature<?, ?> CARIBOU_MOSS;
    public static ConfiguredFeature<?, ?> SMALL_CACTUS;

    private static ConfiguredFeature<?, ?> initLilypadFeature(BlockStateProvider provider, int tries) {
        RandomPatchConfiguration config = new RandomPatchConfiguration.GrassConfigurationBuilder(provider, SimpleBlockPlacer.INSTANCE)
                .tries(tries).whitelist(Sets.newHashSet(Blocks.WATER)).build();

        return Feature.RANDOM_PATCH.configured(config).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).count(4);
    }

    public static void registerFeatures() {
        LOTUS = ModConfiguredFeatures.register("lotus", initLilypadFeature(StateProviders.LOTUS, 10));
        SMALL_LILY_PADS = ModConfiguredFeatures.register("small_lily_pad", initLilypadFeature(StateProviders.SMALL_LILY_PAD, 10));
        SOUR_BERRIES = ModConfiguredFeatures.register("sour_berries", initLilypadFeature(StateProviders.SOUR_BERRIES, 4).chance(2));
        REEDS = ModConfiguredFeatures.register("reeds", ModFeatures.SEDGE.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).count(12).chance(12));
        HANGING_MOSS = ModConfiguredFeatures.register("hanging_moss", ModFeatures.HANGING_MOSS.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).count(20));
        CARIBOU_MOSS = ModConfiguredFeatures.register("caribou_moss", ModFeatures.CARIBOU_MOSS.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(8));
        SMALL_CACTUS = ModConfiguredFeatures.register("small_cactus", Feature.RANDOM_PATCH.configured(Configs.SMALL_CACTUS).decorated(Features.Decorators.ADD_32));
    }
}
