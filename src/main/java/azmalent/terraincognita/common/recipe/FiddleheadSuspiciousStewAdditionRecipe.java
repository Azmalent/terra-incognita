package azmalent.terraincognita.common.recipe;

import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModRecipes;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FiddleheadSuspiciousStewAdditionRecipe extends CustomRecipe {
    private static final ItemStack DUMMY;

    static {
        DUMMY = new ItemStack(Items.SUSPICIOUS_STEW, 1);
        CompoundTag tag = DUMMY.getOrCreateTag();
        tag.putByte("fiddlehead", (byte) 0);
    }

    public FiddleheadSuspiciousStewAdditionRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingContainer inv, @Nonnull Level worldIn) {
        boolean stew = false;
        boolean fiddlehead = false;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
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
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack stew = ItemStack.EMPTY;
        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == Items.SUSPICIOUS_STEW) {
                stew = stack;
                break;
            }
        }

        CompoundTag tag = stew.getOrCreateTag();
        if (tag.contains("Effects", Constants.NBT.TAG_LIST)) {
            ListTag effects = tag.getList("Effects", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < effects.size(); ++i) {
                CompoundTag effectTag = effects.getCompound(i);

                MobEffect effect = MobEffect.byId(effectTag.getByte("EffectId"));
                int duration = effectTag.contains("EffectDuration", 3) ? effectTag.getInt("EffectDuration") : 160;
                duration *= effect.isBeneficial() ? 2 : 0.5f;

                effectTag.putInt("EffectDuration", Math.max(duration, 1));
            }
        }

        stew.getOrCreateTag().putByte("fiddlehead", (byte) 0);
        return stew;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 || height >= 2;
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
