package azmalent.terraincognita.mixin.client;

import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.world.biome.BiomeEntry;
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

        if (ModBiomes.ID_TO_BIOME_MAP.containsKey(id)) {
            BiomeEntry biomeData = ModBiomes.ID_TO_BIOME_MAP.get(id);
            if (biomeData.hasCustomGrassModifier()) {
                int color = biomeData.getCustomGrassColor(x, z);
                cir.setReturnValue(color);
            }
        }
    }
}
