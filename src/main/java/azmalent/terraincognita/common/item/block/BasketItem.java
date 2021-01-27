package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.capability.BasketCapabilityProvider;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.inventory.BasketContainer;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasketItem extends BlockItem {
    public BasketItem(Block block) {
        super(block, new Item.Properties().group(TerraIncognita.TAB).maxStackSize(1));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && player instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((id, inventory, playerEntity) ->
                new BasketContainer(id, inventory, getStackHandler(stack), stack),
                stack.getDisplayName()
            ));
        }

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
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

    public static ItemStack getBasketInHand(PlayerEntity player) {
        assert ModBlocks.BASKET != null;

        if (player.getHeldItemMainhand().getItem() == ModBlocks.BASKET.getItem()) {
            return player.getHeldItemMainhand();
        }

        if (player.getHeldItemOffhand().getItem() == ModBlocks.BASKET.getItem()) {
            return player.getHeldItemOffhand();
        }

        return null;
    }

    public static void handleAutopickup(ItemStack basket, EntityItemPickupEvent event) {
        //TODO: autopickup
    }

    /**
     * Ensure that our capability is sent to the client when transmitted over the network.
     * Not needed if you don't need the capability information on the client
     *
     * Note that this will sometimes be applied multiple times, the following MUST
     * be supported:
     *   Item item = stack.getItem();
     *   NBTTagCompound nbtShare1 = item.getShareTag(stack);
     *   stack.readShareTag(nbtShare1);
     *   NBTTagCompound nbtShare2 = item.getShareTag(stack);
     *   assert nbtShare1.equals(nbtShare2);
     *
     * @param stack The stack to send the NBT tag for
     * @return The NBT tag
     */
    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        BasketStackHandler stackHandler = getStackHandler(stack);
        CompoundNBT capabilityTag = stackHandler.serializeNBT();

        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) {
            combinedTag.put("base", baseTag);
        }
        if (capabilityTag != null) {
            combinedTag.put("cap", capabilityTag);
        }
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT tag) {
        if (tag == null) {
            stack.setTag(null);
            return;
        }

        CompoundNBT baseTag = tag.getCompound("base");
        CompoundNBT capabilityTag = tag.getCompound("cap");
        stack.setTag(baseTag);

        getStackHandler(stack).deserializeNBT(capabilityTag);
    }
}
