package azmalent.terraincognita.common.block.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import org.jetbrains.annotations.NotNull;

public class RootsBlock extends HangingPlantBlock implements BonemealableBlock {
	public static final VoxelShape SHAPE = box(3, 6, 3, 13, 16, 13);

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        Vec3 offset = state.getOffset(reader, blockPos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected boolean isValidGround(BlockState state, BlockState ground, BlockGetter level, BlockPos groundPos) {
        return ground.is(Tags.Blocks.DIRT);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull BlockState blockState, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@Nonnull Level world, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel world, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        BlockState roots = this.defaultBlockState();

        for(int i = 0; i < 4; i++) {
            int x = random.nextInt(4) - random.nextInt(4);
            int y = random.nextInt(2) - random.nextInt(2);
            int z = random.nextInt(4) - random.nextInt(4);
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.offset(x, y, z);
            if (world.isEmptyBlock(nextPos) && roots.canSurvive(world, nextPos)) {
                world.setBlockAndUpdate(nextPos, roots);
            }
        }
    }
}
