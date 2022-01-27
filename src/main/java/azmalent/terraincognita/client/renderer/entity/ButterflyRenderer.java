package azmalent.terraincognita.client.renderer.entity;

import azmalent.terraincognita.common.entity.butterfly.AbstractButterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer<T extends AbstractButterfly> extends MobRenderer<T, ButterflyRenderer.ButterflyModel<T>> {
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel<T>(), 0.2f);
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

    @SuppressWarnings("FieldCanBeLocal")
    public static class ButterflyModel<T extends AbstractButterfly> extends EntityModel<T> {
        private final ModelPart body;
        private final ModelPart antennae;
        private final ModelPart leftWing;
        private final ModelPart rightWing;

        private static final float MIN_WING_ANGLE = (float) Math.PI * 0.2f;
        private static final float MAX_WING_ANGLE = (float) Math.PI * 0.8f;

        public ButterflyModel() {
            texWidth = 64;
            texHeight = 64;

            body = new ModelPart(this, 0, 0);
            body.setPos(0.0F, 24.0F, 0.0F);
            body.texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 8.0F, 0.0F, true);

            antennae = new ModelPart(this, 36, 12);
            antennae.setPos(3.0F, -1.0F, -2.0F);
            body.addChild(antennae);
            setModelRotation(antennae, 1.0472F, 0.0F, 0.0F);
            antennae.texOffs(29, 12).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 4.0F, 0.0F, 0.0F, false);

            leftWing = new ModelPart(this, 8, 11);
            leftWing.setPos(0.0F, -0.5F, 2.0F);
            body.addChild(leftWing);
            setModelRotation(leftWing, 1.5708F, 0.0F, 0.7854F);
            leftWing.addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F, 0.0F, false);

            rightWing = new ModelPart(this, 8, 11);
            rightWing.setPos(-1.0F, -0.5F, 2.0F);
            body.addChild(rightWing);
            setModelRotation(rightWing, 1.5708F, 0.0F, -0.7854F);
            rightWing.addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F, 0.0F, true);
        }

        @Override
        public void setupAnim(@Nonnull T butterfly, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
            body.xRot = butterfly.isLanded() ? 0 : -0.2618f;

            float wingAngle = Mth.lerp(butterfly.getWingRotation(ageInTicks), MIN_WING_ANGLE, MAX_WING_ANGLE);
            leftWing.zRot = wingAngle;
            rightWing.zRot = -wingAngle;
        }

        @Override
        public void renderToBuffer(@Nonnull PoseStack matrixStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            body.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        private void setModelRotation(ModelPart modelRenderer, float x, float y, float z) {
            modelRenderer.xRot = x;
            modelRenderer.yRot = y;
            modelRenderer.zRot = z;
        }
    }
}
