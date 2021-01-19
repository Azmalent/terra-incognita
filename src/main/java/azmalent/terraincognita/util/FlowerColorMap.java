package azmalent.terraincognita.util;

import com.google.common.collect.Maps;
import net.minecraft.item.*;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Map;

public class FlowerColorMap {
    private static Map<Item, DyeItem> FLOWER_COLORS = Maps.newHashMap();

    public static DyeItem getFlowerColor(Item flower) {
        return FLOWER_COLORS.getOrDefault(flower, (DyeItem) Items.WHITE_DYE);
    }

    public static void getFlowerColorFromRecipe(ICraftingRecipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients.size() != 1) return;

        ItemStack[] matchingStacks = ingredients.get(0).getMatchingStacks();
        for (int i = 0, n = matchingStacks.length; i < n; i++) {
            Item input = matchingStacks[i].getItem();
            Item output = recipe.getRecipeOutput().getItem();

            if (input.isIn(ItemTags.SMALL_FLOWERS) && output.isIn(Tags.Items.DYES)) {
                DyeItem dye = (DyeItem) output.getItem();
                FLOWER_COLORS.put(input, dye);
            }
        }
    }
}
