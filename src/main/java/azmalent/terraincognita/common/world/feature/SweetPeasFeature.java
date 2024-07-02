package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.Util;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class SweetPeasFeature extends Feature<NoneFeatureConfiguration> {
    public SweetPeasFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        BlockState state = Util.getRandom(ModBlocks.SWEET_PEAS, random).defaultBlockState();
        boolean success = false;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for(int y = 64; y < 128; y++) {
            mutablePos.set(origin);
            mutablePos.move(random.nextInt(8) - random.nextInt(8), 0, random.nextInt(8) - random.nextInt(8));
            mutablePos.setY(y);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (tryPlaceVine(level, state, direction, mutablePos, random)) {
                    success = true;
                    break;
                }
            }
        }

        return success;
    }

    private boolean tryPlaceVine(WorldGenLevel reader, BlockState defaultState, Direction direction, BlockPos.MutableBlockPos pos, Random rand) {
        boolean success = false;
        if (reader.getBlockState(pos.relative(direction)).is(BlockTags.LEAVES) && hasEnoughVerticalSpace(reader, pos)) {
            int length = 3 + rand.nextInt(8);
            BlockState state = defaultState.setValue(VineBlock.getPropertyForFace(direction), true);

            int startY = pos.getY();
            for (int y = startY; y > startY - length; y--) {
                pos.setY(y);

                if (reader.isEmptyBlock(pos) && state.canSurvive(reader, pos)) {
                    reader.setBlock(pos, state, 2);
                    success = true;
                } else {
                    break;
                }
            }
        }

        return success;
    }

    private boolean hasEnoughVerticalSpace(WorldGenLevel reader, BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            if (!reader.isEmptyBlock(pos.below(i))) {
                return false;
            }
        }

        return true;
    }
}
