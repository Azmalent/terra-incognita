package azmalent.terraincognita.common.block;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PeatFarmlandBlock extends FarmBlock {
    public PeatFarmlandBlock() {
        super(Properties.copy(Blocks.FARMLAND));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.PEAT.getBlock().defaultBlockState() : defaultBlockState();
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull BlockGetter world, BlockPos pos, @Nonnull Direction facing, IPlantable plant) {
        return plant.getPlantType(world, pos) == PlantType.CROP;
    }

    @Override
    public void tick(BlockState state, @Nonnull ServerLevel worldIn, @Nonnull BlockPos pos, Random rand) {
        if (!state.canSurvive(worldIn, pos)) {
            turnToPeat(state, worldIn, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        BlockPos up = pos.above();

        int moisture = state.getValue(MOISTURE);
        if (moisture > 0 && hasCrops(world, pos)) {
            BlockState crop = world.getBlockState(up);
            if (crop.isRandomlyTicking() && random.nextDouble() < TIConfig.Misc.peatGrowthRateBonus.get()) {
                crop.randomTick(world, up, random);

                if (world.getBlockState(up) != crop) {
                    PeatBlock.makeParticles(up, random);
                }
            }
        }

        if (!hasWater(world, pos) && !world.isRainingAt(up)) {
            if (moisture > 0) {
                world.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            }
            else if (!hasCrops(world, pos)) {
                turnToPeat(state, world, pos);
            }
        }
        else if (moisture < 7) {
            world.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }
    }

    @Override
    public void fallOn(Level worldIn, @Nonnull BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isClientSide && worldIn.random.nextBoolean() && ForgeHooks.onFarmlandTrample(worldIn, pos, ModBlocks.PEAT.getDefaultState(), fallDistance, entityIn)) {
            turnToPeat(worldIn.getBlockState(pos), worldIn, pos);
        }

        entityIn.causeFallDamage(fallDistance, 1.0F);
    }

    private boolean hasCrops(BlockGetter worldIn, BlockPos pos) {
        BlockState plant = worldIn.getBlockState(pos.above());
        BlockState state = worldIn.getBlockState(pos);
        return plant.getBlock() instanceof IPlantable && state.canSustainPlant(worldIn, pos, Direction.UP, (IPlantable) plant.getBlock());
    }

    private boolean hasWater(LevelReader worldIn, BlockPos pos) {
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (worldIn.getFluidState(blockpos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos);
    }

    private void turnToPeat(BlockState state, Level worldIn, BlockPos pos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, ModBlocks.PEAT.getBlock().defaultBlockState(), worldIn, pos));
    }
}
