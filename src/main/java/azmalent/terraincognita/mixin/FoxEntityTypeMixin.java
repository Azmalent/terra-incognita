package azmalent.terraincognita.mixin;

import net.minecraft.world.entity.animal.Fox;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Fox.Type.class)
public class FoxEntityTypeMixin {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Inject(method = "byBiome", at = @At("HEAD"), cancellable = true)
    private static void getTypeByBiome(Optional<ResourceKey<Biome>> biome, CallbackInfoReturnable<Fox.Type> cir) {
        if (biome.isPresent() && BiomeDictionary.hasType(biome.get(), BiomeDictionary.Type.SNOWY)) {
            cir.setReturnValue(Fox.Type.SNOW);
            return;
        }

        cir.setReturnValue(Fox.Type.RED);
    }
}
