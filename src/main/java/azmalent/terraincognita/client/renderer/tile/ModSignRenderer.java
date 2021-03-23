package azmalent.terraincognita.client.renderer.tile;

import azmalent.terraincognita.common.block.signs.AbstractModSignBlock;
import azmalent.terraincognita.common.block.signs.ModStandingSignBlock;
import azmalent.terraincognita.common.block.signs.ModWallSignBlock;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ModSignRenderer extends TileEntityRenderer<ModSignTileEntity> {
    /** The ModelSign instance for use in this renderer */
    private final SignModel model = new SignModel();

    public ModSignRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    public void render(ModSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = tileEntityIn.getBlockState();
        matrixStackIn.push();
        float f = 0.6666667F;
        if (blockstate.getBlock() instanceof ModStandingSignBlock) {
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            float f1 = -((float)(blockstate.get(ModStandingSignBlock.ROTATION) * 360) / 16.0F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
            this.model.signStick.showModel = true;
        } else {
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            float f4 = -blockstate.get(ModWallSignBlock.FACING).getHorizontalAngle();
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f4));
            matrixStackIn.translate(0.0D, -0.3125D, -0.4375D);
            this.model.signStick.showModel = false;
        }

        matrixStackIn.push();
        matrixStackIn.scale(0.6666667F, -0.6666667F, -0.6666667F);
        RenderMaterial rendermaterial = getMaterial(blockstate.getBlock());
        IVertexBuilder ivertexbuilder = rendermaterial.getBuffer(bufferIn, this.model::getRenderType);
        this.model.signBoard.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.model.signStick.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();

        FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
        float scale = 0.010416667F;
        matrixStackIn.translate(0.0D, 0.33333334F, 0.046666667F);
        matrixStackIn.scale(scale, -scale, scale);

        int i = tileEntityIn.getTextColor().getTextColor();
        double multiplier = 0.4D;
        int j = (int)((double)NativeImage.getRed(i) * multiplier);
        int k = (int)((double)NativeImage.getGreen(i) * multiplier);
        int l = (int)((double)NativeImage.getBlue(i) * multiplier);
        int i1 = NativeImage.getCombined(0, l, k, j);

        for(int k1 = 0; k1 < 4; ++k1) {
            IReorderingProcessor ireorderingprocessor = tileEntityIn.func_242686_a(k1, (p_243502_1_) -> {
                List<IReorderingProcessor> list = fontrenderer.trimStringToWidth(p_243502_1_, 90);
                return list.isEmpty() ? IReorderingProcessor.field_242232_a : list.get(0);
            });
            if (ireorderingprocessor != null) {
                float f3 = (float)(-fontrenderer.func_243245_a(ireorderingprocessor) / 2);
                fontrenderer.func_238416_a_(ireorderingprocessor, f3, (float)(k1 * 10 - 20), i1, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, tileEntityIn.isGlowing() ? 15728880 : combinedLightIn);
            }
        }

        matrixStackIn.pop();
    }

    public static RenderMaterial getMaterial(Block blockIn) {
        if (blockIn instanceof AbstractModSignBlock) {
            ResourceLocation texture = ((AbstractModSignBlock) blockIn).getTexture();
            return new RenderMaterial(Atlases.SIGN_ATLAS, texture);
        }

        return Atlases.getSignMaterial(WoodType.OAK);
    }

    @OnlyIn(Dist.CLIENT)
    public static final class SignModel extends Model {
        public final ModelRenderer signBoard = new ModelRenderer(64, 32, 0, 0);
        public final ModelRenderer signStick;

        public SignModel() {
            super(RenderType::getEntityCutoutNoCull);
            this.signBoard.addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
            this.signStick = new ModelRenderer(64, 32, 0, 14);
            this.signStick.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
        }

        public void render(@Nonnull MatrixStack p_225598_1_, @Nonnull IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
            this.signBoard.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
            this.signStick.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        }
    }
}
