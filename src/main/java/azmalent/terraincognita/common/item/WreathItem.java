package azmalent.terraincognita.common.item;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class WreathItem extends DyeableArmorItem {
    private static final int DEFAULT_COLOR = DyeColor.YELLOW.getFireworkColor();

    public WreathItem() {
        super(FlowerMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        setColor(stack, DEFAULT_COLOR);
        return stack;
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for (DyeColor color : DyeColor.values()) {
                ItemStack stack = new ItemStack(this);
                setColor(stack, color.getFireworkColor());
                items.add(stack);
            }
        }
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag display = stack.getOrCreateTagElement("display");
        return display.contains("color", Tag.TAG_ANY_NUMERIC) ? display.getInt("color") : DEFAULT_COLOR;
    }

    public static class FlowerMaterial implements ArmorMaterial {
        public static final FlowerMaterial INSTANCE = new FlowerMaterial();

        @Override
        public int getDurabilityForSlot(@Nonnull EquipmentSlot equipmentSlotType) {
            return 64;
        }

        @Override
        public int getDefenseForSlot(@Nonnull EquipmentSlot equipmentSlotType) {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Nonnull
        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_GENERIC;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ItemTags.FLOWERS);
        }

        @Nonnull
        @Override
        public String getName() {
            return TerraIncognita.MODID + ":flowers";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }
}
