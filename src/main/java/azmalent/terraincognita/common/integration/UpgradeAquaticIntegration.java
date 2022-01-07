package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import com.minecraftabnormals.upgrade_aquatic.client.particle.UAParticles;
import com.minecraftabnormals.upgrade_aquatic.common.items.GlowingInkItem;
import com.minecraftabnormals.upgrade_aquatic.core.registry.UAItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;

@ModProxyImpl("upgrade_aquatic")
public class UpgradeAquaticIntegration implements IModIntegration {
    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Upgrade Aquatic...");
        MinecraftForge.EVENT_BUS.addListener(this::onRightClickBlock);
    }

    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        ItemStack stack = event.getItemStack();

        BlockEntity te = world.getBlockEntity(pos);
        if (!player.isSecondaryUseActive() && te instanceof ModSignTileEntity) {
            ModSignTileEntity sign = (ModSignTileEntity) te;

            boolean applied = false;
            if (stack.getItem() == UAItems.GLOWING_INK_SAC.get()) {
                if (sign.setGlowing(true)) {
                    if (world.isClientSide()) GlowingInkItem.squirtInk(UAParticles.GLOW_SQUID_INK.get(), pos);
                    applied = true;
                }
            }
            else if (stack.getItem() == Items.INK_SAC) {
                if (sign.setGlowing(false)) {
                    if (world.isClientSide()) GlowingInkItem.squirtInk(ParticleTypes.SQUID_INK, pos);
                    applied = true;
                }
            }

            if (applied) {
                if (!player.abilities.instabuild) stack.shrink(1);
                world.playSound(player, pos, SoundEvents.SQUID_SQUIRT, SoundSource.BLOCKS, 1.0F, 1.0F);

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
