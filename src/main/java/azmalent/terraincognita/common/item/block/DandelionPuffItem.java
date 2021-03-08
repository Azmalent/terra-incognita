package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModParticles;
import azmalent.terraincognita.common.registry.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class DandelionPuffItem extends BlockItem {
    public DandelionPuffItem(Block blockIn) {
        super(blockIn, new Item.Properties().group(TerraIncognita.TAB));
    }

    @Nonnull
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 100;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        Random random = player.getRNG();
        Vector3d look = player.getLookVec();
        World world = player.world;

        BasicParticleType particle = ModParticles.DANDELION_FLUFF.get();
        Vector3d particleOrigin = player.getPositionVec().add(look).add(0, player.getEyeHeight(), 0);

        world.playSound((PlayerEntity) player, player.getPosition(), ModSounds.DANDELION_BLOW.get(), SoundCategory.PLAYERS, 0.5f, random.nextFloat() * 0.4f + 0.8f);

        int n = random.nextInt(5) - 2;
        for (int i = 0; i < n; i++) {
            float dx = (random.nextFloat() - 0.5F) / 2;
            float dy = (random.nextFloat() - 0.5F) / 2;
            float dz = (random.nextFloat() - 0.5F) / 2;
            Vector3d pos = particleOrigin.add(dx, dy, dz);

            float speed = 2 + random.nextFloat() * 5;
            Vector3d direction = look.add(dx, dy, dz).normalize();
            TerraIncognita.PROXY.spawnParticle(world, particle, false, pos, direction.mul(speed, speed, speed));
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World worldIn, @Nonnull LivingEntity entityLiving) {
        if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            player.addStat(Stats.ITEM_USED.get(this));

            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        return stack;
    }
}
