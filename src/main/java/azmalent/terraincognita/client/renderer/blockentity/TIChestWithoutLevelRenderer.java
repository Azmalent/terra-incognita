package azmalent.terraincognita.client.renderer.blockentity;

import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.common.block.woodset.chest.TIChestBlock;
import azmalent.terraincognita.common.blockentity.TIChestBlockEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TIChestWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private static final Map<Block, TIChestWithoutLevelRenderer> CACHE = Maps.newHashMap();

    private final TIChestBlockEntity chest;

    private TIChestWithoutLevelRenderer(Block chestBlock) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.chest = new TIChestBlockEntity(BlockPos.ZERO, chestBlock.defaultBlockState());
    }

    public static TIChestWithoutLevelRenderer getOrCreate(Block chestBlock) {
        return CACHE.computeIfAbsent(chestBlock, TIChestWithoutLevelRenderer::new);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        TIChestRenderer.inventoryBlock = (TIChestBlock) ItemUtil.getBlockFromItem(itemStackIn);
        blockEntityRenderDispatcher.renderItem(chest, poseStack, buffer, combinedLight, combinedOverlay);
        TIChestRenderer.inventoryBlock = null;
    }
}
