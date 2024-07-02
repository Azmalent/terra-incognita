package azmalent.terraincognita.common.blockentity;

import azmalent.terraincognita.core.ModItemTags;
import azmalent.terraincognita.common.menu.BasketMenu;
import azmalent.terraincognita.common.menu.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.core.registry.ModBlockEntities;
import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class BasketBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    private static final int[] SLOTS = IntStream.range(0, BasketMenu.SIZE).toArray();

    private Component customName = null;
    private BasketStackHandler stackHandler = null;

    public BasketBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BASKET.get(), pos, state);
    }

    public void readFromStack(ItemStack stack) {
        stackHandler = new BasketStackHandler();
        if (stack.getItem() == ModBlocks.BASKET.asItem()) {
            BasketStackHandler itemHandler = BasketItem.getStackHandler(stack);
            if (itemHandler != null) {
                for (int i = 0; i < BasketMenu.SIZE; i++) {
                    stackHandler.setStackInSlot(i, itemHandler.getStackInSlot(i).copy());
                }
            }

            if (stack.hasCustomHoverName()) {
                setCustomName(stack.getHoverName());
            }
        }
    }

    public ItemStack saveToStack() {
        ItemStack stack = ModBlocks.BASKET.makeStack();
        BasketStackHandler itemHandler = BasketItem.getStackHandler(stack);
        if (itemHandler != null) {
            itemHandler.setContents(stackHandler.getContents());
        }

        if (customName != null) {
            stack.setHoverName(customName);
        }

        return stack;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (stackHandler != null) {
            NonNullList<ItemStack> itemList = NonNullList.withSize(BasketMenu.SIZE, ItemStack.EMPTY);
            for (int i = 0; i < BasketMenu.SIZE; i++) {
                ItemStack stack = stackHandler.getStackInSlot(i);
                itemList.set(i, stack);
            }

            ContainerHelper.saveAllItems(tag, itemList, false);
        }

        if (customName != null) {
            if (!tag.contains("display", Tag.TAG_COMPOUND)) {
                tag.put("display", new CompoundTag());
            }

            CompoundTag display = tag.getCompound("display");
            display.putString("Name", Component.Serializer.toJson(customName));
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        stackHandler = new BasketStackHandler();
        if (tag.contains("Items", Tag.TAG_LIST)) {
            NonNullList<ItemStack> items = NonNullList.withSize(BasketMenu.SIZE, ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, items);

            stackHandler.setContents(items);
        }

        if (tag.contains("display", Tag.TAG_COMPOUND)) {
            CompoundTag display = tag.getCompound("display");
            if (display.contains("Name", Tag.TAG_STRING)) {
                String name = display.getString("Name");
                setCustomName(Component.Serializer.fromJson(name));
            }
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean stillValid(@Nonnull Player player) {
        if (level.getBlockEntity(worldPosition) != this) return false;

        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) < MAXIMUM_DISTANCE_SQ;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return customName != null ? customName : new TranslatableComponent(ModBlocks.BASKET.get().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        return new BasketMenu(i, playerInventory, stackHandler, this.level, this.worldPosition);
    }

    public void setCustomName(Component customName) {
        this.customName = customName;
    }

    //IInventory implementation
    @Override
    public int getContainerSize() {
        return stackHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return stackHandler.isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getItem(int i) {
        return stackHandler.getStackInSlot(i);
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int i, int amount) {
        return stackHandler.extractItem(i, amount, false);
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int i) {
        int limit = stackHandler.getSlotLimit(i);
        return stackHandler.extractItem(i, limit, false);
    }

    @Override
    public void setItem(int i, @Nonnull ItemStack itemStack) {
        stackHandler.setStackInSlot(i, itemStack);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            stackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    //ISidedInventory implementation
    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return stack.getItem().canFitInsideContainerItems() && stack.is(ModItemTags.BASKET_STORABLE);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        return true;
    }
}
