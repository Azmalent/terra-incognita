package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.client.renderer.blockentity.TIChestWithoutLevelRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class TIChestItem extends BlockItem {
    public TIChestItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);

        TIChestItem blockItem = this;
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return TIChestWithoutLevelRenderer.getOrCreate(blockItem.getBlock());
            }
        });
    }
}
