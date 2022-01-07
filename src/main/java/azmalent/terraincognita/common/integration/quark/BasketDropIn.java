package azmalent.terraincognita.common.integration.quark;

import azmalent.terraincognita.common.data.ModItemTags;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import vazkii.arl.util.AbstractDropIn;

public class BasketDropIn extends AbstractDropIn {
    @Override
    public boolean canDropItemIn(Player player, ItemStack basket, ItemStack stack, Slot slot) {
        if (stack.getItem().is(ModItemTags.BASKET_STORABLE)) {
            BasketStackHandler stackHandler = BasketItem.getStackHandler(basket);
            return ItemHandlerHelper.insertItemStacked(stackHandler, stack.copy(), true).getCount() < stack.getCount();
        }

        return false;
    }

    @Override
    public ItemStack dropItemIn(Player player, ItemStack basket, ItemStack stack, Slot slot) {
        BasketStackHandler stackHandler = BasketItem.getStackHandler(basket);
        ItemStack remainingStack = ItemHandlerHelper.insertItemStacked(stackHandler, stack.copy(), false);
        stack.setCount(remainingStack.getCount());

        return basket;
    }
}
