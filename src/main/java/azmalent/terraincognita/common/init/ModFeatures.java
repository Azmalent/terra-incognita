package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.feature.EdelweissFeature;
import azmalent.terraincognita.common.world.feature.ReedsFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, TerraIncognita.MODID);

    public static RegistryObject<EdelweissFeature> EDELWEISS;
    public static RegistryObject<ReedsFeature> REEDS;

    static {
        if (TIConfig.Flora.edelweiss.get()) EDELWEISS = FEATURES.register("edelweiss", EdelweissFeature::new);
        if (TIConfig.Flora.reeds.get()) REEDS = FEATURES.register("reeds", ReedsFeature::new);
    }
}
