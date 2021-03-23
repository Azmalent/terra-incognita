package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class SweetPeasFeature extends Feature<NoFeatureConfig> {
    public SweetPeasFeature() {
        super(NoFeatureConfig.field_236558_a_);
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator generator, Random rand, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        BlockState state = ModBlocks.SWEET_PEAS.get(rand.nextInt(ModBlocks.SWEET_PEAS.size())).getDefaultState();
        boolean success = false;

        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        for(int y = 64; y < 128; y++) {
            mutablePos.setPos(pos);
            mutablePos.move(rand.nextInt(8) - rand.nextInt(8), 0, rand.nextInt(8) - rand.nextInt(8));
            mutablePos.setY(y);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (tryPlaceVine(reader, state, direction, mutablePos, rand)) {
                    success = true;
                    break;
                }
            }
        }

        return success;
    }

    private boolean tryPlaceVine(ISeedReader reader, BlockState defaultState, Direction direction, BlockPos.Mutable pos, Random rand) {
        boolean success = false;
        if (reader.getBlockState(pos.offset(direction)).isIn(BlockTags.LEAVES) && hasEnoughVerticalSpace(reader, pos)) {
            int length = 3 + rand.nextInt(8);
            BlockState state = defaultState.with(VineBlock.getPropertyFor(direction), true);

            int startY = pos.getY();
            for (int y = startY; y > startY - length; y--) {
                pos.setY(y);

                if (reader.isAirBlock(pos) && state.isValidPosition(reader, pos)) {
                    reader.setBlockState(pos, state, 2);
                    success = true;
                } else {
                    break;
                }
            }
        }

        return success;
    }

    private boolean hasEnoughVerticalSpace(ISeedReader reader, BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            if (!reader.isAirBlock(pos.down(i))) {
                return false;
            }
        }

        return true;
    }
}
