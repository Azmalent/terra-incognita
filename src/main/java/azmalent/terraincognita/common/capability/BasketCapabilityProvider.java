package azmalent.terraincognita.common.capability;

import azmalent.terraincognita.common.inventory.BasketStackHandler;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasketCapabilityProvider implements ICapabilitySerializable<Tag> {
    private static final Direction NO_SPECIFIC_SIDE = null;

    private BasketStackHandler stackHandler;
    private final LazyOptional<IItemHandler> lazyInitialisionSupplier = LazyOptional.of(this::getCachedInventory);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, lazyInitialisionSupplier);
    }

    @Override
    public Tag serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), NO_SPECIFIC_SIDE);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), NO_SPECIFIC_SIDE, nbt);
    }

    @Nonnull
    private BasketStackHandler getCachedInventory() {
        if (stackHandler == null) {
            stackHandler = new BasketStackHandler();
        }
        return stackHandler;
    }
}
