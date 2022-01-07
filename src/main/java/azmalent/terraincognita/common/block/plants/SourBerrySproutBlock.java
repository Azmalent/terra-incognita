package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class SourBerrySproutBlock extends WaterlilyBlock implements BonemealableBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 1.5D, 12.0D);

    public SourBerrySproutBlock() {
        super(Block.Properties.of(Material.PLANT).sound(SoundType.SWEET_BERRY_BUSH).noCollission().instabreak().randomTicks());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.SOUR_BERRIES.get());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (worldIn.getRawBrightness(pos, 0) >= 9 && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)) {
            worldIn.setBlock(pos, ModBlocks.SOUR_BERRY_BUSH.getDefaultState(), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    //IGrowable implementation
    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlock(pos, ModBlocks.SOUR_BERRY_BUSH.getDefaultState(), 2);
    }
}
