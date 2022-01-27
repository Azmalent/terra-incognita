package azmalent.terraincognita.common.block;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.data.ModBlockTags;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.network.message.s2c.S2CSpawnParticleMessage;
import net.minecraft.block.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class PeatBlock extends Block {
    public PeatBlock() {
        super(Properties.copy(Blocks.DIRT).randomTicks());
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.SHOVEL;
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel world, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.randomTick(state, world, pos, random);

        BlockPos plantPos = pos.above();
        BlockState plant = world.getBlockState(plantPos);
        if (plant.is(ModBlockTags.PEAT_MULTIBLOCK_PLANTS)) {
            while (world.getBlockState(plantPos.above()).is(ModBlockTags.PEAT_MULTIBLOCK_PLANTS)) {
                plantPos = plantPos.above();
            }

            plant = world.getBlockState(plantPos);
        }

        if ((plant.getBlock() instanceof BonemealableBlock || plant.getBlock() instanceof SugarCaneBlock) && plant.isRandomlyTicking()) {
        	if (random.nextDouble() < TIConfig.Misc.peatGrowthRateBonus.get()) {
        	    BlockPos up = plantPos.above();
        	    BlockState above = world.getBlockState(up);

            	plant.randomTick(world, plantPos, random);
            	if (plant.is(ModBlockTags.PEAT_MULTIBLOCK_PLANTS)) {
            	    if (world.getBlockState(up) != above) {
            	        makeParticles(up, random);
                    }
                }
            	else if (world.getBlockState(plantPos) != plant) {
            	    makeParticles(up, random);
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull BlockGetter world, @NotNull BlockPos pos, @Nonnull Direction facing, @NotNull IPlantable plant) {
        return Blocks.DIRT.canSustainPlant(Blocks.DIRT.defaultBlockState(), world, pos, facing, plant);
    }

    public static void makeParticles(BlockPos pos, Random random) {
        for(int i = 0; i < 8; i++) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            double xSpeed = random.nextGaussian() * 0.02D;
            double ySpeed = random.nextGaussian() * 0.02D;
            double zSpeed = random.nextGaussian() * 0.02D;

            S2CSpawnParticleMessage message = new S2CSpawnParticleMessage(ParticleTypes.HAPPY_VILLAGER, x, y, z, xSpeed, ySpeed, zSpeed);
            NetworkHandler.sendToAllPlayers(message);
        }
    }
}
