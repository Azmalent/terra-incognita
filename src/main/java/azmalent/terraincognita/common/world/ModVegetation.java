package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.plants.SmallLilypadBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModFeatures;
import azmalent.terraincognita.common.init.blocksets.PottablePlantEntry;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModVegetation {
    public static class Configs {
        public static BlockClusterFeatureConfig FIELD_FLOWERS;
        public static BlockClusterFeatureConfig SWAMP_FLOWERS;
        public static BlockClusterFeatureConfig ALPINE_FLOWERS;
        public static BlockClusterFeatureConfig EDELWEISS;
        public static BlockClusterFeatureConfig SAVANNA_FLOWERS;
        public static BlockClusterFeatureConfig DESERT_MARIGOLDS;
        public static BlockClusterFeatureConfig JUNGLE_FLOWERS;
        public static BlockClusterFeatureConfig ARCTIC_FLOWERS;
        public static BlockClusterFeatureConfig WITHER_ROSE;
    }

    public static ConfiguredFeature<?, ?> FIELD_FLOWERS;
    public static ConfiguredFeature<?, ?> SWAMP_FLOWERS;
    public static ConfiguredFeature<?, ?> ALPINE_FLOWERS;
    public static ConfiguredFeature<?, ?> EDELWEISS;
    public static ConfiguredFeature<?, ?> SAVANNA_FLOWERS;
    public static ConfiguredFeature<?, ?> DESERT_MARIGOLDS;
    public static ConfiguredFeature<?, ?> JUNGLE_FLOWERS;
    public static ConfiguredFeature<?, ?> ARCTIC_FLOWERS;
    public static ConfiguredFeature<?, ?> WITHER_ROSE;
    public static ConfiguredFeature<?, ?> LOTUS;
    public static ConfiguredFeature<?, ?> SMALL_LILYPADS;
    public static ConfiguredFeature<?, ?> REEDS;
    public static ConfiguredFeature<?, ?> ROOTS;

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

    private static ConfiguredFeature<?, ?> initLilypadFeature(BlockStateProvider provider, int tries) {
        BlockClusterFeatureConfig config = new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER)
                .tries(tries).whitelist(Sets.newHashSet(Blocks.WATER)).build();

        return Feature.RANDOM_PATCH.withConfiguration(config).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(4);
    }

    @SuppressWarnings("unchecked")
    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> register(String id, ConfiguredFeature feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }

    @SuppressWarnings("ConstantConditions")
    public static void configureFeatures() {
		if (TIConfig.Flora.fieldFlowers.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(ModBlocks.CHICORY.getBlock().getDefaultState(), 2);
            provider.addWeightedBlockstate(ModBlocks.YARROW.getBlock().getDefaultState(), 1);

            Configs.FIELD_FLOWERS = createConfig(provider, 32);
            FIELD_FLOWERS = register("field_flowers", initFlowerFeature(Configs.FIELD_FLOWERS));
        }
    
        if (TIConfig.Flora.swampFlowers.get()) {
            Configs.SWAMP_FLOWERS = createConfig(ModBlocks.FORGET_ME_NOT, 32);
            SWAMP_FLOWERS = register("swamp_flowers", initFlowerFeature(Configs.SWAMP_FLOWERS));
        }

        if (TIConfig.Flora.arcticFlowers.get())  {
            Configs.ARCTIC_FLOWERS = createConfig(ModBlocks.FIREWEED, 32);
            ARCTIC_FLOWERS = register("arctic_flowers", initFlowerFeature(Configs.ARCTIC_FLOWERS));
        }

        if (TIConfig.Flora.alpineFlowers.get()) {
            Configs.EDELWEISS = createConfig(ModBlocks.EDELWEISS, 48);
            EDELWEISS = register("edelweiss", ModFeatures.EDELWEISS.get().withConfiguration(Configs.EDELWEISS).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4));

            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(ModBlocks.ALPINE_PINK.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.ROCKFOIL.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.GENTIAN.getBlock().getDefaultState(), 1);

            Configs.ALPINE_FLOWERS = new BlockClusterFeatureConfig.Builder(provider, SimpleBlockPlacer.PLACER).tries(32).func_227317_b_().build();
            ALPINE_FLOWERS = register("alpine_flowers", ModFeatures.ALPINE_FLOWERS.get().withConfiguration(Configs.ALPINE_FLOWERS).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4));
        }

        if (TIConfig.Flora.savannaFlowers.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(ModBlocks.MARIGOLD.getBlock().getDefaultState(), 2);
            provider.addWeightedBlockstate(ModBlocks.BLUE_LUPIN.getBlock().getDefaultState(), 3);
            provider.addWeightedBlockstate(ModBlocks.RED_SNAPDRAGON.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.YELLOW_SNAPDRAGON.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.MAGENTA_SNAPDRAGON.getBlock().getDefaultState(), 1);

            Configs.SAVANNA_FLOWERS = createConfig(provider, 32);
            SAVANNA_FLOWERS = register("savanna_flowers", initFlowerFeature(Configs.SAVANNA_FLOWERS));

            Configs.DESERT_MARIGOLDS = createConfig(ModBlocks.MARIGOLD, 24);
            DESERT_MARIGOLDS = register("desert_marigolds", initFlowerFeature(Configs.DESERT_MARIGOLDS));
        }

        if (TIConfig.Flora.jungleFlowers.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(ModBlocks.BLUE_IRIS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.PURPLE_IRIS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.BLACK_IRIS.getBlock().getDefaultState(), 1);

            Configs.JUNGLE_FLOWERS = createConfig(provider, 64);
            JUNGLE_FLOWERS = register("jungle_flowers", initFlowerFeature(Configs.JUNGLE_FLOWERS));
        }

        if (TIConfig.Flora.smallLilypad.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            for (int i = 0; i < 4; i++) {
                BlockState blockState = ModBlocks.SMALL_LILYPAD.getBlock().getDefaultState().with(SmallLilypadBlock.LILYPADS, i + 1);
                provider.addWeightedBlockstate(blockState, 1);
            }

            SMALL_LILYPADS = register("small_lilypads", initLilypadFeature(provider, 10));
        }

        if (TIConfig.Flora.lotus.get()) {
            WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
            provider.addWeightedBlockstate(Blocks.LILY_PAD.getDefaultState(), 3);
            provider.addWeightedBlockstate(ModBlocks.PINK_LOTUS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.WHITE_LOTUS.getBlock().getDefaultState(), 1);
            provider.addWeightedBlockstate(ModBlocks.YELLOW_LOTUS.getBlock().getDefaultState(), 1);

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

        if (TIConfig.Tweaks.witherRoseGeneration.get()) {
            Configs.WITHER_ROSE = createConfig(Blocks.WITHER_ROSE.getDefaultState(), 128);
            WITHER_ROSE = register("wither_roses", initFlowerFeature(Configs.WITHER_ROSE));
        }
    }
}
