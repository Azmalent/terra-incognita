package azmalent.terraincognita.common.block.plant;

import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SwampReedsBlock extends SugarCaneBlock implements SimpleWaterloggedBlock {
    public enum Variant implements StringRepresentable {
        SINGLE("single"), TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

        private final String name;

        Variant(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Variant> VARIANT = EnumProperty.create("variant", Variant.class);

    public SwampReedsBlock() {
        super(Block.Properties.of(Material.PLANT, MaterialColor.WOOD)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS));

        registerDefaultState(stateDefinition.any()
             .setValue(AGE, 0)
             .setValue(WATERLOGGED, false)
             .setValue(VARIANT, Variant.SINGLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
        builder.add(VARIANT);
    }

    @Override
    @Nullable
    @SuppressWarnings("ConstantConditions")
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        boolean waterlogged = level.getFluidState(pos).getType() == Fluids.WATER;
        Variant variant = level.getBlockState(pos.below()).getBlock() == this ? Variant.TOP : Variant.SINGLE;

        return super.getStateForPlacement(context).setValue(WATERLOGGED, waterlogged).setValue(VARIANT, variant);
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@NotNull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (facing == Direction.UP) {
            var variant = stateIn.getValue(VARIANT);

            if (facingState.getBlock() == this) {
                if (variant == Variant.SINGLE) {
                    variant = Variant.BOTTOM;
                } else if (variant == Variant.TOP) {
                    variant = Variant.MIDDLE;
                }
            } else {
                if (variant == Variant.BOTTOM) {
                    variant = Variant.SINGLE;
                } else if (variant == Variant.MIDDLE) {
                    variant = Variant.TOP;
                }
            }

            return super.updateShape(stateIn.setValue(VARIANT, variant), facing, facingState, level, currentPos, facingPos);
        }

        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader world, BlockPos pos) {
        BlockPos up = pos.above();
        if (world.getFluidState(up).is(FluidTags.WATER) || world.getBlockState(up).is(Blocks.FROSTED_ICE)) {
            return false;
        }

        BlockPos down = pos.below();
        BlockState soil = world.getBlockState(down);
        if (soil.getBlock() == this || soil.canSustainPlant(world, down, Direction.UP, this)) return true;

        if (soil.is(Blocks.GRASS_BLOCK) || soil.is(Blocks.DIRT) || soil.is(Blocks.COARSE_DIRT) || soil.is(Blocks.PODZOL) || soil.is(Blocks.SAND) || soil.is(Blocks.RED_SAND) || soil.is(ModBlocks.PEAT.get())) {
            if (state.getValue(WATERLOGGED)) {
                return true;
            }

            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState neighbor = world.getBlockState(down.relative(direction));
                FluidState fluidState = world.getFluidState(down.relative(direction));
                if (fluidState.is(FluidTags.WATER) || neighbor.is(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return false;
    }
}
