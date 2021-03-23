package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFlowersFeature;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class AlpineFlowerFeature extends DefaultFlowersFeature {
    public AlpineFlowerFeature() {
        super(BlockClusterFeatureConfig.field_236587_a_);
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, BlockClusterFeatureConfig config) {
        BlockState flower = this.getFlowerToPlace(reader, rand, pos, config);
        boolean success = false;

        for(int j = 0; j < this.getFlowerCount(config); ++j) {
            BlockPos nextPos = this.getNearbyPos(rand, pos, config);
            if (reader.isAirBlock(nextPos) && nextPos.getY() < 255 && flower.isValidPosition(reader, nextPos) && this.isValidPosition(reader, nextPos, config)) {
                reader.setBlockState(nextPos, flower, 2);
                success = true;
            }
        }

        return success;
    }

    private BlockState getFlowerToPlace(ISeedReader reader, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        BlockState soil = reader.getBlockState(pos.down());
        if (soil.isIn(Tags.Blocks.STONE) || soil.isIn(Tags.Blocks.COBBLESTONE)) {
            return ModBlocks.SAXIFRAGE.getBlock().getDefaultState();
        }

        return getFlowerToPlace(rand, pos, config);
    }

    @Override
    public boolean isValidPosition(IWorld world, @Nonnull BlockPos pos, BlockClusterFeatureConfig config) {
        return world.canBlockSeeSky(pos) && (pos.getY() >= 64) && super.isValidPosition(world, pos, config);
    }
}
