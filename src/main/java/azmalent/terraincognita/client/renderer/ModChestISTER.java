package azmalent.terraincognita.client.renderer;

import azmalent.terraincognita.client.renderer.tile.ModChestRenderer;
import azmalent.terraincognita.common.block.chests.ModChestBlock;
import azmalent.terraincognita.common.tile.ModChestTileEntity;
import azmalent.terraincognita.common.tile.ModTrappedChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ModChestISTER<T extends TileEntity> extends ItemStackTileEntityRenderer {
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
    public void func_239207_a_(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockItem blockItem = (BlockItem) itemStackIn.getItem();

        ModChestRenderer.inventoryBlock = (ModChestBlock) blockItem.getBlock();
        TileEntityRendererDispatcher.instance.renderItem(chestSupplier.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        ModChestRenderer.inventoryBlock = null;
    }
}
