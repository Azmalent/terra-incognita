package azmalent.terraincognita.common.item;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class TaffyItem extends Item {
    private static int DURATION = 30;

    public TaffyItem() {
        super(new Item.Properties().group(TerraIncognita.TAB).food(ModFoods.TAFFY));
    }

    @Nonnull
    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 16;
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World worldIn, @Nonnull LivingEntity entityLiving) {
        if (!worldIn.isRemote && entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entityLiving;
            EffectInstance effect = player.getActivePotionEffect(ModEffects.STICKY_MOUTH.get());

            int amplifier = 0;
            int duration = DURATION * 20;
            if (effect != null) {
                amplifier = Math.min(effect.getAmplifier() + 1, 2);
                duration += effect.getDuration() / 2;
                player.removeActivePotionEffect(ModEffects.STICKY_MOUTH.get());
            }

            player.addPotionEffect(new EffectInstance(ModEffects.STICKY_MOUTH.get(), duration, amplifier, false, false));
            player.heal(TIConfig.Food.taffyHealing.get());

            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.addStat(Stats.ITEM_USED.get(this));
        }

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
