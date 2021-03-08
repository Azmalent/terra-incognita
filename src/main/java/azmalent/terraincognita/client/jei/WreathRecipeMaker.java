package azmalent.terraincognita.client.jei;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class WreathRecipeMaker {
    private static ResourceLocation recipeId = TerraIncognita.prefix("wreath");
    private static Ingredient flowerIngredient = Ingredient.fromTag(ItemTags.SMALL_FLOWERS);

    public static List<IShapedRecipe<?>> getRecipes() {
        NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
            flowerIngredient, flowerIngredient,
            flowerIngredient, flowerIngredient
        );

        ItemStack output = new ItemStack(ModItems.WREATH.get());

        ShapedRecipe recipe = new ShapedRecipe(recipeId, "", 2, 2, inputs, output);
        return Lists.newArrayList(recipe);
    }
}
