package azmalent.terraincognita.client.renderer.entity.model;

import azmalent.terraincognita.common.entity.AbstractButterflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ButterflyModel<T extends AbstractButterflyEntity> extends EntityModel<T> {
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
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        if (!entity.isLanded()) {
            float wingAngle = MathHelper.cos(ageInTicks * 2) * (float) Math.PI * 0.4f;

            leftWing.rotateAngleZ = -wingAngle;
            rightWing.rotateAngleZ = wingAngle;
        }
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
