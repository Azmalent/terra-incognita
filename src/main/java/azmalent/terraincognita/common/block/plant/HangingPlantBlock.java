package azmalent.terraincognita.common.block.plant;

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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public abstract class HangingPlantBlock extends Block implements IPlantable {
    public HangingPlantBlock() {
        super(Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS));
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState p_200123_1_, @Nonnull BlockGetter p_200123_2_, @Nonnull BlockPos p_200123_3_) {
        return true;
    }

    protected abstract boolean isValidGround(BlockState state, BlockState ground, BlockGetter level, BlockPos groundPos);

    @Nonnull
    @Override
    public BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
        if (pos.getY() >= 255) return false;

        BlockPos up = pos.above();
        return this.isValidGround(state, level.getBlockState(up), level, up);
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @NotNull PathComputationType type) {
        return type == PathComputationType.AIR || super.isPathfindable(state, level, pos, type);
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
