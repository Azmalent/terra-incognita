package azmalent.terraincognita.common.block.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class SmallCactusBlock extends BushBlock {
    private static final VoxelShape SHAPE = box(4, 0, 4, 12, 9, 12);

    public SmallCactusBlock() {
        super(Properties.copy(Blocks.TALL_GRASS));
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(Tags.Blocks.SAND);
    }
}
