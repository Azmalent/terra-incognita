package azmalent.terraincognita.common.inventory;

import azmalent.cuneiform.util.ContainerUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.blockentity.BasketBlockEntity;
import azmalent.terraincognita.common.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class BasketMenu extends AbstractContainerMenu {
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

    private static final int BASKET_INVENTORY_X = PLAYER_INVENTORY_X + SLOT_OFFSET * 3;
    private static final int BASKET_INVENTORY_Y = 18;

    private final IUseContext useContext;

    public BasketMenu(int windowId, Inventory playerInventory, BasketStackHandler stackHandler, ItemStack stack) {
        this(windowId, playerInventory, stackHandler, new IUseContext.Item(stack));
    }

    public BasketMenu(int windowId, Inventory playerInventory, BasketStackHandler stackHandler, Level world, BlockPos pos) {
        this(windowId, playerInventory, stackHandler, new IUseContext.Block(world, pos));
    }

    private BasketMenu(int windowId, Inventory playerInventory, BasketStackHandler stackHandler, IUseContext useContext) {
        super(ModMenus.BASKET.get(), windowId);
        this.useContext = useContext;

        ContainerUtil.addHotbarAndInventorySlots(this, playerInventory);
        ContainerUtil.addContainerSlots(this, stackHandler, BASKET_INVENTORY_X, BASKET_INVENTORY_Y, WIDTH, HEIGHT);
    }

    @SuppressWarnings("unused")
    public static BasketMenu fromNetwork(int windowId, Inventory playerInventory, FriendlyByteBuf buffer) {
        try {
            return new BasketMenu(windowId, playerInventory, new BasketStackHandler(), ItemStack.EMPTY);
        } catch (IllegalArgumentException e) {
            TerraIncognita.LOGGER.warn("Failed to decode BasketMenu", e);
        }

        return null;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();

        if (index < VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_SLOT_COUNT, VANILLA_SLOT_COUNT + SIZE, false)){
                return ItemStack.EMPTY;
            }
        } else if (index < VANILLA_SLOT_COUNT + SIZE) {
            if (!moveItemStackTo(sourceStack, 0, VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            TerraIncognita.LOGGER.warn("Invalid slot index:" + index);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return sourceStack.copy();
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return useContext.canInteractWith(player);
    }

    private interface IUseContext {
        boolean canInteractWith(@Nonnull Player player);

        record Block(Level world, BlockPos pos) implements IUseContext {
            @Override
            public boolean canInteractWith(@Nonnull Player player) {
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof BasketBlockEntity basket) {
                    return basket.stillValid(player);
                }

                return false;
            }
        }

        record Item(ItemStack heldStack) implements IUseContext {
            @Override
            public boolean canInteractWith(@Nonnull Player player) {
                ItemStack main = player.getMainHandItem();
                ItemStack off = player.getOffhandItem();
                return !main.isEmpty() && main == heldStack || !off.isEmpty() && off == heldStack;
            }
        }
    }
}
