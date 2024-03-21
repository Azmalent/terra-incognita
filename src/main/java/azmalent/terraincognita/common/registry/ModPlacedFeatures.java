package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.placement.ModMiscFeaturePlacements;
import azmalent.terraincognita.common.world.placement.ModTreePlacements;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
        DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, TerraIncognita.MODID);

    static {
        ModTreePlacements.init();
        ModVegetationPlacements.init();
        ModMiscFeaturePlacements.init();
    }

    public static RegistryObject<PlacedFeature> register(String id, RegistryObject<ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return PLACED_FEATURES.register(id, () -> new PlacedFeature(feature.getHolder().get(), modifiers.get()));
    }

    public static RegistryObject<PlacedFeature> register(String id, Holder<ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return PLACED_FEATURES.register(id, () -> new PlacedFeature(feature, modifiers.get()));
    }
}
