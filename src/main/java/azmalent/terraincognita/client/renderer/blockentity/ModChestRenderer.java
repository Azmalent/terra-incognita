package azmalent.terraincognita.client.renderer.blockentity;

import azmalent.terraincognita.common.block.chests.ModChestBlock;
import azmalent.terraincognita.common.block.chests.ModTrappedChestBlock;
import azmalent.terraincognita.common.tile.ModChestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.block.*;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Calendar;

import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModChestRenderer<TChest extends ModChestBlockEntity> extends BlockEntityRenderer<TChest> {
    public static ModChestBlock inventoryBlock;

    public final ModelPart singleLid;
    public final ModelPart singleBottom;
    public final ModelPart singleLatch;
    public final ModelPart rightLid;
    public final ModelPart rightBottom;
    public final ModelPart rightLatch;
    public final ModelPart leftLid;
    public final ModelPart leftBottom;
    public final ModelPart leftLatch;
    public boolean isChristmas;

    public ModChestRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
            this.isChristmas = true;
        }

        this.singleBottom = new ModelPart(64, 64, 0, 19);
        this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.singleLid = new ModelPart(64, 64, 0, 0);
        this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.singleLid.y = 9.0F;
        this.singleLid.z = 1.0F;
        this.singleLatch = new ModelPart(64, 64, 0, 0);
        this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.singleLatch.y = 8.0F;
        this.rightBottom = new ModelPart(64, 64, 0, 19);
        this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.rightLid = new ModelPart(64, 64, 0, 0);
        this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.rightLid.y = 9.0F;
        this.rightLid.z = 1.0F;
        this.rightLatch = new ModelPart(64, 64, 0, 0);
        this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.rightLatch.y = 8.0F;
        this.leftBottom = new ModelPart(64, 64, 0, 19);
        this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.leftLid = new ModelPart(64, 64, 0, 0);
        this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.leftLid.y = 9.0F;
        this.leftLid.z = 1.0F;
        this.leftLatch = new ModelPart(64, 64, 0, 0);
        this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.leftLatch.y = 8.0F;
    }

    public ModChestRenderer(BlockEntityRendererProvider.Context context) {

    }

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
                    this.render(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, combinedOverlayIn);
                } else {
                    this.render(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, combinedOverlayIn);
                }
            } else {
                this.render(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlayIn);
            }

            matrixStackIn.popPose();
        }
    }

    private Material getRenderMaterial(AbstractChestBlock<?> chestBlock, ChestType type) {
        if (isChristmas) {
            switch (type) {
                case LEFT:  return Sheets.CHEST_XMAS_LOCATION_LEFT;
                case RIGHT: return Sheets.CHEST_XMAS_LOCATION_RIGHT;
                default:    return Sheets.CHEST_XMAS_LOCATION;
            }
        }

        ModChestBlock block = inventoryBlock != null ? inventoryBlock : (ModChestBlock) chestBlock;
        ResourceLocation texture = block.woodType.getChestTexture(type, block instanceof ModTrappedChestBlock);
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