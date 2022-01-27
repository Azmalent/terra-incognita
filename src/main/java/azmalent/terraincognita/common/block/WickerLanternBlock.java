package azmalent.terraincognita.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class WickerLanternBlock extends LanternBlock {
    private static final VoxelShape GROUNDED_SHAPE = box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    private static final VoxelShape HANGING_SHAPE  = box(4.0D, 2.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    public WickerLanternBlock() {
        super(Properties.of(Material.WOOD).strength(3.5F).sound(SoundType.WOOD).lightLevel((state) -> 15).noOcclusion());
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : GROUNDED_SHAPE;
    }
}
