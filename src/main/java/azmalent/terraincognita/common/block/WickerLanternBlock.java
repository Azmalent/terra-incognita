package azmalent.terraincognita.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class WickerLanternBlock extends LanternBlock {
    private static final VoxelShape GROUNDED_SHAPE = makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    private static final VoxelShape HANGING_SHAPE  = makeCuboidShape(4.0D, 2.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    public WickerLanternBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(3.5F).sound(SoundType.WOOD).setLightLevel((state) -> 15).notSolid());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : GROUNDED_SHAPE;
    }
}
