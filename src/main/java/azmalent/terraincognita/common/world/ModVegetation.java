package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.plants.SmallLilypadBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModFeatures;
import azmalent.terraincognita.common.block.blocksets.PottablePlantEntry;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.*;
import net.minecraft.world.gen.feature.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static azmalent.terraincognita.common.init.ModBlocks.*;

public class ModVegetation {
    public static class Configs {
        public static BlockClusterFeatureConfig FOREST_FLOWERS;
        public static BlockClusterFeatureConfig SWAMP_FLOWERS;
        public static BlockClusterFeatureConfig CATTAILS;
        public static BlockClusterFeatureConfig WATER_FLAG;
        public static BlockClusterFeatureConfig ALPINE_FLOWERS;
        public static BlockClusterFeatureConfig EDELWEISS;
        public static BlockClusterFeatureConfig SAVANNA_FLOWERS;
        public static BlockClusterFeatureConfig DESERT_MARIGOLDS;
        public static BlockClusterFeatureConfig JUNGLE_FLOWERS;
        public static BlockClusterFeatureConfig ARCTIC_FLOWERS;
        public static BlockClusterFeatureConfig ARCTIC_TALL_FLOWERS;
        public static BlockClusterFeatureConfig WITHER_ROSE;
    }

    public static ConfiguredFeature<?, ?> FOREST_FLOWERS;
    public static ConfiguredFeature<?, ?> SWAMP_FLOWERS;
    public static ConfiguredFeature<?, ?> ALPINE_FLOWERS;
    public static ConfiguredFeature<?, ?> SAVANNA_FLOWERS;
    public static ConfiguredFeature<?, ?> DESERT_MARIGOLDS;
    public static ConfiguredFeature<?, ?> JUNGLE_FLOWERS;
    public static ConfiguredFeature<?, ?> ARCTIC_FLOWERS;
    public static ConfiguredFeature<?, ?> WITHER_ROSE;

    public static ConfiguredFeature<?, ?> LOTUS;
    public static ConfiguredFeature<?, ?> SMALL_LILYPADS;
    public static ConfiguredFeature<?, ?> REEDS;
    public static ConfiguredFeature<?, ?> ROOTS;
    public static ConfiguredFeature<?, ?> HANGING_MOSS;

    private static BlockClusterFeatureConfig createConfig(PottablePlantEntry plant, int tries) {
        return createConfig(plant.getBlock().getDefaultState(), tries);
    }

    private static BlockClusterFeatureConfig createConfig(BlockState blockState, int tries) {
        SimpleBlockStateProvider provider = new SimpleBlockStateProvider(blockState);
        return createConfig(provider, tries);
    }

