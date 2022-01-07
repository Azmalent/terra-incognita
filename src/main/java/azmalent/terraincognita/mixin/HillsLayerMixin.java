package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.world.biome.BiomeEntry;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.common.world.biome.SubBiomeEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.layer.RegionHillsLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings({"deprecation"})
@Mixin(RegionHillsLayer.class)
public class HillsLayerMixin {
    private static final int SUB_BIOME_CHANCE = 3;

    @Inject(method = "apply", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/gen/INoiseRandom;random(I)I"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void registerSubBiomes(Context random, Area area1, Area area2, int x, int z, CallbackInfoReturnable<Integer> cir, int i, int j, int k) {
        if (random.nextRandom(SUB_BIOME_CHANCE) == 0 || k == 0) {
            Biome biome = BuiltinRegistries.BIOME.byId(i);
            if (biome == null) return;

            ResourceLocation id = BuiltinRegistries.BIOME.getKey(biome);
            BiomeEntry biomeEntry = ModBiomes.ID_TO_BIOME_MAP.get(id);
            if (biomeEntry instanceof NormalBiomeEntry) {

                SubBiomeEntry subBiome = ((NormalBiomeEntry) biomeEntry).getRandomSubBiome(random);
                if (subBiome != null) {
                    cir.setReturnValue(subBiome.getNumericId());
                }
            }
        }
    }
}
