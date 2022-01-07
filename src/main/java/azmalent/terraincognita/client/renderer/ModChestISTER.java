package azmalent.terraincognita.client.renderer;

import azmalent.terraincognita.client.renderer.tile.ModChestRenderer;
import azmalent.terraincognita.common.block.chests.ModChestBlock;
import azmalent.terraincognita.common.tile.ModChestTileEntity;
import azmalent.terraincognita.common.tile.ModTrappedChestTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ModChestISTER<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer {
    private final Supplier<ModChestTileEntity> chestSupplier;

    public ModChestISTER(Supplier<ModChestTileEntity> chestSupplier) {
        this.chestSupplier = chestSupplier;
    }

    public static ModChestISTER<ModChestTileEntity> forNormalChest() {
        return new ModChestISTER<>(ModChestTileEntity::new);
    }

    public static ModChestISTER<ModTrappedChestTileEntity> forTrappedChest() {
        return new ModChestISTER<>(ModTrappedChestTileEntity::new);
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformTypeIn, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockItem blockItem = (BlockItem) itemStackIn.getItem();

        ModChestRenderer.inventoryBlock = (ModChestBlock) blockItem.getBlock();
        BlockEntityRenderDispatcher.instance.renderItem(chestSupplier.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        ModChestRenderer.inventoryBlock = null;
    }
}
