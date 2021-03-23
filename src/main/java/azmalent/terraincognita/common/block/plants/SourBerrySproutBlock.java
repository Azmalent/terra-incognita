package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SourBerrySproutBlock extends LilyPadBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 1.5D, 12.0D);

    public SourBerrySproutBlock() {
        super(Block.Properties.create(Material.PLANTS).sound(SoundType.SWEET_BERRY_BUSH).doesNotBlockMovement().zeroHardnessAndResistance().tickRandomly());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.SOUR_BERRIES.get());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (worldIn.getLightSubtracted(pos, 0) >= 9 && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)) {
            worldIn.setBlockState(pos, ModBlocks.SOUR_BERRY_BUSH.getDefaultState(), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    //IGrowable implementation
    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, ModBlocks.SOUR_BERRY_BUSH.getDefaultState(), 2);
    }
}
