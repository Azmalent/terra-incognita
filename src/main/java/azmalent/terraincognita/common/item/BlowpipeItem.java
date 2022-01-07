package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.entity.CactusNeedleEntity;
import azmalent.terraincognita.common.registry.ModItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;

@MethodsReturnNonnullByDefault
public class BlowpipeItem extends ProjectileWeaponItem implements Vanishable {
    private static final Predicate<ItemStack> AMMO_PREDICATE = stack -> stack.getItem() == ModItems.CACTUS_NEEDLE.get();

    public BlowpipeItem() {
        super(new Item.Properties().stacksTo(1).durability(64).tab(CreativeModeTab.TAB_COMBAT));
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity living, int timeLeft) {
        if (living instanceof Player) {
            Player player = (Player)living;
            boolean infiniteAmmo = player.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack ammo = player.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, !ammo.isEmpty() || infiniteAmmo);
            if (i < 0) return;

            if (!ammo.isEmpty() || infiniteAmmo) {
                if (ammo.isEmpty()) {
                    ammo = new ItemStack(ModItems.CACTUS_NEEDLE.get());
                }

                float velocity = getArrowVelocity(i);
                if (!((double)velocity < 0.1D)) {
                    if (!worldIn.isClientSide) {
                        CactusNeedleEntity needle = new CactusNeedleEntity(player, worldIn);

                        int power = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (power > 0) {
                            needle.setDamage(needle.getDamage() + (double)power * 0.5D + 0.5D);
                        }

                        int knockback = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (knockback > 0) {
                            needle.setKnockbackStrength(knockback);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            needle.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, player, (playerEntity) -> {
                            playerEntity.broadcastBreakEvent(player.getUsedItemHand());
                        });

                        worldIn.addFreshEntity(needle);
                    }

                    worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
                    if (!infiniteAmmo) {
                        ammo.shrink(1);
                        if (ammo.isEmpty()) {
                            player.inventory.removeItem(ammo);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean hasAmmo = !player.getProjectile(stack).isEmpty();

        InteractionResultHolder<ItemStack> result = ForgeEventFactory.onArrowNock(stack, world, player, hand, hasAmmo);
        if (result != null) return result;

        if (!player.abilities.instabuild && !hasAmmo) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    public static float getArrowVelocity(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW;
    }


    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return AMMO_PREDICATE;
    }

    //Shooting cooldown for mobs
    @Override
    public int getDefaultProjectileRange() {
        return 12;
    }
}
