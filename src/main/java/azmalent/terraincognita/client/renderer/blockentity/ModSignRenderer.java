package azmalent.terraincognita.client.renderer.blockentity;

import azmalent.terraincognita.common.block.signs.AbstractModSignBlock;
import azmalent.terraincognita.common.block.signs.ModStandingSignBlock;
import azmalent.terraincognita.common.block.signs.ModWallSignBlock;
import azmalent.terraincognita.common.tile.ModSignBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ModSignRenderer implements BlockEntityRenderer<ModSignBlockEntity> {
    /** The ModelSign instance for use in this renderer */
    private final SignModel model = new SignModel();

    public ModSignRenderer(BlockEntityRendererProvider.Context context) {

    }

    public void render(ModSignBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = tileEntityIn.getBlockState();
        matrixStackIn.pushPose();
        float f = 0.6666667F;
        if (blockstate.getBlock() instanceof ModStandingSignBlock) {
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            float f1 = -((float)(blockstate.getValue(ModStandingSignBlock.ROTATION) * 360) / 16.0F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
            this.model.signStick.visible = true;
        } else {
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            float f4 = -blockstate.getValue(ModWallSignBlock.FACING).toYRot();
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f4));
            matrixStackIn.translate(0.0D, -0.3125D, -0.4375D);
            this.model.signStick.visible = false;
        }

        matrixStackIn.pushPose();
        matrixStackIn.scale(0.6666667F, -0.6666667F, -0.6666667F);
        Material rendermaterial = getMaterial(blockstate.getBlock());
        VertexConsumer ivertexbuilder = rendermaterial.buffer(bufferIn, this.model::renderType);
        this.model.signBoard.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.model.signStick.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();

        Font fontrenderer = this.renderer.getFont();
        float scale = 0.010416667F;
        matrixStackIn.translate(0.0D, 0.33333334F, 0.046666667F);
        matrixStackIn.scale(scale, -scale, scale);

        int i = tileEntityIn.getTextColor().getTextColor();
        double multiplier = 0.4D;
        int j = (int)((double)NativeImage.getR(i) * multiplier);
        int k = (int)((double)NativeImage.getG(i) * multiplier);
        int l = (int)((double)NativeImage.getB(i) * multiplier);
        int i1 = NativeImage.combine(0, l, k, j);

        for(int k1 = 0; k1 < 4; ++k1) {
            FormattedCharSequence ireorderingprocessor = tileEntityIn.getRenderMessage(k1, (p_243502_1_) -> {
                List<FormattedCharSequence> list = fontrenderer.split(p_243502_1_, 90);
                return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
            });
            if (ireorderingprocessor != null) {
                float f3 = (float)(-fontrenderer.width(ireorderingprocessor) / 2);
                fontrenderer.drawInBatch(ireorderingprocessor, f3, (float)(k1 * 10 - 20), i1, false, matrixStackIn.last().pose(), bufferIn, false, 0, tileEntityIn.isGlowing() ? 15728880 : combinedLightIn);
            }
        }

        matrixStackIn.popPose();
    }

    public static Material getMaterial(Block blockIn) {
        if (blockIn instanceof AbstractModSignBlock) {
            ResourceLocation texture = ((AbstractModSignBlock) blockIn).getTexture();
            return new Material(Sheets.SIGN_SHEET, texture);
        }

        return Sheets.signTexture(WoodType.OAK);
    }

    @OnlyIn(Dist.CLIENT)
    public static final class SignModel extends Model {
        public final ModelPart signBoard = new ModelPart(64, 32, 0, 0);
        public final ModelPart signStick;

        public SignModel() {
            super(RenderType::entityCutoutNoCull);
            this.signBoard.addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
            this.signStick = new ModelPart(64, 32, 0, 14);
            this.signStick.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
        }

        public void renderToBuffer(@Nonnull PoseStack p_225598_1_, @Nonnull VertexConsumer p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
            this.signBoard.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
            this.signStick.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        }
    }
}
