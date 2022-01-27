package azmalent.terraincognita.client.renderer;

import azmalent.terraincognita.client.renderer.blockentity.ModChestRenderer;
import azmalent.terraincognita.common.block.chests.ModChestBlock;
import azmalent.terraincognita.common.tile.ModChestBlockEntity;
import azmalent.terraincognita.common.tile.ModTrappedChestBlockEntity;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ModChestItemRenderer<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer {
    private final Supplier<ModChestBlockEntity> chestSupplier;

    public ModChestItemRenderer(Supplier<ModChestBlockEntity> chestSupplier) {
        this.chestSupplier = chestSupplier;
    }

    public static ModChestItemRenderer<ModChestBlockEntity> forNormalChest() {
        return new ModChestItemRenderer<>(ModChestBlockEntity::new);
    }

    public static ModChestItemRenderer<ModTrappedChestBlockEntity> forTrappedChest() {
        return new ModChestItemRenderer<>(ModTrappedChestBlockEntity::new);
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.@NotNull TransformType transformTypeIn, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockItem blockItem = (BlockItem) itemStackIn.getItem();

        ModChestRenderer.inventoryBlock = (ModChestBlock) blockItem.getBlock();
        BlockEntityRenderDispatcher.instance.renderItem(chestSupplier.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        ModChestRenderer.inventoryBlock = null;
    }
}
