package azmalent.terraincognita.mixin.client;

import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.blocksets.TIWoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Atlases.class)
public class AtlasesMixin {
    @Inject(method = "collectAllMaterials", at = @At("RETURN"))
    private static void collectAllMaterials(Consumer<RenderMaterial> consumer, CallbackInfo ci) {
        ModBlocks.WoodTypes.VALUES.forEach((woodType) -> {
            consumer.accept(new RenderMaterial(Atlases.SIGN_ATLAS, woodType.SIGN_TEXTURE));
        });
    }
}
