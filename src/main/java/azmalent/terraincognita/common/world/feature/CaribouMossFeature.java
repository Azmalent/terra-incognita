package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class CaribouMossFeature extends Feature<NoFeatureConfig> {
    public CaribouMossFeature() {
        super(NoFeatureConfig.field_236558_a_);
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        boolean success = false;
        if (reader.isAirBlock(pos) && ModBlocks.CARIBOU_MOSS.getBlock().getDefaultState().isValidPosition(reader, pos)) {
            int x = 4 + rand.nextInt(6);
            int z = 4 + rand.nextInt(6);
            int count = (int) (x * z * (rand.nextDouble() + rand.nextDouble() + 2));

            BlockPos.Mutable nextPos = new BlockPos.Mutable();
            for (int i = 0; i < count; i++) {
                nextPos.setAndOffset(pos,
                    rand.nextInt(x) - rand.nextInt(x),
                    rand.nextInt(2) - rand.nextInt(2),
                    rand.nextInt(z) - rand.nextInt(z)
                );

                success |= tryPlaceMoss(reader, nextPos, rand);
            }
        }

        return success;
    }

    private boolean tryPlaceMoss(ISeedReader reader, BlockPos pos, Random rand) {
        if (!reader.isAirBlock(pos)) return false;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = ModBlocks.CARIBOU_MOSS_WALL.getDefaultState().with(CaribouMossWallBlock.FACING, direction);
            if (rand.nextBoolean() && state.isValidPosition(reader, pos)) {
                reader.setBlockState(pos, state, 2);
                return true;
            }
        }

        BlockState state = ModBlocks.CARIBOU_MOSS.getBlock().getDefaultState();
        if (state.isValidPosition(reader, pos)) {
            reader.setBlockState(pos, state, 2);
            return true;
        }

        return false;
    }
}
