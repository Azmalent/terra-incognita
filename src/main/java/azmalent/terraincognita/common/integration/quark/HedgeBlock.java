package azmalent.terraincognita.common.integration.quark;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import vazkii.quark.content.building.module.HedgesModule;

import javax.annotation.Nonnull;

public class HedgeBlock extends FenceBlock {
    public static final BooleanProperty EXTEND = BooleanProperty.create("extend");

    public HedgeBlock(MaterialColor woodColor) {
        super(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
        setDefaultState(getDefaultState().with(EXTEND, false));
    }

    @Override
    public boolean canConnect(BlockState state, boolean isSideSolid, @Nonnull Direction direction) {
        return state.getBlock().isIn(HedgesModule.hedgesTag);
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull IBlockReader world, BlockPos pos, @Nonnull Direction facing, IPlantable plantable) {
        return facing == Direction.UP && !state.get(WATERLOGGED) && plantable.getPlantType(world, pos) == PlantType.PLAINS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos down = blockpos.down();
        BlockState downState = iblockreader.getBlockState(down);

        return super.getStateForPlacement(context)
                .with(EXTEND, downState.getBlock() instanceof HedgeBlock);
    }

    @Nonnull
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        if(facing == Direction.DOWN) {
            return stateIn.with(EXTEND, facingState.getBlock() instanceof HedgeBlock);
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(EXTEND);
    }
}
