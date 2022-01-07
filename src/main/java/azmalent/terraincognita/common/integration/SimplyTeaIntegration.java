package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import knightminer.simplytea.core.Registration;
import knightminer.simplytea.core.config.TeaDrink;
import knightminer.simplytea.item.TeaCupItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collections;

@SuppressWarnings({"FieldCanBeLocal", "unused", "ConstantConditions"})
@ModProxyImpl("simplytea")
public class SimplyTeaIntegration implements IModIntegration {
    private static RegistryObject<Item> FIREWEED_TEA_BAG;
    private static RegistryObject<Item> FIREWEED_TEA_CUP;

    private static ModTeaStats FIREWEED_TEA_STATS = new ModTeaStats(2, 0.5f, Registration.restful, 400, 0);

    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Simply Tea...");
        CreativeModeTab group = Registration.group;

        FIREWEED_TEA_BAG = ModItems.ITEMS.register("fireweed_teabag", () -> new Item(new Item.Properties().tab(group)));
        FIREWEED_TEA_CUP = ModItems.ITEMS.register("fireweed_tea_cup", () -> new ModTeaItem(FIREWEED_TEA_STATS));
    }

    private static class ModTeaStats extends FoodProperties {
        private MobEffect effect;
        private int duration;
        private int amplifier;

        public ModTeaStats(int hunger, float saturation, MobEffect effect, int duration, int amplifier) {
            super(hunger, saturation, false, true, true, Collections.emptyList());

            this.effect = effect;
            this.duration = duration;
            this.amplifier = amplifier;
        }

        public MobEffectInstance getEffect(boolean hasHoney) {
            return new MobEffectInstance(effect, duration, amplifier + (hasHoney ? 1 : 0));
        }
    }

    private static class ModTeaItem extends TeaCupItem {
        public ModTeaItem(ModTeaStats stats) {
            super(new Item.Properties().tab(Registration.group).stacksTo(1).durability(2).setNoRepair().food(stats));
        }

        @Nonnull
        @Override
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level worldIn, @Nonnull LivingEntity living) {
            if (this.isEdible()) {
                ItemStack result = stack.getContainerItem();
                boolean hasHoney = hasHoney(stack, "with_honey");
                living.curePotionEffects(stack);
                living.eat(worldIn, stack);
                FoodProperties food = this.getFoodProperties();
                if (food instanceof ModTeaStats) {
                    MobEffectInstance effectInstance = ((ModTeaStats)food).getEffect(hasHoney);
                    if (effectInstance != null) {
                        living.addEffect(effectInstance);
                    }
                }

                return result;
            } else {
                return stack;
            }
        }
    }
}
