package azmalent.terraincognita.mixin;

import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FoxEntity.Type.class)
public class FoxEntityTypeMixin {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Inject(method = "func_242325_a", at = @At("HEAD"), cancellable = true)
    private static void getTypeByBiome(Optional<RegistryKey<Biome>> biome, CallbackInfoReturnable<FoxEntity.Type> cir) {
        if (biome.isPresent() && BiomeDictionary.hasType(biome.get(), BiomeDictionary.Type.SNOWY)) {
            cir.setReturnValue(FoxEntity.Type.SNOW);
            return;
        }

        cir.setReturnValue(FoxEntity.Type.RED);
    }
}
