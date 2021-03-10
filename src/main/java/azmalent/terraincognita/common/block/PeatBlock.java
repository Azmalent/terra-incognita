package azmalent.terraincognita.common.block;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.data.ModBlockTags;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.network.message.s2c.S2CSpawnParticleMessage;
import net.minecraft.block.*;
import net.minecraft.particles.ParticleTypes;
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

        if ((plant.getBlock() instanceof IGrowable || plant.getBlock() instanceof SugarCaneBlock) && plant.ticksRandomly()) {
        	if (random.nextDouble() < TIConfig.Misc.peatGrowthRateBonus.get()) {
        	    BlockPos up = plantPos.up();
        	    BlockState above = world.getBlockState(up);

            	plant.randomTick(world, plantPos, random);
            	if (plant.isIn(ModBlockTags.PEAT_MULTIBLOCK_PLANTS)) {
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
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull IBlockReader world, BlockPos pos, @Nonnull Direction facing, IPlantable plant) {
        return Blocks.DIRT.canSustainPlant(Blocks.DIRT.getDefaultState(), world, pos, facing, plant);
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
