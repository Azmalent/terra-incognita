package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModMiscFeatures {
    //Ores
    public static ConfiguredFeature<?, ?> PEAT_DISK;
    public static ConfiguredFeature<?, ?> MOSSY_GRAVEL_DISK;

    //Local decorations
    public static ConfiguredFeature<?, ?> TUNDRA_ROCK;
    public static ConfiguredFeature<?, ?> MUSKEG_LOG;

    public static void registerFeatures() {
        PEAT_DISK = ModConfiguredFeatures.register("peat_disk", Feature.DISK.configured(new DiskConfiguration(
                ModBlocks.PEAT.defaultBlockState(),
                UniformInt.of(2, 3), 1,
                ImmutableList.of( Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState())) )
        );

        MOSSY_GRAVEL_DISK = ModConfiguredFeatures.register("mossy_gravel_disk", Feature.DISK.configured(new DiskConfiguration(
                ModBlocks.MOSSY_GRAVEL.defaultBlockState(),
                UniformInt.of(3, 2), 1,
                ImmutableList.of( Blocks.DIRT.defaultBlockState(), Blocks.GRAVEL.defaultBlockState())) )
        );

        TUNDRA_ROCK = ModConfiguredFeatures.register("tundra_rock", ModFeatures.MOSSY_BOULDER.get().configured(NoneFeatureConfiguration.NONE));
        MUSKEG_LOG = ModConfiguredFeatures.register("muskeg_log", ModFeatures.FALLEN_LOG.get().configured(NoneFeatureConfiguration.NONE));
    }
}
