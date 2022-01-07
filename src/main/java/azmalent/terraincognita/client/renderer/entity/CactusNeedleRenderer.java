package azmalent.terraincognita.client.renderer.entity;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.CactusNeedleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

//TODO
@OnlyIn(Dist.CLIENT)
public class CactusNeedleRenderer extends EntityRenderer<CactusNeedleEntity> {
    public static final ResourceLocation TEXTURE = TerraIncognita.prefix("textures/entity/projectile/cactus_needle.png");

    public CactusNeedleRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(CactusNeedleEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));

        float shake = (float)entityIn.arrowShake - partialTicks;
        if (shake > 0.0F) {
            float f10 = -Mth.sin(shake * 3.0F) * shake;
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f10));
        }

        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(45.0F));
        matrixStackIn.scale(0.05625F, 0.05625F, 0.05625F);
        matrixStackIn.translate(-4.0D, 0.0D, 0.0D);
        VertexConsumer builder = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        this.drawVertex(matrix4f, matrix3f, builder, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

        for(int j = 0; j < 4; ++j) {
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            this.drawVertex(matrix4f, matrix3f, builder, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, builder, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, builder, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, builder, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
        }

        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull CactusNeedleEntity entity) {
        return TEXTURE;
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, int offsetX, int offsetY, int offsetZ, float textureX, float textureY, int p_229039_9_, int p_229039_10_, int p_229039_11_, int packedLightIn) {
        vertexBuilder.vertex(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).color(255, 255, 255, 255).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).endVertex();
    }
}
