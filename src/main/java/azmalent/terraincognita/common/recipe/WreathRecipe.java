package azmalent.terraincognita.common.recipe;

import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModRecipes;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WreathRecipe extends CustomRecipe {
    public static final Map<Item, DyeItem> FLOWER_TO_DYE_MAP = Maps.newHashMap();
    private static final ItemStack DUMMY = new ItemStack(ModItems.WREATH.get());

    public WreathRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingContainer inv, @Nonnull Level level) {
        int numFlowers = 0;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.is(ItemTags.SMALL_FLOWERS)) numFlowers++;
            else if (!stack.isEmpty()) return false;
        }

        if (numFlowers == 4) {
            for (int row = 0; row < inv.getHeight() - 1; row++) {
                for (int col = 0; col < inv.getWidth() - 1; col++) {
                    int i = row * inv.getWidth() + col;
                    if (!inv.getItem(i).isEmpty()) {
                        return !inv.getItem(i + 1).isEmpty() &&
                            !inv.getItem(i + inv.getWidth()).isEmpty() &&
                            !inv.getItem(i + inv.getWidth() + 1).isEmpty();
                    }
                }
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public ItemStack assemble(CraftingContainer inv) {
        List<DyeItem> dyes = new ArrayList<>();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (!inv.getItem(i).isEmpty()) {
                DyeItem dye = FLOWER_TO_DYE_MAP.getOrDefault(inv.getItem(i).getItem(), (DyeItem) Items.WHITE_DYE);
                dyes.add(dye);
            }
        }

        ItemStack output = new ItemStack(ModItems.WREATH.get());
        return DyeableLeatherItem.dyeArmor(output, dyes);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return DUMMY;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.WREATH.get();
    }
}
