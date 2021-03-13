package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.world.biome.BiomeEntry;
import azmalent.terraincognita.common.world.biome.NormalBiomeEntry;
import azmalent.terraincognita.common.world.biome.SubBiomeEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.HillsLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings({"deprecation"})
@Mixin(HillsLayer.class)
public class HillsLayerMixin {
    private static final int SUB_BIOME_CHANCE = 3;

    @Inject(method = "apply", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/gen/INoiseRandom;random(I)I"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void registerSubBiomes(INoiseRandom random, IArea area1, IArea area2, int x, int z, CallbackInfoReturnable<Integer> cir, int i, int j, int k) {
        if (random.random(SUB_BIOME_CHANCE) == 0 || k == 0) {
            Biome biome = WorldGenRegistries.BIOME.getByValue(i);
            if (biome == null) return;

            ResourceLocation id = WorldGenRegistries.BIOME.getKey(biome);
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
