package azmalent.terraincognita.common.inventory;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.tile.BasketTileEntity;
import azmalent.terraincognita.common.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BasketContainer extends Container {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;
    public static final int SIZE = WIDTH * HEIGHT;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_HEIGHT = 3;
    private static final int PLAYER_INVENTORY_WIDTH = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_WIDTH * PLAYER_INVENTORY_HEIGHT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int SLOT_OFFSET = 18;

    private static final int PLAYER_INVENTORY_X = 8;
    private static final int PLAYER_INVENTORY_Y = 84;
    private static final int HOTBAR_Y = 142;

    private static final int BASKET_INVENTORY_X = PLAYER_INVENTORY_X + SLOT_OFFSET * 3;
    private static final int BASKET_INVENTORY_Y = 18;

    private final ItemStack heldStack;

    public BasketContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory, new BasketStackHandler(), ItemStack.EMPTY);
    }

    public BasketContainer(int id, PlayerInventory playerInventory, BasketStackHandler stackHandler, BasketTileEntity te) {
        this(id, playerInventory, stackHandler, ItemStack.EMPTY);
    }

    public BasketContainer(int id, PlayerInventory playerInventory, BasketStackHandler stackHandler, ItemStack heldStack) {
        super(ModContainers.BASKET.get(), id);
        this.heldStack = heldStack;

        // Add the players hotbar to the gui - the [xpos, ypos] location of each item
        for (int i = 0; i < HOTBAR_SLOT_COUNT; i++) {
            addSlot(new Slot(playerInventory, i, PLAYER_INVENTORY_X + SLOT_OFFSET * i, HOTBAR_Y));
        }

        // Add the rest of the player's inventory to the gui
        for (int y = 0; y < PLAYER_INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_WIDTH; x++) {
                int slotIndex = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_WIDTH + x;
                int xpos = PLAYER_INVENTORY_X + x * SLOT_OFFSET;
                int ypos = PLAYER_INVENTORY_Y + y * SLOT_OFFSET;
                addSlot(new Slot(playerInventory, slotIndex, xpos, ypos));
            }
        }

        // Add the tile inventory container to the gui
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                int x = BASKET_INVENTORY_X + col * SLOT_OFFSET;
                int y = BASKET_INVENTORY_Y + row * SLOT_OFFSET;
                addSlot(new SlotItemHandler(stackHandler, row * HEIGHT + col, x, y));
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        Slot sourceSlot = inventorySlots.get(index);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getStack();

        if (index < VANILLA_SLOT_COUNT) {
            if (!mergeItemStack(sourceStack, VANILLA_SLOT_COUNT, VANILLA_SLOT_COUNT + SIZE, false)){
                return ItemStack.EMPTY;
            }
        } else if (index < VANILLA_SLOT_COUNT + SIZE) {
            if (!mergeItemStack(sourceStack, 0, VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            TerraIncognita.LOGGER.warn("Invalid slot index:" + index);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return sourceStack.copy();
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity player) {
        return heldStack.isEmpty() || player.getHeldItemMainhand() == heldStack || player.getHeldItemOffhand() == heldStack;
    }
}
