package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.LilyPadItem;
import net.minecraft.util.ActionResultType;

public class SmallLilypadItem extends LilyPadItem {
    public SmallLilypadItem(Block block) {
        super(block, (new Properties()).group(TerraIncognita.TAB));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().getBlockState(context.getPos()).getBlock() == this.getBlock()) {
            return tryPlace(new BlockItemUseContext(context));
        }

        return super.onItemUse(context);
    }
}
