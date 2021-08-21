package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import knightminer.simplytea.core.Registration;
import knightminer.simplytea.core.config.TeaDrink;
import knightminer.simplytea.item.TeaCupItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
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
        ItemGroup group = Registration.group;

        FIREWEED_TEA_BAG = ModItems.ITEMS.register("fireweed_teabag", () -> new Item(new Item.Properties().group(group)));
        FIREWEED_TEA_CUP = ModItems.ITEMS.register("fireweed_tea_cup", () -> new ModTeaItem(FIREWEED_TEA_STATS));
    }

    private static class ModTeaStats extends Food {
        private Effect effect;
        private int duration;
        private int amplifier;

        public ModTeaStats(int hunger, float saturation, Effect effect, int duration, int amplifier) {
            super(hunger, saturation, false, true, true, Collections.emptyList());

            this.effect = effect;
            this.duration = duration;
            this.amplifier = amplifier;
        }

        public EffectInstance getEffect(boolean hasHoney) {
            return new EffectInstance(effect, duration, amplifier + (hasHoney ? 1 : 0));
        }
    }

    private static class ModTeaItem extends TeaCupItem {
        public ModTeaItem(ModTeaStats stats) {
            super(new Item.Properties().group(Registration.group).maxStackSize(1).maxDamage(2).setNoRepair().food(stats));
        }

        @Nonnull
        @Override
        public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity living) {
            if (this.isFood()) {
                ItemStack result = stack.getContainerItem();
                boolean hasHoney = hasHoney(stack, "with_honey");
                living.curePotionEffects(stack);
                living.onFoodEaten(worldIn, stack);
                Food food = this.getFood();
                if (food instanceof ModTeaStats) {
                    EffectInstance effectInstance = ((ModTeaStats)food).getEffect(hasHoney);
                    if (effectInstance != null) {
                        living.addPotionEffect(effectInstance);
                    }
                }

                return result;
            } else {
                return stack;
            }
        }
    }
}
