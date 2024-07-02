package azmalent.terraincognita.common.menu;

import azmalent.terraincognita.core.ModItemTags;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasketStackHandler extends ItemStackHandler implements ICapabilityProvider {
    private static final int SIZE = BasketMenu.SIZE;

    public BasketStackHandler() {
        super(SIZE);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        validateSlotIndex(slot);
        return !stack.isEmpty() && stack.getItem().canFitInsideContainerItems() && stack.is(ModItemTags.BASKET_STORABLE);
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this));
    }
}