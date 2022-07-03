package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.common.world.feature.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.FEATURES);

    public static final RegistryObject<CactusFlowerFeature> CACTUS_FLOWERS  = FEATURES.register("cactus_flowers", CactusFlowerFeature::new);
    public static final RegistryObject<CaribouMossFeature>  CARIBOU_MOSS    = FEATURES.register("caribou_moss", CaribouMossFeature::new);
    public static final RegistryObject<HangingMossFeature>  HANGING_MOSS    = FEATURES.register("hanging_moss", HangingMossFeature::new);
    public static final RegistryObject<FallenLogFeature>    FALLEN_LOG      = FEATURES.register("fallen_log", FallenLogFeature::new);
    public static final RegistryObject<SedgeFeature>        SEDGE           = FEATURES.register("sedge", SedgeFeature::new);
    public static final RegistryObject<SweetPeasFeature>    SWEET_PEAS      = FEATURES.register("sweet_peas", SweetPeasFeature::new);
}
