package azmalent.terraincognita.util;

import com.google.common.collect.Maps;
import net.minecraft.item.*;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Map;

public class ColorUtil {
    private static Map<Item, DyeItem> FLOWER_COLORS = Maps.newHashMap();

    public static DyeItem getDyeFromFlower(Item flower) {
        return FLOWER_COLORS.getOrDefault(flower, (DyeItem) Items.WHITE_DYE);
    }

    public static void saveFlowerDyeRecipe(ICraftingRecipe recipe) {
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

    public static int lerp(float t, int color1, int color2) {
        float r1 = ((color1 >> 16) & 255) / 255f;
        float g1 = ((color1 >> 8) & 255) / 255f;
        float b1 = (color1 & 255) / 255f;

        float r2 = ((color2 >> 16) & 255) / 255f;
        float g2 = ((color2 >> 8) & 255) / 255f;
        float b2 = (color2 & 255) / 255f;

        int r = (int) (MathHelper.lerp(t, r1, r2) * 255);
        int g = (int) (MathHelper.lerp(t, g1, g2) * 255);
        int b = (int) (MathHelper.lerp(t, b1, b2) * 255);

        return(((r << 8) + g) << 8) + b;
    }
}
