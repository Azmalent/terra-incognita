package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.effect.ModStewEffect;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

public class MarigoldBlock extends FlowerBlock {
    private static final int WATER_SEARCH_RADIUS = 3;

    public MarigoldBlock() {
        super(ModStewEffect.REGENERATION.effect, ModStewEffect.REGENERATION.duration, Properties.from(Blocks.ORANGE_TULIP));
    }

    private boolean hasWaterNearby(IBlockReader world, BlockPos soilPos) {
        for (int y = -1; y <= 0; y++) {
            for (int x = -WATER_SEARCH_RADIUS; x <= WATER_SEARCH_RADIUS; x++) {
                for (int z = -WATER_SEARCH_RADIUS; z <= WATER_SEARCH_RADIUS; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;

                    BlockPos pos = soilPos.add(x, y, z);
                    if (world.getFluidState(pos).isTagged(FluidTags.WATER) || world.getBlockState(pos).isIn(Blocks.FROSTED_ICE)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        if (state.isIn(Tags.Blocks.SAND) && hasWaterNearby(worldIn, pos)) {
            return true;
        }

        return super.isValidGround(state, worldIn, pos);
    }
}
