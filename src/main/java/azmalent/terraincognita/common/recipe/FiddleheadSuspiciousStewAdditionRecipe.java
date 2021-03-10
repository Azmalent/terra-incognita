package azmalent.terraincognita.common.recipe;

import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

public class FiddleheadSuspiciousStewAdditionRecipe extends SpecialRecipe {
    private static ItemStack DUMMY;

    static {
        DUMMY = new ItemStack(Items.SUSPICIOUS_STEW, 1);
        CompoundNBT tag = DUMMY.getOrCreateTag();
        tag.putByte("fiddlehead", (byte) 0);
    }

    public FiddleheadSuspiciousStewAdditionRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, @Nonnull World worldIn) {
        boolean stew = false;
        boolean fiddlehead = false;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();

                if (item == Items.SUSPICIOUS_STEW && !stew) {
                    if (stack.hasTag() && stack.getTag().contains("fiddlehead")) return false;
                    stew = true;
                } else if (item == ModItems.FIDDLEHEAD.get() && !fiddlehead) {
                    fiddlehead = true;
                }
                else return false;
            }
        }

        return stew && fiddlehead;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stew = ItemStack.EMPTY;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.SUSPICIOUS_STEW) {
                stew = stack;
                break;
            }
        }

        CompoundNBT tag = stew.getOrCreateTag();
        if (tag.contains("Effects", Constants.NBT.TAG_LIST)) {
            ListNBT effects = tag.getList("Effects", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < effects.size(); ++i) {
                CompoundNBT effectTag = effects.getCompound(i);

                Effect effect = Effect.get(effectTag.getByte("EffectId"));
                int duration = effectTag.contains("EffectDuration", 3) ? effectTag.getInt("EffectDuration") : 160;
                duration *= effect.isBeneficial() ? 2 : 0.5f;

                effectTag.putInt("EffectDuration", Math.max(duration, 1));
            }
        }

        stew.getOrCreateTag().putByte("fiddlehead", (byte) 0);
        return stew;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 || height >= 2;
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
