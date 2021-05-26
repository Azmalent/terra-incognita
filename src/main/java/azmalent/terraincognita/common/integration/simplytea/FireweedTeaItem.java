package azmalent.terraincognita.common.integration.simplytea;

import knightminer.simplytea.core.Registration;
import knightminer.simplytea.item.TeaCupItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nonnull;

public class FireweedTeaItem extends TeaCupItem {
    public FireweedTeaItem() {
        super(new Item.Properties().group(Registration.group).maxStackSize(1).maxDamage(2).setNoRepair()
            .food(new Food.Builder().hunger(2).saturation(0.5f)
                .effect(() -> new EffectInstance(Registration.restful, 20 * 20, 1), 1)
                .build()
            )
        );
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        stack = stack.copy();
        stack.setDamage(stack.getDamage() + 1);
        return stack.getDamage() >= stack.getMaxDamage() ? new ItemStack(Registration.cup) : stack;
    }
}
