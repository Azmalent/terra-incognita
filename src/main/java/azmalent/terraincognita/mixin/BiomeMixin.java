package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.init.ModBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private void getGrassColor(double x, double z, CallbackInfoReturnable<Integer> cir) {
        Biome self = (Biome) (Object) this;
        ResourceLocation id = self.getRegistryName();

        if (ModBiomes.CUSTOM_GRASS_MODIFIERS.containsKey(id)) {
            int color = ModBiomes.CUSTOM_GRASS_MODIFIERS.get(id).apply(x, z);
            cir.setReturnValue(color);
        }
    }
}
