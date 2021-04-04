package azmalent.terraincognita.common.item.dispenser;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public final class ShearCactusDispenserBehavior extends OptionalDispenseBehavior {
    @Nonnull
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        World world = source.getWorld();
        if (!world.isRemote()) {
            BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            BlockState state = world.getBlockState(pos);
            if (state.isIn(Blocks.CACTUS)) {
                world.setBlockState(pos, ModBlocks.SMOOTH_CACTUS.getDefaultState());

                world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
                Block.spawnAsEntity(world, pos, new ItemStack(ModItems.CACTUS_NEEDLE.get(), 1 + world.rand.nextInt(2)));
                if (stack.attemptDamageItem(1, world.rand, null)) {
                    stack.setCount(0);
                }

                setSuccessful(true);
            }
        }

        return stack;
    }
}
