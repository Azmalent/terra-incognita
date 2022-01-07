package azmalent.terraincognita.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;

public class InventoryUtil {
    public static void giveStackToPlayer(Player player, ItemStack stack) {
        if (!player.inventory.add(stack)) {
            player.drop(stack, false);
        }
    }

    public static void giveStackToPlayer(Player player, ItemStack stack, InteractionHand hand) {
        if (player.getItemInHand(hand).isEmpty()) {
            player.setItemInHand(hand, stack);
        } else {
            giveStackToPlayer(player, stack);
        }
    }
}
