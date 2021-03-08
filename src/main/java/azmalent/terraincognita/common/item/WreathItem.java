package azmalent.terraincognita.common.item;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

public class WreathItem extends DyeableArmorItem {
    private static int DEFAULT_COLOR = DyeColor.YELLOW.getColorValue();

    public WreathItem() {
        super(FlowerMaterial.INSTANCE, EquipmentSlotType.HEAD, new Item.Properties().group(TerraIncognita.TAB));
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        setColor(stack, DEFAULT_COLOR);
        return stack;
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for (DyeColor color : DyeColor.values()) {
                ItemStack stack = new ItemStack(this);
                setColor(stack, color.getColorValue());
                items.add(stack);
            }
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT display = stack.getOrCreateChildTag("display");
        return display.contains("color", Constants.NBT.TAG_ANY_NUMERIC) ? display.getInt("color") : DEFAULT_COLOR;
    }
}
