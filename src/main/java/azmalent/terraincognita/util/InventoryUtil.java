package azmalent.terraincognita.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class InventoryUtil {
    public static void giveStackToPlayer(PlayerEntity player, ItemStack stack) {
        if (!player.inventory.addItemStackToInventory(stack)) {
            player.dropItem(stack, false);
        }
    }

    public static void giveStackToPlayer(PlayerEntity player, ItemStack stack, Hand hand) {
        if (player.getHeldItem(hand).isEmpty()) {
            player.setHeldItem(hand, stack);
        } else {
            giveStackToPlayer(player, stack);
        }
    }
}
