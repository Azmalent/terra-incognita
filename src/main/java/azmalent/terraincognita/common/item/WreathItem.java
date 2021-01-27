package azmalent.terraincognita.common.item;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class WreathItem extends DyeableArmorItem {
    private static int DEFAULT_COLOR = DyeColor.YELLOW.getColorValue();

    public WreathItem() {
        super(FlowerMaterial.INSTANCE, EquipmentSlotType.HEAD, new Item.Properties().group(TerraIncognita.TAB));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        setColor(stack, DEFAULT_COLOR);
        return stack;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for (DyeColor color : DyeColor.values()) {
                ItemStack stack = new ItemStack(this);
                setColor(stack, color.getColorValue());
                items.add(stack);
            }
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT display = stack.getOrCreateChildTag("display");
        return display.contains("color", Constants.NBT.TAG_ANY_NUMERIC) ? display.getInt("color") : DEFAULT_COLOR;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (!flagIn.isAdvanced()) {
            for (ITextComponent line : tooltip) {
                if (line instanceof TranslationTextComponent) {
                    String key = ((TranslationTextComponent) line).getKey();
                    TerraIncognita.LOGGER.info(key);
                    if (key.equals("item.dyed")) {
                        tooltip.remove(line);
                        return;
                    }
                }
            }
        }
    }
}
