package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.world.feature.CaribouMossFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("deprecation")
public class CaribouMossBlock extends BushBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public CaribouMossBlock() {
        super(Properties.copy(Blocks.GRASS));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 offset = state.getOffset(world, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, BlockPos pos) {
        return state.isFaceSturdy(level, pos.below(), Direction.UP);
    }

    //IGrowable implementation
    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@Nonnull Level level, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel level, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        for(int i = 0; i < 3; i++) {
            int x = rand.nextInt(3) - rand.nextInt(3);
            int y = rand.nextInt(2) - rand.nextInt(2);
            int z = rand.nextInt(3) - rand.nextInt(3);
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.offset(x, y, z);
            if (level.isEmptyBlock(nextPos) || level.getBlockState(nextPos).is(Blocks.SNOW)) {
                CaribouMossFeature.tryPlaceMoss(level, pos.offset(x, y, z), rand);
            }
        }
    }
}
