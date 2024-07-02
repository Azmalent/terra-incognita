package azmalent.terraincognita.core.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.placement.ModMiscFeaturePlacements;
import azmalent.terraincognita.common.world.placement.ModTreePlacements;
import azmalent.terraincognita.common.world.placement.ModVegetationPlacements;
import azmalent.terraincognita.common.world.placement.VanillaVegetationPlacements;
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
public class ModPlacements {
    public static final DeferredRegister<PlacedFeature> PLACEMENTS =
        DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, TerraIncognita.MODID);

    static {
        ModTreePlacements.init();
        ModVegetationPlacements.init();
        ModMiscFeaturePlacements.init();
        VanillaVegetationPlacements.init();
    }

    public static RegistryObject<PlacedFeature> register(String id, RegistryObject<ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return PLACEMENTS.register(id, () -> new PlacedFeature(feature.getHolder().get(), modifiers.get()));
    }

    public static RegistryObject<PlacedFeature> register(String id, Holder<ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return PLACEMENTS.register(id, () -> new PlacedFeature(feature, modifiers.get()));
    }
}
