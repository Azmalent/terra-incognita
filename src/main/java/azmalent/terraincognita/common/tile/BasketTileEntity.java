package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.data.ModItemTags;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModTileEntities;
import azmalent.terraincognita.common.inventory.BasketContainer;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class BasketTileEntity extends TileEntity implements INamedContainerProvider, ISidedInventory {
    private static final int[] SLOTS = IntStream.range(0, BasketContainer.SIZE).toArray();

    private ITextComponent customName = null;
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

            if (stack.hasDisplayName()) {
                setCustomName(stack.getDisplayName());
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
            stack.setDisplayName(customName);
        }

        return stack;
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        if (stackHandler != null) {
            NonNullList<ItemStack> itemList = NonNullList.withSize(BasketContainer.SIZE, ItemStack.EMPTY);
            for (int i = 0; i < BasketContainer.SIZE; i++) {
                ItemStack stack = stackHandler.getStackInSlot(i);
                itemList.set(i, stack);
            }

            ItemStackHelper.saveAllItems(tag, itemList, false);
        }

        if (customName != null) {
            if (!tag.contains("display", Constants.NBT.TAG_COMPOUND)) {
                tag.put("display", new CompoundNBT());
            }

            CompoundNBT display = tag.getCompound("display");
            display.putString("Name", ITextComponent.Serializer.toJson(customName));
        }

        return tag;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);

        stackHandler = new BasketStackHandler();
        if (tag.contains("Items", Constants.NBT.TAG_LIST)) {
            NonNullList<ItemStack> items = NonNullList.withSize(BasketContainer.SIZE, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(tag, items);

            stackHandler.setContents(items);
        }

        if (tag.contains("display", Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT display = tag.getCompound("display");
            if (display.contains("Name", Constants.NBT.TAG_STRING)) {
                String name = display.getString("Name");
                setCustomName(ITextComponent.Serializer.getComponentFromJson(name));
            }
        }
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        if (world.getTileEntity(pos) != this) return false;

        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < MAXIMUM_DISTANCE_SQ;
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return customName != null ? customName : new TranslationTextComponent(ModBlocks.BASKET.getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        return new BasketContainer(i, playerInventory, stackHandler, this);
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    //IInventory implementation
    @Override
    public int getSizeInventory() {
        return stackHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return stackHandler.isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int i) {
        return stackHandler.getStackInSlot(i);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int i, int amount) {
        return stackHandler.extractItem(i, amount, false);
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int i) {
        int limit = stackHandler.getSlotLimit(i);
        return stackHandler.extractItem(i, limit, false);
    }

    @Override
    public void setInventorySlotContents(int i, @Nonnull ItemStack itemStack) {
        stackHandler.setStackInSlot(i, itemStack);
    }

    @Override
    public void clear() {
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
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return itemStackIn.getItem().isIn(ModItemTags.BASKET_STORABLE);
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        return true;
    }
}
