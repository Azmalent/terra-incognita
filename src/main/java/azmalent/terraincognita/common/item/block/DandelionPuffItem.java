package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModParticles;
import azmalent.terraincognita.common.registry.ModSounds;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

public class DandelionPuffItem extends BlockItem {
    public DandelionPuffItem(Block blockIn) {
        super(blockIn, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 100;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        Random random = player.getRandom();
        Vec3 look = player.getLookAngle();
        Level world = player.level;

        SimpleParticleType particle = ModParticles.DANDELION_FLUFF.get();
        Vec3 particleOrigin = player.position().add(look).add(0, player.getEyeHeight(), 0);

        world.playSound((Player) player, player.blockPosition(), ModSounds.DANDELION_BLOW.get(), SoundSource.PLAYERS, 0.5f, random.nextFloat() * 0.4f + 0.8f);

        int n = random.nextInt(5) - 2;
        for (int i = 0; i < n; i++) {
            float dx = (random.nextFloat() - 0.5F) / 2;
            float dy = (random.nextFloat() - 0.5F) / 2;
            float dz = (random.nextFloat() - 0.5F) / 2;
            Vec3 pos = particleOrigin.add(dx, dy, dz);

            float speed = 2 + random.nextFloat() * 5;
            Vec3 direction = look.add(dx, dy, dz).normalize();
            TerraIncognita.PROXY.spawnParticle(world, particle, false, pos, direction.multiply(speed, speed, speed));
        }
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        finishUsingItem(stack, level, entityLiving);
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, Level level, @Nonnull LivingEntity entityLiving) {
        if (!level.isClientSide && entityLiving instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));

            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }

        return stack;
    }
}
