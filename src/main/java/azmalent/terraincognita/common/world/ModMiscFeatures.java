package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;

@SuppressWarnings("ConstantConditions")
public class ModMiscFeatures {
    public static ConfiguredFeature<?, ?> PEAT_DISK;
    public static ConfiguredFeature<?, ?> MOSSY_GRAVEL_DISK;
    public static ConfiguredFeature<?, ?> ROCK;

    public static void configureFeatures() {
        ROCK = register("tundra_rock", ModFeatures.MOSSY_BOULDER.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242732_c(2));

        if (TIConfig.Misc.peat.get()) {
            SphereReplaceConfig config = new SphereReplaceConfig(ModBlocks.PEAT.getBlock().getDefaultState(), FeatureSpread.func_242253_a(3, 2), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState()));
            PEAT_DISK = register("peat", Feature.DISK.withConfiguration(config).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).chance(2));
        }

        if (TIConfig.Misc.mossyGravel.get()) {
            SphereReplaceConfig config = new SphereReplaceConfig(ModBlocks.MOSSY_GRAVEL.getBlock().getDefaultState(), FeatureSpread.func_242253_a(3, 2), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState()));
            MOSSY_GRAVEL_DISK = register("mossy_gravel", Feature.DISK.withConfiguration(config).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).chance(2));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> register(String id, ConfiguredFeature feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }
}
