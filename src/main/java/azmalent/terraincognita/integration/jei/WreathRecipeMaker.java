package azmalent.terraincognita.integration.jei;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class WreathRecipeMaker {
    private static final ResourceLocation recipeId = TerraIncognita.prefix("wreath");
    private static final Ingredient flowerIngredient = Ingredient.of(ItemTags.SMALL_FLOWERS);

    //TODO: display different colors
    public static List<CraftingRecipe> getRecipes() {
        NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
            flowerIngredient, flowerIngredient,
            flowerIngredient, flowerIngredient
        );

        ItemStack output = new ItemStack(ModItems.WREATH.get());

        ShapedRecipe recipe = new ShapedRecipe(recipeId, "", 2, 2, inputs, output);
        return Lists.newArrayList(recipe);
    }
}
