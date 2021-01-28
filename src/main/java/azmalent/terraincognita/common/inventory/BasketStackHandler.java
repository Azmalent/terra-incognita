package azmalent.terraincognita.common.inventory;

import azmalent.terraincognita.common.data.ModItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BasketStackHandler extends ItemStackHandler {
    private static final int SIZE = BasketContainer.SIZE;

    public BasketStackHandler() {
        super(SIZE);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        validateSlotIndex(slot);
        return !stack.isEmpty() && stack.getItem().isIn(ModItemTags.BASKET_STORABLE);
    }

    public NonNullList<ItemStack> getContents() {
        return stacks;
    }

    public void setContents(NonNullList<ItemStack> contents) {
        if (contents == null || contents.size() != SIZE) {
            throw new IllegalArgumentException("List is null or has invalid size");
        }

        for (int i = 0; i < SIZE; i++) {
            setStackInSlot(i, contents.get(i).copy());
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < SIZE; i++) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}

