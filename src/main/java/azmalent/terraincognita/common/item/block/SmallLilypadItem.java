package azmalent.terraincognita.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.LilyPadItem;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class SmallLilypadItem extends LilyPadItem {
    public SmallLilypadItem(Block block) {
        super(block, (new Properties()).group(ItemGroup.DECORATIONS));
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().getBlockState(context.getPos()).getBlock() == this.getBlock()) {
            return tryPlace(new BlockItemUseContext(context));
        }

        return super.onItemUse(context);
    }
}
