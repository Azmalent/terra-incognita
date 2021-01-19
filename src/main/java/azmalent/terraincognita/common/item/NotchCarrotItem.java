package azmalent.terraincognita.common.item;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class NotchCarrotItem extends Item {
    public NotchCarrotItem() {
        super(new Item.Properties().group(TerraIncognita.TAB).food(ModFoods.NOTCH_CARROT));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
