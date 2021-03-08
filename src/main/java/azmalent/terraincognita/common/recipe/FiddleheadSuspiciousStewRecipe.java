package azmalent.terraincognita.common.recipe;


import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FiddleheadSuspiciousStewRecipe extends SpecialRecipe {
    private static ItemStack DUMMY = new ItemStack(Items.SUSPICIOUS_STEW, 1);

    public FiddleheadSuspiciousStewRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, @Nonnull World worldIn) {
        boolean flower = false;
        boolean redMushroom = false;
        boolean brownMushroom = false;
        boolean bowl = false;
        boolean fiddlehead = false;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();

                if (item == Blocks.BROWN_MUSHROOM.asItem() && !brownMushroom) {
                    brownMushroom = true;
                } else if (item == Blocks.RED_MUSHROOM.asItem() && !redMushroom) {
                    redMushroom = true;
                } else if (item.isIn(ItemTags.SMALL_FLOWERS) && !flower) {
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
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW, 1);

        ItemStack flower = ItemStack.EMPTY;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem().isIn(ItemTags.SMALL_FLOWERS)) {
                flower = stack;
                break;
            }
        }

        if (flower.getItem() instanceof BlockItem && ((BlockItem)flower.getItem()).getBlock() instanceof FlowerBlock) {
            FlowerBlock flowerBlock = (FlowerBlock) ((BlockItem) flower.getItem()).getBlock();
            Effect effect = flowerBlock.getStewEffect();
            float duration = flowerBlock.getStewEffectDuration() * (effect.isBeneficial() ? 2 : 0.5f);
            SuspiciousStewItem.addEffect(stew, effect, Math.max((int) duration, 1));
            stew.getOrCreateTag().putByte("fiddlehead", (byte) 0);
        }

        return stew;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 5;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return DUMMY;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.FIDDLEHEAD_SUSPICIOUS_STEW.get();
    }
}
