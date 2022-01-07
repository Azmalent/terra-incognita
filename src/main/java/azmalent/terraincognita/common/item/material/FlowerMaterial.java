package azmalent.terraincognita.common.item.material;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import javax.annotation.Nonnull;

public class FlowerMaterial implements ArmorMaterial {
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
