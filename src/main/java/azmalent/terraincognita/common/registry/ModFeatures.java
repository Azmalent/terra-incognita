package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.feature.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = TerraIncognita.REG_HELPER.getOrCreateRegistry(ForgeRegistries.FEATURES);

    public static final RegistryObject<SweetPeasFeature> SWEET_PEAS       = FEATURES.register("sweet_peas", SweetPeasFeature::new);
    public static final RegistryObject<SedgeFeature> SEDGE                = FEATURES.register("sedge", SedgeFeature::new);
    public static final RegistryObject<HangingMossFeature> HANGING_MOSS   = FEATURES.register("hanging_moss", HangingMossFeature::new);
    public static final RegistryObject<CaribouMossFeature> CARIBOU_MOSS   = FEATURES.register("caribou_moss", CaribouMossFeature::new);
    public static final RegistryObject<FallenLogFeature> FALLEN_LOG       = FEATURES.register("fallen_log", FallenLogFeature::new);
    public static final RegistryObject<MossyBoulderFeature> MOSSY_BOULDER = FEATURES.register("mossy_boulder", MossyBoulderFeature::new);
}
