package azmalent.terraincognita.common.recipe;

import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.init.ModRecipes;
import azmalent.terraincognita.util.FlowerColorMap;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FlowerBandRecipe extends SpecialRecipe {
    private static ItemStack DUMMY = new ItemStack(ModItems.FLOWER_BAND.get());

    public FlowerBandRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int numFlowers = 0;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();
            if (item.isIn(ItemTags.SMALL_FLOWERS)) numFlowers++;
            else if (!stack.isEmpty()) return false;
        }

        return numFlowers == 4;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        List<DyeItem> dyes = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                DyeItem dye = FlowerColorMap.getFlowerColor(inv.getStackInSlot(i).getItem());
                dyes.add(dye);
            }
        }

        ItemStack output = new ItemStack(ModItems.FLOWER_BAND.get());
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
        return ModRecipes.FLOWER_BAND.get();
    }
}
