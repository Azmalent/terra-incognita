package azmalent.terraincognita.client.renderer;

import azmalent.cuneiform.lib.util.ItemUtil;
import azmalent.terraincognita.client.renderer.blockentity.ModChestRenderer;
import azmalent.terraincognita.common.block.chest.ModChestBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
public class ModChestInventoryRenderer<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer {
    private final T chest;

    public ModChestInventoryRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet, T chest) {
        super(dispatcher, modelSet);
        this.chest = chest;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ModChestRenderer.inventoryBlock = (ModChestBlock) ItemUtil.getBlockFromItem(itemStackIn);
        blockEntityRenderDispatcher.renderItem(chest, poseStack, buffer, combinedLight, combinedOverlay);
        ModChestRenderer.inventoryBlock = null;
    }
}
