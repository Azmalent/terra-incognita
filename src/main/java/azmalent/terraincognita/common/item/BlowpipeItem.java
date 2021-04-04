package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.registry.ModItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;

import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
public class BlowpipeItem extends ShootableItem implements IVanishable {
    private static final Predicate<ItemStack> AMMO_PREDICATE = stack -> stack.getItem() == ModItems.CACTUS_NEEDLE.get();

    public BlowpipeItem() {
        super(new Item.Properties().maxStackSize(1).maxDamage(64).group(ItemGroup.COMBAT));
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return AMMO_PREDICATE;
    }

    @Override
    public int func_230305_d_() {
        return 12;
    }
}
