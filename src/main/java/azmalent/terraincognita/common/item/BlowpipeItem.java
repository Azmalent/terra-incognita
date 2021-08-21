package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.entity.CactusNeedleEntity;
import azmalent.terraincognita.common.registry.ModItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
public class BlowpipeItem extends ShootableItem implements IVanishable {
    private static final Predicate<ItemStack> AMMO_PREDICATE = stack -> stack.getItem() == ModItems.CACTUS_NEEDLE.get();

    public BlowpipeItem() {
        super(new Item.Properties().maxStackSize(1).maxDamage(64).group(ItemGroup.COMBAT));
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity living, int timeLeft) {
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)living;
            boolean infiniteAmmo = player.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack ammo = player.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, !ammo.isEmpty() || infiniteAmmo);
            if (i < 0) return;

            if (!ammo.isEmpty() || infiniteAmmo) {
                if (ammo.isEmpty()) {
                    ammo = new ItemStack(ModItems.CACTUS_NEEDLE.get());
                }

                float velocity = getArrowVelocity(i);
                if (!((double)velocity < 0.1D)) {
                    if (!worldIn.isRemote) {
                        CactusNeedleEntity needle = new CactusNeedleEntity(player, worldIn);

                        int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (power > 0) {
                            needle.setDamage(needle.getDamage() + (double)power * 0.5D + 0.5D);
                        }

                        int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (knockback > 0) {
                            needle.setKnockbackStrength(knockback);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            needle.setFire(100);
                        }

                        stack.damageItem(1, player, (playerEntity) -> {
                            playerEntity.sendBreakAnimation(player.getActiveHand());
                        });

                        worldIn.addEntity(needle);
                    }

                    worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
                    if (!infiniteAmmo) {
                        ammo.shrink(1);
                        if (ammo.isEmpty()) {
                            player.inventory.deleteStack(ammo);
                        }
                    }

                    player.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
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
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }


    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return AMMO_PREDICATE;
    }

    //Shooting cooldown for mobs
    @Override
    public int func_230305_d_() {
        return 12;
    }
}
