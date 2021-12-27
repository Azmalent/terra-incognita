package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.feature.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, TerraIncognita.MODID);

    public static final RegistryObject<AlpineFlowerFeature> ALPINE_FLOWERS = FEATURES.register("alpine_flowers", AlpineFlowerFeature::new);
    public static final RegistryObject<SweetPeasFeature> SWEET_PEAS       = FEATURES.register("sweet_peas", SweetPeasFeature::new);
    public static final RegistryObject<ReedsFeature> REEDS                = FEATURES.register("reeds", ReedsFeature::new);
    public static final RegistryObject<HangingMossFeature> HANGING_MOSS   = FEATURES.register("hanging_moss", HangingMossFeature::new);
    public static final RegistryObject<CaribouMossFeature> CARIBOU_MOSS   = FEATURES.register("caribou_moss", CaribouMossFeature::new);
    public static final RegistryObject<FallenLogFeature> MUSKEG_LOG       = FEATURES.register("muskeg_log", FallenLogFeature::new);
    public static final RegistryObject<MossyBoulderFeature> MOSSY_BOULDER = FEATURES.register("mossy_boulder", MossyBoulderFeature::new);
}
