package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.feature.AlpineFlowerFeature;
import azmalent.terraincognita.common.world.feature.HangingMossFeature;
import azmalent.terraincognita.common.world.feature.ReedsFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, TerraIncognita.MODID);

    public static RegistryObject<AlpineFlowerFeature> ALPINE_FLOWERS = FEATURES.register("alpine_flowers", () -> new AlpineFlowerFeature(64));
    public static RegistryObject<AlpineFlowerFeature> EDELWEISS = FEATURES.register("edelweiss", () -> new AlpineFlowerFeature(TIConfig.Flora.edelweissMinimumY.get()));
    public static RegistryObject<ReedsFeature> REEDS = FEATURES.register("reeds", ReedsFeature::new);
    public static RegistryObject<HangingMossFeature> HANGING_MOSS = FEATURES.register("hanging_moss", HangingMossFeature::new);
}
