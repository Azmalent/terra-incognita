package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;

@SuppressWarnings("ConstantConditions")
public class ModOres {
    public static ConfiguredFeature<?, ?> PEAT_DISK;

    public static void configureFeatures() {
        if (TIConfig.Misc.peat.get()) {
            SphereReplaceConfig config = new SphereReplaceConfig(ModBlocks.PEAT.getBlock().getDefaultState(), FeatureSpread.func_242253_a(3, 2), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState()));
            PEAT_DISK = register("peat", Feature.DISK.withConfiguration(config).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).chance(2));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> register(String id, ConfiguredFeature feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TerraIncognita.prefix(id), feature);
    }
}
