package azmalent.terraincognita.common.inventory;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.tile.BasketTileEntity;
import azmalent.terraincognita.common.registry.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    private final IUseContext useContext;

    public BasketContainer(int windowId, PlayerInventory playerInventory, BasketStackHandler stackHandler, ItemStack stack) {
        this(windowId, playerInventory, stackHandler, new IUseContext.Item(stack));
    }

    public BasketContainer(int windowId, PlayerInventory playerInventory, BasketStackHandler stackHandler, World world, BlockPos pos) {
        this(windowId, playerInventory, stackHandler, new IUseContext.Block(world, pos));
    }

    private BasketContainer(int windowId, PlayerInventory playerInventory, BasketStackHandler stackHandler, IUseContext useContext) {
        super(ModContainers.BASKET.get(), windowId);
        this.useContext = useContext;

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

    @OnlyIn(Dist.CLIENT)
    public static BasketContainer createOnClientSide(int windowId, PlayerInventory playerInventory, PacketBuffer buffer) {
        try {
            return new BasketContainer(windowId, playerInventory, new BasketStackHandler(), ItemStack.EMPTY);
        } catch (IllegalArgumentException e) {
            TerraIncognita.LOGGER.warn(e);
        }

        return null;
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
        return useContext.canInteractWith(player);
    }

    private interface IUseContext {
        boolean canInteractWith(@Nonnull PlayerEntity player);

        final class Block implements IUseContext {
            private final World world;
            private final BlockPos pos;

            public Block(World world, BlockPos pos) {
                this.world = world;
                this.pos = pos;
            }

            @Override
            public boolean canInteractWith(@Nonnull PlayerEntity player) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof BasketTileEntity) {
                    return player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64;
                }

                return false;
            }
        }

        final class Item implements IUseContext {
            private final ItemStack heldStack;

            public Item(ItemStack heldStack) {
                this.heldStack = heldStack;
            }

            @Override
            public boolean canInteractWith(@Nonnull PlayerEntity player) {
                ItemStack main = player.getHeldItemMainhand();
                ItemStack off = player.getHeldItemOffhand();
                return !main.isEmpty() && main == heldStack || !off.isEmpty() && off == heldStack;
            }
        }
    }
}
