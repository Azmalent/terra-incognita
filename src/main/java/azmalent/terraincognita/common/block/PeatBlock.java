package azmalent.terraincognita.common.block;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.data.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
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
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.randomTick(state, world, pos, random);

        BlockPos plantPos = pos.up();
        BlockState plant = world.getBlockState(plantPos);
        if (plant.isIn(ModBlockTags.PEAT_MULTIBLOCK_PLANTS)) {
            while (world.getBlockState(plantPos.up()).isIn(ModBlockTags.PEAT_MULTIBLOCK_PLANTS)) {
                plantPos = plantPos.up();
            }

            plant = world.getBlockState(plantPos);
        }

        if (plant.getBlock() instanceof IGrowable && plant.ticksRandomly()) {
        	if (random.nextDouble() < TIConfig.Misc.peatGrowthRateBonus.get()) {
            	plant.randomTick(world, plantPos, random);
            }
        }
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull IBlockReader world, BlockPos pos, @Nonnull Direction facing, IPlantable plant) {
        return Blocks.DIRT.canSustainPlant(state, world, pos, facing, plant);
    }
}
