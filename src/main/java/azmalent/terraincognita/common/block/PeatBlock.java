package azmalent.terraincognita.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class PeatBlock extends Block {
    public PeatBlock() {
        super(Properties.from(Blocks.DIRT).tickRandomly());
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.SHOVEL;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        BlockState plant = world.getBlockState(pos.up());
        if (plant.getBlock() instanceof SugarCaneBlock || plant.isIn(Blocks.BAMBOO)) {
            BlockPos top = pos.up();
            while (world.getBlockState(top.up()).getBlock() == plant.getBlock()) {
                top = top.up();
            }

            plant = world.getBlockState(top);
        }

        if (plant.getBlock() instanceof IGrowable && plant.ticksRandomly()) {
            plant.randomTick(world, pos, random);
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plant) {
        if (plant.getPlantType(world, pos) == PlantType.PLAINS) {
            return true;
        }

        if (plant.getPlantType(world, pos) == PlantType.BEACH) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos neighbor = pos.offset(direction);
                FluidState fluidState = world.getFluidState(neighbor);
                if (fluidState.isTagged(FluidTags.WATER) || world.getBlockState(neighbor).isIn(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return super.canSustainPlant(state, world, pos, facing, plant);
    }
}
