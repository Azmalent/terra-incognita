package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.capability.BasketCapabilityProvider;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.inventory.BasketContainer;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasketItem extends BlockItem {
    public BasketItem(Block block) {
        super(block, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && player instanceof ServerPlayer) {
            NetworkHooks.openGui((ServerPlayer) player, new SimpleMenuProvider((id, inventory, playerEntity) ->
                new BasketContainer(id, inventory, getStackHandler(stack), stack),
                stack.getHoverName()
            ));
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BasketCapabilityProvider();
    }

    @SuppressWarnings("ConstantConditions")
    public static BasketStackHandler getStackHandler(ItemStack basketStack) {
        IItemHandler itemHandler = basketStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if (itemHandler instanceof BasketStackHandler) {
            return (BasketStackHandler) itemHandler;
        }

        TerraIncognita.LOGGER.error("BasketItem did not have the expected ITEM_HANDLER_CAPABILITY");
        return null;
    }

    public static ItemStack getBasketInHand(Player player) {
        if (player.getMainHandItem().getItem() == ModBlocks.BASKET.getItem()) {
            return player.getMainHandItem();
        }

        if (player.getOffhandItem().getItem() == ModBlocks.BASKET.getItem()) {
            return player.getOffhandItem();
        }

        return null;
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag baseTag = stack.getTag();
        BasketStackHandler stackHandler = getStackHandler(stack);
        CompoundTag capabilityTag = stackHandler.serializeNBT();

        CompoundTag combinedTag = new CompoundTag();
        if (baseTag != null) {
            combinedTag.put("base", baseTag);
        }
        if (capabilityTag != null) {
            combinedTag.put("cap", capabilityTag);
        }
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag tag) {
        if (tag == null) {
            stack.setTag(null);
            return;
        }

        CompoundTag baseTag = tag.getCompound("base");
        CompoundTag capabilityTag = tag.getCompound("cap");
        stack.setTag(baseTag);

        getStackHandler(stack).deserializeNBT(capabilityTag);
    }
}
