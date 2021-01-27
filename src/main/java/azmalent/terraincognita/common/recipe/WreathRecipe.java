package azmalent.terraincognita.common.recipe;

import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.init.ModRecipes;
import azmalent.terraincognita.util.ColorUtil;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WreathRecipe extends SpecialRecipe {
    private static ItemStack DUMMY = new ItemStack(ModItems.WREATH.get());

    public WreathRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int numFlowers = 0;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem().isIn(ItemTags.SMALL_FLOWERS)) numFlowers++;
            else if (!stack.isEmpty()) return false;
        }

        if (numFlowers == 4) {
            for (int row = 0; row < inv.getHeight() - 1; row++) {
                for (int col = 0; col < inv.getWidth() - 1; col++) {
                    int i = row * inv.getWidth() + col;
                    if (!inv.getStackInSlot(i).isEmpty()) {
                        return !inv.getStackInSlot(i + 1).isEmpty() &&
                            !inv.getStackInSlot(i + inv.getWidth()).isEmpty() &&
                            !inv.getStackInSlot(i + inv.getWidth() + 1).isEmpty();
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        List<DyeItem> dyes = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                DyeItem dye = ColorUtil.getDyeFromFlower(inv.getStackInSlot(i).getItem());
                dyes.add(dye);
            }
        }

        ItemStack output = new ItemStack(ModItems.WREATH.get());
        return IDyeableArmorItem.dyeItem(output, dyes);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return DUMMY;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.WREATH.get();
    }
}
