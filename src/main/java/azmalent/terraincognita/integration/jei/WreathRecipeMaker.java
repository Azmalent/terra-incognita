package azmalent.terraincognita.integration.jei;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.registry.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class WreathRecipeMaker {
    private static final ResourceLocation RECIPE_ID = TerraIncognita.prefix("wreath");
    private static final Ingredient FLOWER_INGREDIENT = Ingredient.of(ItemTags.SMALL_FLOWERS);

    //TODO: display different colors
    public static List<CraftingRecipe> getRecipes() {
        NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
            FLOWER_INGREDIENT, FLOWER_INGREDIENT,
            FLOWER_INGREDIENT, FLOWER_INGREDIENT
        );

        ItemStack output = new ItemStack(ModItems.WREATH.get());

        ShapedRecipe recipe = new ShapedRecipe(RECIPE_ID, "", 2, 2, inputs, output);
        return List.of(recipe);
    }
}
