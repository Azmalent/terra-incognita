package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.world.feature.CaribouMossFeature;
import net.minecraft.block.*;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class CaribouMossBlock extends BushBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public CaribouMossBlock() {
        super(Properties.copy(Blocks.GRASS));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(world, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.isFaceSturdy(worldIn, pos.below(), Direction.UP);
    }

    //IGrowable implementation
    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@Nonnull Level worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        for(int i = 0; i < 3; i++) {
            int x = rand.nextInt(3) - rand.nextInt(3);
            int y = rand.nextInt(2) - rand.nextInt(2);
            int z = rand.nextInt(3) - rand.nextInt(3);
            if (x == 0 && z == 0) continue;

            BlockPos nextPos = pos.offset(x, y, z);
            if (worldIn.isEmptyBlock(nextPos) || worldIn.getBlockState(nextPos).is(Blocks.SNOW)) {
                CaribouMossFeature.tryPlaceMoss(worldIn, pos.offset(x, y, z), rand);
            }
        }
    }
}
