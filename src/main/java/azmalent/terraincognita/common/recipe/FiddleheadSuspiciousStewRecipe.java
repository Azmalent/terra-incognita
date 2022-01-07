package azmalent.terraincognita.common.recipe;


import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModRecipes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;

public class FiddleheadSuspiciousStewRecipe extends CustomRecipe {
    private static final ItemStack DUMMY = new ItemStack(Items.SUSPICIOUS_STEW, 1);

    public FiddleheadSuspiciousStewRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingContainer inv, @Nonnull Level worldIn) {
        boolean flower = false;
        boolean redMushroom = false;
        boolean brownMushroom = false;
        boolean bowl = false;
        boolean fiddlehead = false;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();

                if (item == Blocks.BROWN_MUSHROOM.asItem() && !brownMushroom) {
                    brownMushroom = true;
                } else if (item == Blocks.RED_MUSHROOM.asItem() && !redMushroom) {
                    redMushroom = true;
                } else if (item.is(ItemTags.SMALL_FLOWERS) && !flower) {
                    flower = true;
                } else if (item == Items.BOWL && !bowl) {
                    bowl = true;
                } else if (item == ModItems.FIDDLEHEAD.get() && !fiddlehead) {
                    fiddlehead = true;
                }
                else return false;
            }
        }

        return flower && brownMushroom && redMushroom && bowl && fiddlehead;
    }

    @Nonnull
    @Override
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW, 1);

        ItemStack flower = ItemStack.EMPTY;
        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem().is(ItemTags.SMALL_FLOWERS)) {
                flower = stack;
                break;
            }
        }

        if (flower.getItem() instanceof BlockItem && ((BlockItem)flower.getItem()).getBlock() instanceof FlowerBlock) {
            FlowerBlock flowerBlock = (FlowerBlock) ((BlockItem) flower.getItem()).getBlock();
            MobEffect effect = flowerBlock.getSuspiciousStewEffect();
            float duration = flowerBlock.getEffectDuration() * (effect.isBeneficial() ? 2 : 0.5f);
            SuspiciousStewItem.saveMobEffect(stew, effect, Math.max((int) duration, 1));
            stew.getOrCreateTag().putByte("fiddlehead", (byte) 0);
        }

        return stew;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 5;
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
