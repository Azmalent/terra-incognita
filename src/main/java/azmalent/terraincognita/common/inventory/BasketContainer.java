package azmalent.terraincognita.common.inventory;

import azmalent.terraincognita.common.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;

public class BasketContainer extends Container {
    private final IInventory inventory = new Inventory(3 * 3);

    public class BasketSlot extends Slot {
        public BasketSlot(IInventory inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem().isIn(ItemTags.SMALL_FLOWERS);
        }
    }

    public BasketContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory);
    }

    public BasketContainer(int id, PlayerInventory playerInventory) {
        super(ModContainers.BASKET.get(), id);

        inventory.openInventory(playerInventory.player);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                addSlot(new BasketSlot(inventory, y * 3 + x, x * 18, y * 18));
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index)
    {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (index < 9) {
                if(!this.mergeItemStack(stack, 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(stack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if(stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return copy;
    }

    @Override
    public void onContainerClosed(PlayerEntity player)
    {
        super.onContainerClosed(player);
        this.inventory.closeInventory(player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return inventory.isUsableByPlayer(player);
    }
}
