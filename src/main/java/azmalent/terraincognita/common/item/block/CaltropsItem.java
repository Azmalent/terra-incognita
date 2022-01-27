package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.common.item.dispenser.CaltropsDispenserBehavior;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModSounds;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Random;

public class CaltropsItem extends BlockItem {
    private static final CaltropsDispenserBehavior DISPENSER_BEHAVIOR = new CaltropsDispenserBehavior();

    public CaltropsItem(Block block) {
        super(block, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(16));
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    private boolean tryPlace(Level world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        BlockState down = world.getBlockState(pos.below());

        if (down.isFaceSturdy(world, pos.below(), Direction.UP) && blockState.is(Blocks.AIR)) {
            world.setBlockAndUpdate(pos, ModBlocks.CALTROPS.get().defaultBlockState());
            return true;
        }

        return false;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level.isClientSide && entity.isOnGround()) {
            if (entity.getThrower() != null || entity.getPersistentData().getBoolean("dispensed") || entity.tickCount > 60) {
                if (tryPlace(entity.level, entity.blockPosition())) {
                    entity.getItem().shrink(1);

                    Random random = entity.level.random;
                    float pitch = (random.nextFloat() - random.nextFloat()) * 0.2f + 1;
                    entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.CALTROPS_THROWN.get(), SoundSource.BLOCKS, 0.8f, pitch);

                    return true;
                }
            }
        }

        return false;
    }
}
