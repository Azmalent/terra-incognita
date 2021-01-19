package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class LotusItem extends BlockItem {
    public LotusItem(Block block) {
        super(block, new Item.Properties().group(TerraIncognita.TAB));
    }
}
