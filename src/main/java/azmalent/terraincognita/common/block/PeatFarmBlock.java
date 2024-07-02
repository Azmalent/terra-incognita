package azmalent.terraincognita.common.block;

import azmalent.terraincognita.TIServerConfig;
import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class PeatFarmBlock extends FarmBlock {
    public PeatFarmBlock() {
        super(Properties.copy(Blocks.FARMLAND));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.PEAT.defaultBlockState() : defaultBlockState();
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull BlockGetter world, @NotNull BlockPos pos, @Nonnull Direction facing, IPlantable plant) {
        return plant.getPlantType(world, pos) == PlantType.CROP;
    }

    @Override
    public void tick(BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @NotNull Random rand) {
        if (!state.canSurvive(level, pos)) {
            turnToPeat(state, level, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel world, BlockPos pos, @NotNull Random random) {
        BlockPos up = pos.above();

        int moisture = state.getValue(MOISTURE);
        if (moisture > 0 && hasCrops(world, pos)) {
            BlockState crop = world.getBlockState(up);
            if (crop.isRandomlyTicking() && random.nextDouble() < TIServerConfig.Peat.growthRateBonus.get()) {
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
    @ParametersAreNonnullByDefault
    public void fallOn(Level level, BlockState state, @Nonnull BlockPos pos, Entity entityIn, float fallDistance) {
        if (!level.isClientSide && level.random.nextBoolean() && ForgeHooks.onFarmlandTrample(level, pos, ModBlocks.PEAT.defaultBlockState(), fallDistance, entityIn)) {
            turnToPeat(level.getBlockState(pos), level, pos);
        }

        entityIn.causeFallDamage(fallDistance, 1.0F, DamageSource.FALL);
    }

    private boolean hasCrops(BlockGetter level, BlockPos pos) {
        BlockState plant = level.getBlockState(pos.above());
        BlockState state = level.getBlockState(pos);
        return plant.getBlock() instanceof IPlantable && state.canSustainPlant(level, pos, Direction.UP, (IPlantable) plant.getBlock());
    }

    private boolean hasWater(LevelReader level, BlockPos pos) {
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
    }

    private void turnToPeat(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, ModBlocks.PEAT.defaultBlockState(), level, pos));
    }
}
