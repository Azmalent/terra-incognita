package azmalent.terraincognita.common.inventory;

import azmalent.terraincognita.common.data.ModItemTags;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BasketStackHandler extends ItemStackHandler {
    private static final int SIZE = BasketContainer.SIZE;
    private boolean isDirty = false;

    public BasketStackHandler() {
        super(SIZE);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 0 || slot >= SIZE) {
            throw new IllegalArgumentException("Invalid slot index: " + slot);
        }

        return !stack.isEmpty() && stack.getItem().isIn(ModItemTags.BASKET_STORABLE);
    }

    public ItemStack[] getContents() {
        ItemStack[] contents = new ItemStack[SIZE];

        for (int i = 0; i < SIZE; i++) {
            contents[i] = getStackInSlot(i).copy();
        }

        return contents;
    }

    public void setContents(ItemStack[] contents) {
        if (contents == null || contents.length != SIZE) {
            throw new IllegalArgumentException("Array is null or has invalid length");
        }

        for (int i = 0; i < SIZE; i++) {
            setStackInSlot(i, contents[i].copy());
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

    public int getNumberOfEmptySlots() {
        int n = 0;
        for (int i = 0; i < SIZE; i++) {
            if (getStackInSlot(i).isEmpty()) {
                n++;
            }
        }

        return n;
    }

    public boolean isDirty() {
        boolean dirty = isDirty;
        isDirty = false;
        return dirty;
    }

    @Override
    protected void onContentsChanged(int slot) {
        isDirty = true;
    }
}

