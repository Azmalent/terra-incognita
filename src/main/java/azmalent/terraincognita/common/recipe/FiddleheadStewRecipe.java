package azmalent.terraincognita.common.recipe;

import azmalent.cuneiform.common.crafting.ShapelessRecipeMatcher;
import azmalent.cuneiform.common.crafting.SimpleShapelessRecipe;
import azmalent.cuneiform.util.CraftingUtil;
import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.core.registry.ModItems;
import azmalent.terraincognita.core.registry.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class FiddleheadStewRecipe extends SimpleShapelessRecipe {
    private static final ItemStack DUMMY = new ItemStack(Items.SUSPICIOUS_STEW, 1);

    private static final ShapelessRecipeMatcher MATCHER = CraftingUtil.defineShapelessRecipe()
            .addIngredient(Blocks.BROWN_MUSHROOM)
            .addIngredient(Blocks.RED_MUSHROOM)
            .addIngredient(ItemTags.SMALL_FLOWERS)
            .addIngredient(Items.BOWL)
            .addIngredient(ModItems.FERN_FIDDLEHEAD)
            .build();

    public FiddleheadStewRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    protected ShapelessRecipeMatcher getMatcher() {
        return MATCHER;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@NotNull CraftingContainer inv) {
        ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW, 1);
        ItemStack flower = CraftingUtil.findItemInGrid(inv, item -> item.is(ItemTags.SMALL_FLOWERS));

        if (ItemUtil.getBlockFromItem(flower) instanceof FlowerBlock flowerBlock) {
            MobEffect effect = flowerBlock.getSuspiciousStewEffect();
            float duration = flowerBlock.getEffectDuration() * (effect.isBeneficial() ? 2 : 0.5f);
            SuspiciousStewItem.saveMobEffect(stew, effect, Math.max((int) duration, 1));
            stew.getOrCreateTag().putByte("fiddlehead", (byte) 1);
        }

        return stew;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return DUMMY;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FIDDLEHEAD_SUSPICIOUS_STEW.get();
    }
}
