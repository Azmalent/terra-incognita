package azmalent.terraincognita.client.renderer.entity;

import azmalent.terraincognita.client.ClientHandler;
import azmalent.terraincognita.common.entity.butterfly.AbstractButterflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer<T extends AbstractButterflyEntity> extends MobRenderer<T, ButterflyRenderer.ButterflyModel<T>> {
    public ButterflyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ButterflyModel<T>(), 0.1f);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(T butterfly) {
        return butterfly.getTexture();
    }

    @Override
    protected void preRenderCallback(T butterfly, MatrixStack matrixStack, float partialTickTime) {
        matrixStack.scale(0.7f, 0.7f, 0.7f);
    }

    @Override
    protected void applyRotations(T entity, @Nonnull MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (!entity.isLanded()) {
            matrixStack.translate(0.0D, MathHelper.cos(ageInTicks * 0.3F) * 0.1F, 0.0D);
        }

        super.applyRotations(entity, matrixStack, ageInTicks, rotationYaw, partialTicks);
    }

    public static class ButterflyModel<T extends AbstractButterflyEntity> extends EntityModel<T> {
        private final ModelRenderer body;
        private final ModelRenderer antennae;
        private final ModelRenderer leftWing;
        private final ModelRenderer rightWing;

        private static final float MIN_WING_ANGLE = (float) Math.PI * 0.2f;
        private static final float MAX_WING_ANGLE = (float) Math.PI * 0.8f;
        private float wingRotation = 0;
        private float targetWingRotation = 0;

        public ButterflyModel() {
            textureWidth = 64;
            textureHeight = 64;

            body = new ModelRenderer(this);
            body.setRotationPoint(0.0F, 24.0F, 0.0F);
            body.setTextureOffset(0, 0).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 8.0F, 0.0F, true);

            antennae = new ModelRenderer(this);
            antennae.setRotationPoint(3.0F, -1.0F, -2.0F);
            body.addChild(antennae);
            setRotationAngle(antennae, 1.0472F, 0.0F, 0.0F);
            antennae.setTextureOffset(36, 12).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 4.0F, 0.0F, 0.0F, false);

            leftWing = new ModelRenderer(this);
            leftWing.setRotationPoint(-1.0F, -0.5F, 2.0F);
            body.addChild(leftWing);
            setRotationAngle(leftWing, 1.5708F, 0.0F, -0.2618F);
            leftWing.setTextureOffset(8, 11).addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F, 0.0F, false);

            rightWing = new ModelRenderer(this);
            rightWing.setRotationPoint(0.0F, -0.5F, 2.0F);
            body.addChild(rightWing);
            setRotationAngle(rightWing, 1.5708F, 0.0F, 0.2618F);
            rightWing.setTextureOffset(8, 11).addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F, 0.0F, true);
        }

        @Override
        public void setRotationAngles(@Nonnull T butterfly, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
            if (butterfly.isLanded()) {
                if (!ClientHandler.getWorld().isNightTime() && butterfly.getRNG().nextInt(80) == 0) {
                    targetWingRotation = butterfly.getRNG().nextFloat();
                }

                wingRotation = MathHelper.lerp(0.05f, wingRotation, targetWingRotation);
            } else {
                wingRotation = MathHelper.abs(MathHelper.cos(ageInTicks / 1.5f));
                targetWingRotation = wingRotation;
            }

            float wingAngle = MathHelper.lerp(wingRotation, MIN_WING_ANGLE, MAX_WING_ANGLE);
            leftWing.rotateAngleZ = -wingAngle;
            rightWing.rotateAngleZ = wingAngle;
        }

        @Override
        public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            body.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }


    }
}
