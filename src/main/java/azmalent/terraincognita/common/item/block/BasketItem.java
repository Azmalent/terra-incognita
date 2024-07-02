package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.ModItemTags;
import azmalent.terraincognita.common.menu.BasketMenu;
import azmalent.terraincognita.common.menu.BasketStackHandler;
import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("ConstantConditions")
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
                new BasketMenu(id, inventory, getStackHandler(stack), stack), stack.getHoverName()
            ));
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean overrideOtherStackedOnMe(ItemStack basket, ItemStack stack, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            if (stack.getItem().canFitInsideContainerItems() && stack.is(ModItemTags.BASKET_STORABLE)) {
                BasketStackHandler stackHandler = BasketItem.getStackHandler(basket);

                if (ItemHandlerHelper.insertItemStacked(stackHandler, stack.copy(), true).getCount() < stack.getCount()) {
                    ItemStack remainingStack = ItemHandlerHelper.insertItemStacked(stackHandler, stack.copy(), false);
                    stack.setCount(remainingStack.getCount());

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    /*
     *  Capability stuff
     */
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BasketStackHandler();
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

    //For autopickup
    public static ItemStack getBasketInHand(Player player) {
        if (player.getMainHandItem().getItem() == ModBlocks.BASKET.asItem()) {
            return player.getMainHandItem();
        }

        if (player.getOffhandItem().getItem() == ModBlocks.BASKET.asItem()) {
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
