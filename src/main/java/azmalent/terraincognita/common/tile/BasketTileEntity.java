package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.data.ModItemTags;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModTileEntities;
import azmalent.terraincognita.common.inventory.BasketContainer;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class BasketTileEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    private static final int[] SLOTS = IntStream.range(0, BasketContainer.SIZE).toArray();

    private Component customName = null;
    private BasketStackHandler stackHandler = null;

    public BasketTileEntity() {
        super(ModTileEntities.BASKET.get());
    }

    public void readFromStack(ItemStack stack) {
        stackHandler = new BasketStackHandler();
        if (stack.getItem() == ModBlocks.BASKET.getItem()) {
            BasketStackHandler itemHandler = BasketItem.getStackHandler(stack);
            if (itemHandler != null) {
                for (int i = 0; i < BasketContainer.SIZE; i++) {
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

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag tag) {
        super.save(tag);
        if (stackHandler != null) {
            NonNullList<ItemStack> itemList = NonNullList.withSize(BasketContainer.SIZE, ItemStack.EMPTY);
            for (int i = 0; i < BasketContainer.SIZE; i++) {
                ItemStack stack = stackHandler.getStackInSlot(i);
                itemList.set(i, stack);
            }

            ContainerHelper.saveAllItems(tag, itemList, false);
        }

        if (customName != null) {
            if (!tag.contains("display", Constants.NBT.TAG_COMPOUND)) {
                tag.put("display", new CompoundTag());
            }

            CompoundTag display = tag.getCompound("display");
            display.putString("Name", Component.Serializer.toJson(customName));
        }

        return tag;
    }

    @Override
    public void load(BlockState state, CompoundTag tag) {
        super.load(state, tag);

        stackHandler = new BasketStackHandler();
        if (tag.contains("Items", Constants.NBT.TAG_LIST)) {
            NonNullList<ItemStack> items = NonNullList.withSize(BasketContainer.SIZE, ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, items);

            stackHandler.setContents(items);
        }

        if (tag.contains("display", Constants.NBT.TAG_COMPOUND)) {
            CompoundTag display = tag.getCompound("display");
            if (display.contains("Name", Constants.NBT.TAG_STRING)) {
                String name = display.getString("Name");
                setCustomName(Component.Serializer.fromJson(name));
            }
        }
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        if (level.getBlockEntity(worldPosition) != this) return false;

        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) < MAXIMUM_DISTANCE_SQ;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return customName != null ? customName : new TranslatableComponent(ModBlocks.BASKET.getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        return new BasketContainer(i, playerInventory, stackHandler, this.level, this.worldPosition);
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
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return itemStackIn.getItem().is(ModItemTags.BASKET_STORABLE);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        return true;
    }
}
