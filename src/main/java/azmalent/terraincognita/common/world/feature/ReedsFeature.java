package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.ReedsBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class ReedsFeature extends Feature<NoFeatureConfig> {
    private final static int X_SPREAD = 4;
    private final static int Y_SPREAD = 2;
    private final static int Z_SPREAD = 4;
    private final static int TRIES = 30;

    public ReedsFeature() {
        super(NoFeatureConfig.field_236558_a_);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos centerPos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);
        BlockPos.Mutable nextPos = new BlockPos.Mutable();
        boolean success = false;

        for(int i = 0; i < TRIES; ++i) {
            nextPos.setAndOffset(centerPos, rand.nextInt(X_SPREAD + 1) - rand.nextInt(X_SPREAD + 1), rand.nextInt(Y_SPREAD + 1) - rand.nextInt(Y_SPREAD + 1), rand.nextInt(Z_SPREAD + 1) - rand.nextInt(Z_SPREAD + 1));
            if (!reader.isAirBlock(nextPos.up())) {
                continue;
            }

            int height = 2 + rand.nextInt(rand.nextInt(3) + 1);
            for (int j = 0; j < height; j++) {
                nextPos.offset(Direction.UP, j);

                boolean isWater = reader.getFluidState(nextPos).getFluid() == Fluids.WATER;
                BlockState reeds = ModBlocks.REEDS.getBlock().getDefaultState().with(ReedsBlock.WATERLOGGED, isWater);

                if ((isWater || reader.isAirBlock(nextPos) || reader.getBlockState(nextPos).getMaterial().isReplaceable()) && reeds.isValidPosition(reader, nextPos)) {
                    reader.setBlockState(nextPos, reeds, 2);
                    success = true;
                }
                else {
                    break;
                }
            }
        }

        return success;
    }
}
