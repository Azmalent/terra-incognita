package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.item.material.FlowerMaterial;
import com.google.common.collect.Maps;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.core.NonNullList;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WreathItem extends DyeableArmorItem {
    private static final int DEFAULT_COLOR = DyeColor.YELLOW.getColorValue();

    public WreathItem() {
        super(FlowerMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        setColor(stack, DEFAULT_COLOR);
        return stack;
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for (DyeColor color : DyeColor.values()) {
                ItemStack stack = new ItemStack(this);
                setColor(stack, color.getColorValue());
                items.add(stack);
            }
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag display = stack.getOrCreateTagElement("display");
        return display.contains("color", Constants.NBT.TAG_ANY_NUMERIC) ? display.getInt("color") : DEFAULT_COLOR;
    }
}
