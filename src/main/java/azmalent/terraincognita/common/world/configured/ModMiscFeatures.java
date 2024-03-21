package azmalent.terraincognita.common.world.configured;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModConfiguredFeatures;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModMiscFeatures {
    public static void init() {
        //Called to force static constructor
    }

    //Disks
    public static final RegistryObject<ConfiguredFeature<?, ?>> PEAT_DISK = registerDisk(
        "peat", () -> new DiskConfiguration(
            ModBlocks.PEAT.defaultBlockState(),
            UniformInt.of(2, 3), 1,
            ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState())
        )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> MOSSY_GRAVEL_DISK = registerDisk(
        "mossy_gravel", () -> new DiskConfiguration(
            ModBlocks.MOSSY_GRAVEL.defaultBlockState(),
            UniformInt.of(2, 3), 1,
            ImmutableList.of(Blocks.DIRT.defaultBlockState())
        )
    );

    private static RegistryObject<ConfiguredFeature<?, ?>> registerDisk(String id, Supplier<DiskConfiguration> configSupplier) {
        return ModConfiguredFeatures.register(id, Feature.DISK, configSupplier);
    }
}
