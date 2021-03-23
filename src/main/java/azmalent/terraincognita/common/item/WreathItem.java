package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.item.material.FlowerMaterial;
import com.google.common.collect.Maps;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class WreathItem extends DyeableArmorItem {
    private static final int DEFAULT_COLOR = DyeColor.YELLOW.getColorValue();

    public WreathItem() {
        super(FlowerMaterial.INSTANCE, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.MISC));
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
