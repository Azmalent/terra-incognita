package azmalent.terraincognita.common.world;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.*;

public class ModOres {
    public static ConfiguredFeature<?, ?> PEAT;

    public static void configureFeatures() {
        if (TIConfig.Misc.peat.get()) {
            SphereReplaceConfig config = new SphereReplaceConfig(ModBlocks.PEAT.getBlock().getDefaultState(), FeatureSpread.func_242253_a(2, 1), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState()));
            PEAT = Feature.DISK.withConfiguration(config).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
        }
    }
}
