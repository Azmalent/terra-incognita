package azmalent.terraincognita.common.item.block;

import azmalent.terraincognita.common.item.dispenser.CaltropsDispenserBehavior;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CaltropsItem extends BlockItem {
    private static CaltropsDispenserBehavior DISPENSER_BEHAVIOR = new CaltropsDispenserBehavior();

    public CaltropsItem(Block block) {
        super(block, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(16));
        DispenserBlock.registerDispenseBehavior(this, DISPENSER_BEHAVIOR);
    }

    private boolean tryPlace(World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        BlockState down = world.getBlockState(pos.down());

        if (down.isSolidSide(world, pos.down(), Direction.UP) && blockState.isIn(Blocks.AIR)) {
            world.setBlockState(pos, ModBlocks.CALTROPS.getBlock().getDefaultState());
            return true;
        }

        return false;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.world.isRemote && entity.isOnGround()) {
            if (entity.getThrowerId() != null || entity.getPersistentData().getBoolean("dispensed") || entity.ticksExisted > 60) {
                if (tryPlace(entity.world, entity.getPosition())) {
                    entity.getItem().shrink(1);

                    Random random = entity.world.rand;
                    float pitch = (random.nextFloat() - random.nextFloat()) * 0.2f + 1;
                    entity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), ModSounds.CALTROPS_THROWN.get(), SoundCategory.BLOCKS, 0.8f, pitch);

                    return true;
                }
            }
        }

        return false;
    }
}
