package azmalent.terraincognita.mixin.client;

import azmalent.terraincognita.common.block.signs.ITerraIncognitaSign;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignTileEntityRenderer.class)
public class SignRendererMixin {
    @Inject(method = "getMaterial", at = @At("HEAD"), cancellable = true)
    private static void getMaterial(Block block, CallbackInfoReturnable<RenderMaterial> cir) {
        if (block instanceof ITerraIncognitaSign) {
            ITerraIncognitaSign sign = (ITerraIncognitaSign) block;
            RenderMaterial material = new RenderMaterial(Atlases.SIGN_ATLAS, sign.getTexture());
            cir.setReturnValue(material);
        }
    }
}
