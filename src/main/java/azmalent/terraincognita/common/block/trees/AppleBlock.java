package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class AppleBlock extends AbstractFruitBlock {
    private static final VoxelShape SMALL_SHAPE = makeCuboidShape(6, 9, 6, 10, 13, 10);
    private static final VoxelShape BIG_SHAPE = makeCuboidShape(5, 7, 5, 11, 13, 11);

    public AppleBlock() {
        super(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.RED).hardnessAndResistance(0.2F).sound(SoundType.WOOD), () -> Items.APPLE);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(worldIn, pos);
        return (state.get(AGE) < 2 ? SMALL_SHAPE : BIG_SHAPE).withOffset(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState up = worldIn.getBlockState(pos.up());
        return (up.isIn(ModWoodTypes.APPLE.LEAVES.getBlock()) || up.isIn(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock())  && !up.get(LeavesBlock.PERSISTENT));
    }
}
