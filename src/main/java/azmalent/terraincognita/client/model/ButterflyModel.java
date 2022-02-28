package azmalent.terraincognita.client.model;

import azmalent.terraincognita.common.entity.butterfly.AbstractButterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ButterflyModel<T extends AbstractButterfly> extends EntityModel<T> {
    private final ModelPart group;
    private final ModelPart body;
    private final ModelPart antennae;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    private static final float MIN_WING_ANGLE = (float) Math.PI * 0.2f;
    private static final float MAX_WING_ANGLE = (float) Math.PI * 0.8f;

    public ButterflyModel(ModelPart root) {
        group = root.getChild("group");
        body = group.getChild("body");
        antennae = body.getChild("antennae");
        leftWing = body.getChild("leftWing");
        rightWing = body.getChild("rightWing");
    }

    @Override
    public void setupAnim(@Nonnull T butterfly, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        group.xRot = butterfly.isLanded() ? 0 : -0.2618f;

        float wingAngle = Mth.lerp(butterfly.getWingRotation(ageInTicks), MIN_WING_ANGLE, MAX_WING_ANGLE);
        leftWing.zRot = wingAngle;
        rightWing.zRot = -wingAngle;
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack matrixStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        group.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition group = root.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition body = group.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0, 0, 0));

        body.addOrReplaceChild("antennae",
                CubeListBuilder.create()
                        .texOffs(29, 12)
                        .addBox(-7.0F, -4.0F, 0.0F, 7.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(3.0F, -1.0F, -2.0F, 1.0472F, 0.0F, 0.0F));

        body.addOrReplaceChild("leftWing",
            CubeListBuilder.create()
                    .texOffs(8, 11)
                    .addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F),
            PartPose.offsetAndRotation(0.0F, -0.5F, 2.0F, 1.5708F, 0.0F, 0.7854F));

        body.addOrReplaceChild("rightWing",
                CubeListBuilder.create()
                        .texOffs(8, 11).mirror()
                        .addBox(0.0F, -7.0F, 0.0F, 0.0F, 13.0F, 8.0F),
                PartPose.offsetAndRotation(-1.0F, -0.5F, 2.0F, 1.5708F, 0.0F, -0.7854F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}
