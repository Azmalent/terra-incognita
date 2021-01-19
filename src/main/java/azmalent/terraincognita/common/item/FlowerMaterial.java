package azmalent.terraincognita.common.item;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class FlowerMaterial implements IArmorMaterial {
    public static FlowerMaterial INSTANCE = new FlowerMaterial();

    @Override
    public int getDurability(EquipmentSlotType equipmentSlotType) {
        return 64;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType equipmentSlotType) {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromTag(ItemTags.FLOWERS);
    }

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
