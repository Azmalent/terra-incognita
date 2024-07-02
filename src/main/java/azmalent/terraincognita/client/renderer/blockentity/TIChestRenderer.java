package azmalent.terraincognita.client.renderer.blockentity;

import azmalent.terraincognita.common.block.woodset.chest.TIChestBlock;
import azmalent.terraincognita.common.block.woodset.chest.TITrappedChestBlock;
import azmalent.terraincognita.common.blockentity.TIChestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

import javax.annotation.Nonnull;
import java.util.Calendar;

public class TIChestRenderer<TChest extends TIChestBlockEntity> implements BlockEntityRenderer<TChest> {
    public static TIChestBlock inventoryBlock;

    public final ModelPart lid;
    public final ModelPart bottom;
    public final ModelPart lock;
    public final ModelPart leftLid;
    public final ModelPart leftBottom;
    public final ModelPart leftLock;
    public final ModelPart rightLid;
    public final ModelPart rightBottom;
    public final ModelPart rightLock;
    public boolean isChristmas;

    public TIChestRenderer(BlockEntityRendererProvider.Context context) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
            this.isChristmas = true;
        }

        ModelPart single = context.bakeLayer(ModelLayers.CHEST);
        this.bottom = single.getChild("bottom");
        this.lid = single.getChild("lid");
        this.lock = single.getChild("lock");

        ModelPart left = context.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
        this.leftBottom = left.getChild("bottom");
        this.leftLid = left.getChild("lid");
        this.leftLock = left.getChild("lock");

        ModelPart right = context.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
        this.rightBottom = right.getChild("bottom");
        this.rightLid = right.getChild("lid");
        this.rightLock = right.getChild("lock");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void render(TChest tileEntityIn, float partialTicks, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level world = tileEntityIn.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> chest = (AbstractChestBlock) block;
            boolean flag1 = chesttype != ChestType.SINGLE;
            matrixStackIn.pushPose();
            float f = blockstate.getValue(ChestBlock.FACING).toYRot();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> icallbackwrapper;
            if (flag) {
                icallbackwrapper = chest.combine(blockstate, world, tileEntityIn.getBlockPos(), true);
            } else {
                icallbackwrapper = DoubleBlockCombiner.Combiner::acceptNone;
            }

            float f1 = icallbackwrapper.apply(ChestBlock.opennessCombiner(tileEntityIn)).get(partialTicks);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = icallbackwrapper.apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);

            Material material = getRenderMaterial(chest, chesttype);
            VertexConsumer ivertexbuilder = material.buffer(bufferIn, RenderType::entityCutoutNoCull);
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.render(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLock, this.leftBottom, f1, i, combinedOverlayIn);
                } else {
                    this.render(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLock, this.rightBottom, f1, i, combinedOverlayIn);
                }
            } else {
                this.render(matrixStackIn, ivertexbuilder, this.lid, this.lock, this.bottom, f1, i, combinedOverlayIn);
            }

            matrixStackIn.popPose();
        }
    }

    private Material getRenderMaterial(AbstractChestBlock<?> chestBlock, ChestType type) {
        if (isChristmas) {
            return switch (type) {
                case LEFT -> Sheets.CHEST_XMAS_LOCATION_LEFT;
                case RIGHT -> Sheets.CHEST_XMAS_LOCATION_RIGHT;
                default -> Sheets.CHEST_XMAS_LOCATION;
            };
        }

        TIChestBlock block = inventoryBlock != null ? inventoryBlock : (TIChestBlock) chestBlock;
        ResourceLocation texture = block.woodType.getChestTexture(type, block instanceof TITrappedChestBlock);
        return new Material(Sheets.CHEST_SHEET, texture);
    }

    public void render(PoseStack matrixStack, VertexConsumer builder, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
        chestLid.xRot = -(lidAngle * ((float)Math.PI / 2F));
        chestLatch.xRot = chestLid.xRot;
        chestLid.render(matrixStack, builder, combinedLightIn, combinedOverlayIn);
        chestLatch.render(matrixStack, builder, combinedLightIn, combinedOverlayIn);
        chestBottom.render(matrixStack, builder, combinedLightIn, combinedOverlayIn);
    }
}