package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import com.minecraftabnormals.upgrade_aquatic.client.particle.UAParticles;
import com.minecraftabnormals.upgrade_aquatic.common.items.GlowingInkItem;
import com.minecraftabnormals.upgrade_aquatic.core.registry.UAItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getItemStack();

        TileEntity te = world.getTileEntity(pos);
        if (!player.isSecondaryUseActive() && te instanceof ModSignTileEntity) {
            ModSignTileEntity sign = (ModSignTileEntity) te;

            boolean applied = false;
            if (stack.getItem() == UAItems.GLOWING_INK_SAC.get()) {
                if (sign.setGlowing(true)) {
                    if (world.isRemote()) GlowingInkItem.squirtInk(UAParticles.GLOW_SQUID_INK.get(), pos);
                    applied = true;
                }
            }
            else if (stack.getItem() == Items.INK_SAC) {
                if (sign.setGlowing(false)) {
                    if (world.isRemote()) GlowingInkItem.squirtInk(ParticleTypes.SQUID_INK, pos);
                    applied = true;
                }
            }

            if (applied) {
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                world.playSound(player, pos, SoundEvents.ENTITY_SQUID_SQUIRT, SoundCategory.BLOCKS, 1.0F, 1.0F);

                event.setCanceled(true);
                event.setCancellationResult(ActionResultType.SUCCESS);
            }
        }
    }
}
