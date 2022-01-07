package azmalent.terraincognita.common.integration.quark.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import vazkii.quark.content.building.module.HedgesModule;

import javax.annotation.Nonnull;

public class TIHedgeBlock extends FenceBlock {
    public static final BooleanProperty EXTEND = BooleanProperty.create("extend");

    public TIHedgeBlock(MaterialColor woodColor) {
        super(Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        registerDefaultState(defaultBlockState().setValue(EXTEND, false));
    }

    @Override
    public boolean connectsTo(BlockState state, boolean isSideSolid, @Nonnull Direction direction) {
        return state.getBlock().is(HedgesModule.hedgesTag);
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull BlockGetter world, BlockPos pos, @Nonnull Direction facing, IPlantable plantable) {
        return facing == Direction.UP && !state.getValue(WATERLOGGED) && plantable.getPlantType(world, pos) == PlantType.PLAINS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos down = blockpos.below();
        BlockState downState = iblockreader.getBlockState(down);

        return super.getStateForPlacement(context)
                .setValue(EXTEND, downState.getBlock() instanceof TIHedgeBlock);
    }

    @Nonnull
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        if(facing == Direction.DOWN) {
            return stateIn.setValue(EXTEND, facingState.getBlock() instanceof TIHedgeBlock);
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EXTEND);
    }
}