    private static BlockClusterFeatureConfig createConfig(BlockStateProvider provider, int tries) {
        return new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER).tries(tries).build();
    }

    private static ConfiguredFeature<?, ?> initFlowerFeature(BlockClusterFeatureConfig config) {
        return Feature.FLOWER.withConfiguration(config)
            .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4);
    }

    private static ConfiguredFeature<?, ?> initTallFlowerFeature(BlockClusterFeatureConfig config, int min, int max) {
        return Feature.RANDOM_PATCH.withConfiguration(config)
            .func_242730_a(FeatureSpread.func_242253_a(min, max))
            .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5);
    }

    private static ConfiguredFeature<?, ?> initLilypadFeature(BlockStateProvider provider, int tries) {
        BlockClusterFeatureConfig config = new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER)
                .tries(tries).whitelist(Sets.newHashSet(Blocks.WATER)).build();

        return Feature.RANDOM_PATCH.withConfiguration(config).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(4);
    }

    @SuppressWarnings("unchecked")
    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> register(String id, ConfiguredFeature<T, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }

    @SuppressWarnings("ConstantConditions")
    public static void configureFeatures() {
		if (TIConfig.Flora.fieldFlowers.get()) {
            List<BlockState> flowers = Lists.newArrayList(PlainFlowerBlockStateProvider.COMMON_FLOWERS);
            flowers.add(CHICORY.getBlock().getDefaultState());
            flowers.add(YARROW.getBlock().getDefaultState());
            PlainFlowerBlockStateProvider.COMMON_FLOWERS = flowers.toArray(new BlockState[0]);

            List<BlockState> rareFlowers = Lists.newArrayList(PlainFlowerBlockStateProvider.RARE_FLOWERS);
            rareFlowers.add(DAFFODIL.getBlock().getDefaultState());
            PlainFlowerBlockStateProvider.RARE_FLOWERS = rareFlowers.toArray(new BlockState[0]);

            addToFlowerForestGradient(DAFFODIL);
        }

		if (TIConfig.Flora.forestFlowers.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(BLUEBELL.getBlock().getDefaultState(), 3);
            provider.addWeightedBlockstate(PINK_PRIMROSE.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(PURPLE_PRIMROSE.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(YELLOW_PRIMROSE.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(FOXGLOVE.getBlock().getDefaultState(), 2);
            Configs.FOREST_FLOWERS = createConfig(provider, 12);

            FOREST_FLOWERS = register("forest_flowers", initFlowerFeature(Configs.FOREST_FLOWERS));

            addToFlowerForestGradient(BLUEBELL, PINK_PRIMROSE, PURPLE_PRIMROSE, YELLOW_PRIMROSE, FOXGLOVE);
        }

        if (TIConfig.Flora.swampFlowers.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(FORGET_ME_NOT.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(GLOBEFLOWER.getBlock().getDefaultState(), 1);
            Configs.SWAMP_FLOWERS = createConfig(provider, 32);

            Configs.CATTAILS = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(CATTAIL.getBlock().getDefaultState()), new DoublePlantBlockPlacer())).tries(48).func_227317_b_().requiresWater().replaceable().build();
            Configs.WATER_FLAG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(WATER_FLAG.getBlock().getDefaultState()), new DoublePlantBlockPlacer())).tries(24).func_227317_b_().requiresWater().replaceable().build();

            List<ConfiguredRandomFeatureList> chances = Lists.newArrayList(
                initFlowerFeature(Configs.SWAMP_FLOWERS).withChance(0.4f),
                initTallFlowerFeature(Configs.WATER_FLAG, -1, 4).withChance(0.3f)
            );

            ConfiguredFeature<?, ?> defaultFeature = initTallFlowerFeature(Configs.CATTAILS, 2, 4);

            SWAMP_FLOWERS = register("swamp_flowers", Feature.RANDOM_SELECTOR
                .withConfiguration(new MultipleRandomFeatureConfig(chances, defaultFeature))
                .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
                .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4)
            );
        }

        if (TIConfig.Flora.arcticFlowers.get()) {
            Configs.ARCTIC_FLOWERS = createConfig(DWARF_FIREWEED, 8);

            WeightedBlockStateProvider tallFlowerProvider = new WeightedBlockStateProvider();
            tallFlowerProvider.addWeightedBlockstate(TALL_FIREWEED.getBlock().getDefaultState(), 2);
            tallFlowerProvider.addWeightedBlockstate(WHITE_RHODODENDRON.getBlock().getDefaultState(), 1);
            Configs.ARCTIC_TALL_FLOWERS = (new BlockClusterFeatureConfig.Builder(tallFlowerProvider, new DoublePlantBlockPlacer())).tries(12).func_227317_b_().build();

            List<ConfiguredRandomFeatureList> chances = Lists.newArrayList(
                initFlowerFeature(Configs.ARCTIC_FLOWERS).withChance(0.5f)
            );

            ConfiguredFeature<?, ?> defaultFeature = initTallFlowerFeature(Configs.ARCTIC_TALL_FLOWERS, -3, 4);

            ARCTIC_FLOWERS = register("arctic_flowers", Feature.RANDOM_SELECTOR
                .withConfiguration(new MultipleRandomFeatureConfig(chances, defaultFeature))
                .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
                .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4)
            );
        }

        if (TIConfig.Flora.alpineFlowers.get()) {
            Configs.EDELWEISS = createConfig(EDELWEISS, 24);

            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(ALPINE_PINK.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(SAXIFRAGE.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(GENTIAN.getBlock().getDefaultState(), 1);
            Configs.ALPINE_FLOWERS = new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER).tries(24).func_227317_b_().build();

            List<Supplier<ConfiguredFeature<?, ?>>> features = Lists.newArrayList(
                () -> ModFeatures.EDELWEISS.get().withConfiguration(Configs.EDELWEISS).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4),
                () -> ModFeatures.ALPINE_FLOWERS.get().withConfiguration(Configs.ALPINE_FLOWERS).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4)
            );

            ALPINE_FLOWERS = register("alpine_flowers", Feature.SIMPLE_RANDOM_SELECTOR
                .withConfiguration(new SingleRandomFeature(features))
                .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
                .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4)
            );
        }

        if (TIConfig.Flora.savannaFlowers.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(MARIGOLD.getBlock().getDefaultState(), 2);
            provider.addWeightedBlockstate(BLUE_LUPINE.getBlock().getDefaultState(), 3);
            provider.addWeightedBlockstate(RED_SNAPDRAGON.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(YELLOW_SNAPDRAGON.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(MAGENTA_SNAPDRAGON.getBlock().getDefaultState(), 1);

            Configs.SAVANNA_FLOWERS = createConfig(provider, 32);
            SAVANNA_FLOWERS = register("savanna_flowers", initFlowerFeature(Configs.SAVANNA_FLOWERS));

            Configs.DESERT_MARIGOLDS = createConfig(MARIGOLD, 24);
            DESERT_MARIGOLDS = register("desert_marigolds", initFlowerFeature(Configs.DESERT_MARIGOLDS));
        }

        if (TIConfig.Flora.jungleFlowers.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(BLUE_IRIS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(PURPLE_IRIS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(BLACK_IRIS.getBlock().getDefaultState(), 1);

            Configs.JUNGLE_FLOWERS = createConfig(provider, 48);
            JUNGLE_FLOWERS = register("jungle_flowers", initFlowerFeature(Configs.JUNGLE_FLOWERS));
        }

        if (TIConfig.Flora.smallLilypad.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            for (int i = 0; i < 4; i++) {
                BlockState blockState = SMALL_LILY_PAD.getBlock().getDefaultState().with(SmallLilypadBlock.LILYPADS, i + 1);
                provider.addWeightedBlockstate(blockState, 1);
            }

            SMALL_LILYPADS = register("small_lilypads", initLilypadFeature(provider, 10));
        }

        if (TIConfig.Flora.lotus.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(Blocks.LILY_PAD.getDefaultState(), 3);
            provider.addWeightedBlockstate(PINK_LOTUS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(WHITE_LOTUS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(YELLOW_LOTUS.getBlock().getDefaultState(), 1);

            LOTUS = register("lotuses", initLilypadFeature(provider, 10));
        }

        if (TIConfig.Flora.reeds.get()) {
            REEDS = register("reeds", ModFeatures.REEDS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20).chance(8));
        }

        if (TIConfig.Flora.roots.get()) {
            SimpleBlockStateProvider provider = new SimpleBlockStateProvider(ModBlocks.ROOTS.getBlock().getDefaultState());
            BlockClusterFeatureConfig config = new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER).tries(60).xSpread(4).ySpread(16).zSpread(4).func_227317_b_().build();
            ROOTS = register("roots", Feature.RANDOM_PATCH.withConfiguration(config).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
        }

        if (TIConfig.Flora.hangingMoss.get()) {
            HANGING_MOSS = register("hanging_moss", ModFeatures.MOSS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
        }

        if (TIConfig.Misc.witherRoseGeneration.get()) {
            Configs.WITHER_ROSE = createConfig(Blocks.WITHER_ROSE.getDefaultState(), 128);
            WITHER_ROSE = register("wither_roses", initFlowerFeature(Configs.WITHER_ROSE));
        }
    }

    private static void addToFlowerForestGradient(PottablePlantEntry... flowers) {
        List<BlockState> states = Lists.newArrayList(ForestFlowerBlockStateProvider.STATES);
        states.addAll(Arrays.stream(flowers).map(flower -> flower.getBlock().getDefaultState()).collect(Collectors.toList()));

        ForestFlowerBlockStateProvider.STATES = states.toArray(new BlockState[0]);
    }
}
