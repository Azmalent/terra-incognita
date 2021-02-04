package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.feature.AlpineFlowerFeature;
import azmalent.terraincognita.common.world.feature.HugeTreeFeature;
import azmalent.terraincognita.common.world.feature.ReedsFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, TerraIncognita.MODID);

    public static RegistryObject<AlpineFlowerFeature> ALPINE_FLOWERS;
    public static RegistryObject<AlpineFlowerFeature> EDELWEISS;
    public static RegistryObject<ReedsFeature> REEDS;
    public static RegistryObject<HugeTreeFeature> HUGE_TREE;

    static {
        if (TIConfig.Flora.alpineFlowers.get()) {
            ALPINE_FLOWERS = FEATURES.register("alpine_flowers", () -> new AlpineFlowerFeature(64));
            EDELWEISS = FEATURES.register("edelweiss", () -> new AlpineFlowerFeature(TIConfig.Flora.edelweissMinimumY.get()));
        }
        if (TIConfig.Flora.reeds.get()) REEDS = FEATURES.register("reeds", ReedsFeature::new);
        if (TIConfig.Trees.bigOaks.get() || TIConfig.Trees.bigBirches.get()) HUGE_TREE = FEATURES.register("huge_tree", HugeTreeFeature::new);
    }
}
