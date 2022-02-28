package azmalent.terraincognita.client.renderer.entity;

import azmalent.terraincognita.client.model.ButterflyModel;
import azmalent.terraincognita.client.model.ModelHandler;
import azmalent.terraincognita.common.entity.butterfly.AbstractButterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer<T extends AbstractButterfly> extends MobRenderer<T, ButterflyModel<T>> {
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, ModelHandler.createModel(ModelHandler.BUTTERFLY), 0.2f);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(T butterfly) {
        return butterfly.getTexture();
    }

    @Override
    protected void scale(T butterfly, PoseStack matrixStack, float partialTickTime) {
        float scale = butterfly.getSizeModifier();
        matrixStack.scale(scale, scale, scale);
    }

    @Override
    protected void setupRotations(T butterfly, @Nonnull PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (!butterfly.isLanded()) {
            matrixStack.translate(0, 0.1f + Mth.cos(ageInTicks * 0.3f) * 0.1f, 0);
        }

        super.setupRotations(butterfly, matrixStack, ageInTicks, rotationYaw, partialTicks);
    }
}
