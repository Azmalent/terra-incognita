package azmalent.terraincognita.common.recipe;

import azmalent.cuneiform.common.crafting.ShapelessRecipeMatcher;
import azmalent.cuneiform.common.crafting.SimpleShapelessRecipe;
import azmalent.cuneiform.lib.util.CraftingUtil;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModRecipes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class FiddleheadStewAdditionRecipe extends SimpleShapelessRecipe {
    private static final ItemStack DUMMY = new ItemStack(Items.SUSPICIOUS_STEW, 1);
    
    private static final ShapelessRecipeMatcher MATCHER = CraftingUtil.defineShapelessRecipe()
        .addIngredient(stack -> stack.is(Items.SUSPICIOUS_STEW) && !stack.getOrCreateTag().contains("fiddlehead"))
        .addIngredient(ModItems.FIDDLEHEAD)
        .build();

    static {
        DUMMY.getOrCreateTag().putByte("fiddlehead", (byte) 1);
    }

    public FiddleheadStewAdditionRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    protected ShapelessRecipeMatcher getMatcher() {
        return MATCHER;
    }

    @Nonnull
    @Override
    @SuppressWarnings("ConstantConditions")
    public ItemStack assemble(@NotNull CraftingContainer inv) {
        ItemStack stew = CraftingUtil.findItemInGrid(inv, stack -> stack.is(Items.SUSPICIOUS_STEW));

        CompoundTag tag = stew.getOrCreateTag();
        if (tag.contains("Effects", Tag.TAG_LIST)) {
            ListTag effects = tag.getList("Effects", Tag.TAG_COMPOUND);
            for(int i = 0; i < effects.size(); ++i) {
                CompoundTag effectTag = effects.getCompound(i);

                MobEffect effect = MobEffect.byId(effectTag.getByte("EffectId"));
                int duration = effectTag.contains("EffectDuration", 3) ? effectTag.getInt("EffectDuration") : 160;
                duration *= effect.isBeneficial() ? 2 : 0.5f;

                effectTag.putInt("EffectDuration", Math.max(duration, 1));
            }
        }

        stew.getOrCreateTag().putByte("fiddlehead", (byte) 1);
        return stew;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return MATCHER.canCraftInDimensions(width, height);
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
