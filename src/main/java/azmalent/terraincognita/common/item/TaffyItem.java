package azmalent.terraincognita.common.item;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModEffects;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class TaffyItem extends Item {
    private static final int DURATION = 30;

    public TaffyItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModItems.Foods.TAFFY));
    }

    @Nonnull
    @Override
    public Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 16;
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, Level level, @Nonnull LivingEntity entityLiving) {
        if (!level.isClientSide && entityLiving instanceof ServerPlayer player) {
            MobEffectInstance effect = player.getEffect(ModEffects.STICKY_MOUTH.get());

            int amplifier = 0;
            int duration = DURATION * 20;
            if (effect != null) {
                amplifier = Math.min(effect.getAmplifier() + 1, 2);
                duration += effect.getDuration() / 2;
                player.removeEffectNoUpdate(ModEffects.STICKY_MOUTH.get());
            }

            player.addEffect(new MobEffectInstance(ModEffects.STICKY_MOUTH.get(), duration, amplifier, false, false));
            player.heal(TIConfig.Food.taffyHealing.get());

            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return super.finishUsingItem(stack, level, entityLiving);
    }
}
