package azmalent.terraincognita.common.block;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class PeatFarmlandBlock extends FarmlandBlock {
    public PeatFarmlandBlock() {
        super(Properties.from(Blocks.FARMLAND));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return !getDefaultState().isValidPosition(context.getWorld(), context.getPos()) ? ModBlocks.PEAT.getBlock().getDefaultState() : getDefaultState();
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull IBlockReader world, BlockPos pos, @Nonnull Direction facing, IPlantable plant) {
        return plant.getPlantType(world, pos) == PlantType.CROP;
    }

    @Override
    public void tick(BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, Random rand) {
        if (!state.isValidPosition(worldIn, pos)) {
            turnToPeat(state, worldIn, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos up = pos.up();

        int moisture = state.get(MOISTURE);
        if (moisture > 0 && hasCrops(world, pos)) {
            BlockState crop = world.getBlockState(up);
            if (crop.ticksRandomly() && random.nextDouble() < TIConfig.Misc.peatGrowthRateBonus.get()) {
                crop.randomTick(world, up, random);

                if (world.getBlockState(up) != crop) {
                    PeatBlock.makeParticles(up, random);
                }
            }
        }

        if (!hasWater(world, pos) && !world.isRainingAt(up)) {
            if (moisture > 0) {
                world.setBlockState(pos, state.with(MOISTURE, moisture - 1), 2);
            }
            else if (!hasCrops(world, pos)) {
                turnToPeat(state, world, pos);
            }
        }
        else if (moisture < 7) {
            world.setBlockState(pos, state.with(MOISTURE, 7), 2);
        }
    }

    @Override
    public void onFallenUpon(World worldIn, @Nonnull BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isRemote && worldIn.rand.nextBoolean() && ForgeHooks.onFarmlandTrample(worldIn, pos, ModBlocks.PEAT.getDefaultState(), fallDistance, entityIn)) {
            turnToPeat(worldIn.getBlockState(pos), worldIn, pos);
        }

        entityIn.onLivingFall(fallDistance, 1.0F);
    }

    private boolean hasCrops(IBlockReader worldIn, BlockPos pos) {
        BlockState plant = worldIn.getBlockState(pos.up());
        BlockState state = worldIn.getBlockState(pos);
        return plant.getBlock() instanceof IPlantable && state.canSustainPlant(worldIn, pos, Direction.UP, (IPlantable) plant.getBlock());
    }

    private boolean hasWater(IWorldReader worldIn, BlockPos pos) {
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
                return true;
            }
        }

        return FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos);
    }

    private void turnToPeat(BlockState state, World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, nudgeEntitiesWithNewState(state, ModBlocks.PEAT.getBlock().getDefaultState(), worldIn, pos));
    }
}
