package azmalent.terraincognita.client.renderer.entity;

import azmalent.terraincognita.common.entity.AbstractButterflyEntity;
import azmalent.terraincognita.common.entity.ButterflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static azmalent.terraincognita.client.renderer.entity.ButterflyRenderer.*;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer<T extends AbstractButterflyEntity> extends MobRenderer<T, ButterflyModel<T>> {
    public ButterflyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ButterflyModel<T>(), 0.2f);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return entity.getTexture();
    }

    public static class ButterflyModel<T extends AbstractButterflyEntity> extends EntityModel<T> {
        private final ModelRenderer body;
        private final ModelRenderer antennae;
        private final ModelRenderer leftWing;
        private final ModelRenderer rightWing;

        public ButterflyModel() {
            textureWidth = 16;
            textureHeight = 16;

            body = new ModelRenderer(this);
            body.setRotationPoint(0.0F, 24.0F, 0.0F);
            body.setTextureOffset(0, 0).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, true);

            antennae = new ModelRenderer(this);
            antennae.setRotationPoint(1.0F, -1.0F, -2.0F);
            body.addChild(antennae);
            setRotationAngle(antennae, 0.7854F, 0.0F, 0.0F);
            antennae.setTextureOffset(10, 6).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, false);

            leftWing = new ModelRenderer(this);
            leftWing.setRotationPoint(-1.0F, -0.5F, 1.0F);
            body.addChild(leftWing);
            setRotationAngle(leftWing, 1.5708F, 0.0F, -0.7854F);
            leftWing.setTextureOffset(0, 0).addBox(0.0F, -6.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, false);

            rightWing = new ModelRenderer(this);
            rightWing.setRotationPoint(0.0F, -0.5F, 2.0F);
            body.addChild(rightWing);
            setRotationAngle(rightWing, 1.5708F, 0.0F, 0.7854F);
            rightWing.setTextureOffset(0, 0).addBox(0.0F, -7.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, true);
        }

        @Override
        public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
            //previously the render function, render code was moved to a method below
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            body.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }
    }
}
