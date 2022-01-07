package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.DefaultFlowerFeature;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class AlpineFlowerFeature extends DefaultFlowerFeature {
    public AlpineFlowerFeature() {
        super(RandomPatchConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull WorldGenLevel reader, ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, RandomPatchConfiguration config) {
        BlockState flower = this.getFlowerToPlace(reader, rand, pos, config);
        boolean success = false;

        for(int j = 0; j < this.getCount(config); ++j) {
            BlockPos nextPos = this.getPos(rand, pos, config);
            if (reader.isEmptyBlock(nextPos) && nextPos.getY() < 255 && flower.canSurvive(reader, nextPos) && this.isValid(reader, nextPos, config)) {
                reader.setBlock(nextPos, flower, 2);
                success = true;
            }
        }

        return success;
    }

    private BlockState getFlowerToPlace(WorldGenLevel reader, Random rand, BlockPos pos, RandomPatchConfiguration config) {
        BlockState soil = reader.getBlockState(pos.below());
        if (soil.is(Tags.Blocks.STONE) || soil.is(Tags.Blocks.COBBLESTONE)) {
            return ModBlocks.SAXIFRAGE.getBlock().defaultBlockState();
        }

        return getRandomFlower(rand, pos, config);
    }

    @Override
    public boolean isValid(LevelAccessor world, @Nonnull BlockPos pos, RandomPatchConfiguration config) {
        return world.canSeeSkyFromBelowWater(pos) && (pos.getY() >= 64) && super.isValid(world, pos, config);
    }
}
