package azmalent.terraincognita.common.item.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraft.world.InteractionResult;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class SmallLilypadItem extends WaterLilyBlockItem {
    public SmallLilypadItem(Block block) {
        super(block, (new Properties()).tab(CreativeModeTab.TAB_DECORATIONS));
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() == this.getBlock()) {
            return place(new BlockPlaceContext(context));
        }

        return super.useOn(context);
    }
}
