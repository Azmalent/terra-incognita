package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class NotchCarrotItem extends Item {
    public NotchCarrotItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModItems.Foods.NOTCH_CARROT));
    }

    @Nonnull
    @Override
    public Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.EPIC;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
}
