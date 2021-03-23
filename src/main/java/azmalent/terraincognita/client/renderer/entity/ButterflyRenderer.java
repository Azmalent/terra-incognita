package azmalent.terraincognita.client.renderer.entity;

import azmalent.terraincognita.common.entity.butterfly.AbstractButterflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer<T extends AbstractButterflyEntity> extends MobRenderer<T, ButterflyRenderer.ButterflyModel<T>> {
    public ButterflyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ButterflyModel<T>(), 0.2f);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(T butterfly) {
        return butterfly.getTexture();
    }

    @Override
    protected void preRenderCallback(T butterfly, MatrixStack matrixStack, float partialTickTime) {
        float scale = butterfly.getSizeModifier();
        matrixStack.scale(scale, scale, scale);
    }

    @Override
    protected void applyRotations(T butterfly, @Nonnull MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (!butterfly.isLanded()) {
            matrixStack.translate(0, 0.1f + MathHelper.cos(ageInTicks * 0.3f) * 0.1f, 0);
        }

        super.applyRotations(butterfly, matrixStack, ageInTicks, rotationYaw, partialTicks);
    }

    @SuppressWarnings("FieldCanBeLocal")
    public static class ButterflyModel<T extends AbstractButterflyEntity> extends EntityModel<T> {
        private final ModelRenderer body;
        private final ModelRenderer antennae;
        private final ModelRenderer leftWing;
        private final ModelRenderer rightWing;

        private static final float MIN_WING_ANGLE = (float) Math.PI * 0.2f;
        private static final float MAX_WING_ANGLE = (float) Math.PI * 0.8f;

        public ButterflyModel() {
            textureWidth = 64;
            textureHeight = 64;

            body = new ModelRenderer(this, 0, 0);
            body.setRotationPoint(0.0F, 24.0F, 0.0F);
            body.setTextureOffset(0, 0).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 8.0F, 0.0F, true);

            antennae = new ModelRenderer(this, 36, 12);
            antennae.setRotationPoint(3.0F, -1.0F, -2.0F);
            body.addChild(antennae);
            setModelRotation(antennae, 1.0472F, 0.0F, 0.0F);
            antennae.setTextureOffset(29, 12).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 4.0F, 0.0F, 0.0F, false);

            leftWing = new ModelRenderer(this, 8, 11);
            leftWing.setRotationPoint(0.0F, -0.5F, 2.0F);
            body.addChild(leftWing);
            setModelRotation(leftWing, 1.5708F, 0.0F, 0.7854F);
            leftWing.addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F, 0.0F, false);

            rightWing = new ModelRenderer(this, 8, 11);
            rightWing.setRotationPoint(-1.0F, -0.5F, 2.0F);
            body.addChild(rightWing);
            setModelRotation(rightWing, 1.5708F, 0.0F, -0.7854F);
            rightWing.addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F, 0.0F, true);
        }

        @Override
        public void setRotationAngles(@Nonnull T butterfly, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
            body.rotateAngleX = butterfly.isLanded() ? 0 : -0.2618f;

            float wingAngle = MathHelper.lerp(butterfly.getWingRotation(ageInTicks), MIN_WING_ANGLE, MAX_WING_ANGLE);
            leftWing.rotateAngleZ = wingAngle;
            rightWing.rotateAngleZ = -wingAngle;
        }

        @Override
        public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            body.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        private void setModelRotation(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }
    }
}
