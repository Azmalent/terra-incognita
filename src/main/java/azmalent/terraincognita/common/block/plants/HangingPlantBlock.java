package azmalent.terraincognita.common.block.plants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public abstract class HangingPlantBlock extends Block implements IPlantable {
    public HangingPlantBlock() {
        super(Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, @Nonnull BlockGetter p_200123_2_, @Nonnull BlockPos p_200123_3_) {
        return true;
    }

    protected abstract boolean isValidGround(BlockState state, BlockState ground, BlockGetter worldIn, BlockPos groundPos);

    @Nonnull
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (pos.getY() >= 255) return false;

        BlockPos up = pos.above();
        return this.isValidGround(state, worldIn.getBlockState(up), worldIn, up);
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, PathComputationType type) {
        return type == PathComputationType.AIR || super.isPathfindable(state, worldIn, pos, type);
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return defaultBlockState();
        return state;
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CAVE;
    }

}
