package azmalent.terraincognita.common.world;

import azmalent.terraincognita.common.block.plants.SmallLilypadBlock;
import azmalent.terraincognita.common.block.plants.SourBerryBushBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;

public class ModVegetationFeatures {
    public static class States {
        static final BlockState LILY_PAD = Blocks.LILY_PAD.getDefaultState();

        static final BlockState WHITE_LOTUS  = ModBlocks.WHITE_LOTUS.getBlock().getDefaultState();
        static final BlockState PINK_LOTUS   = ModBlocks.PINK_LOTUS.getBlock().getDefaultState();
        static final BlockState YELLOW_LOTUS = ModBlocks.YELLOW_LOTUS.getBlock().getDefaultState();

        static final BlockState CATTAIL = ModBlocks.CATTAIL.getBlock().getDefaultState();
        static final BlockState ROOTS = ModBlocks.ROOTS.getDefaultState();
    }

    public static class StateProviders {
        static final WeightedBlockStateProvider LOTUS = new WeightedBlockStateProvider().addWeightedBlockstate(States.LILY_PAD, 3).addWeightedBlockstate(States.WHITE_LOTUS, 1).addWeightedBlockstate(States.PINK_LOTUS, 1).addWeightedBlockstate(States.YELLOW_LOTUS, 1);
        static final WeightedBlockStateProvider SMALL_LILY_PAD = new WeightedBlockStateProvider();
        static {
            for (int i = 0; i < 4; i++) {
                BlockState state = ModBlocks.SMALL_LILY_PAD.getDefaultState().with(SmallLilypadBlock.LILYPADS, i + 1);
                SMALL_LILY_PAD.addWeightedBlockstate(state, 1);
            }
        }

        static final SimpleBlockStateProvider SOUR_BERRIES = new SimpleBlockStateProvider(ModBlocks.SOUR_BERRY_BUSH.getDefaultState().with(SourBerryBushBlock.AGE, 3));
    }

    public static class Configs {
        static final BlockClusterFeatureConfig CATTAILS = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CATTAIL), DoublePlantBlockPlacer.PLACER).tries(48).func_227317_b_().requiresWater().replaceable().build();
        static final BlockClusterFeatureConfig ROOTS = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.ROOTS), SimpleBlockPlacer.PLACER).tries(60).xSpread(4).ySpread(16).zSpread(4).func_227317_b_().build();
    }

    public static ConfiguredFeature<?, ?> LOTUS;
    public static ConfiguredFeature<?, ?> SMALL_LILY_PADS;
    public static ConfiguredFeature<?, ?> SOUR_BERRIES;
    public static ConfiguredFeature<?, ?> CATTAILS;
    public static ConfiguredFeature<?, ?> REEDS;
    public static ConfiguredFeature<?, ?> ROOTS;
    public static ConfiguredFeature<?, ?> HANGING_MOSS;
    public static ConfiguredFeature<?, ?> CARIBOU_MOSS;

    private static ConfiguredFeature<?, ?> initLilypadFeature(BlockStateProvider provider, int tries) {
        BlockClusterFeatureConfig config = new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER)
                .tries(tries).whitelist(Sets.newHashSet(Blocks.WATER)).build();

        return Feature.RANDOM_PATCH.withConfiguration(config).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(4);
    }

    public static void registerFeatures() {
        LOTUS = ModConfiguredFeatures.register("lotus", initLilypadFeature(StateProviders.LOTUS, 10));
        SMALL_LILY_PADS = ModConfiguredFeatures.register("small_lily_pad", initLilypadFeature(StateProviders.SMALL_LILY_PAD, 10));
        SOUR_BERRIES = ModConfiguredFeatures.register("sour_berries", initLilypadFeature(StateProviders.SOUR_BERRIES, 4).chance(2));
        CATTAILS = ModConfiguredFeatures.register("cattails", Feature.RANDOM_PATCH.withConfiguration(Configs.CATTAILS).func_242730_a(FeatureSpread.func_242253_a(2, 4)).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5));
        REEDS = ModConfiguredFeatures.register("reeds", ModFeatures.REEDS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(12).chance(12));
        ROOTS = ModConfiguredFeatures.register("roots", Feature.RANDOM_PATCH.withConfiguration(Configs.ROOTS).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
        HANGING_MOSS = ModConfiguredFeatures.register("hanging_moss", ModFeatures.HANGING_MOSS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
        CARIBOU_MOSS = ModConfiguredFeatures.register("caribou_moss", ModFeatures.CARIBOU_MOSS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(8));
    }
}
