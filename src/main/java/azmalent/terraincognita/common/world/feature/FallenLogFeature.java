package azmalent.terraincognita.common.world.feature;

import azmalent.terraincognita.common.block.plants.CaribouMossWallBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class FallenLogFeature extends Feature<NoFeatureConfig> {
    public FallenLogFeature() {
        super(NoFeatureConfig.field_236558_a_);
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        boolean success = false;

        int length = 5 + rand.nextInt(3);
        Direction direction = Direction.Plane.HORIZONTAL.random(rand);
        BlockState log = (rand.nextFloat() < 0.66f ? Blocks.SPRUCE_LOG : Blocks.BIRCH_LOG).getDefaultState().with(RotatedPillarBlock.AXIS, direction.getAxis());
        boolean mossy = rand.nextBoolean();

        BlockPos.Mutable nextPos = pos.toMutable();
        for (int i = 0; i < length; i++) {
            if (reader.isAirBlock(nextPos) && isDirtAt(reader, nextPos.down())) {
                reader.setBlockState(nextPos, log, 2);
                if (mossy) placeCaribouMossOnLog(reader, nextPos, direction, rand);
                success = true;
            }

            nextPos.move(direction);
        }

        return success;
    }

    private void placeCaribouMossOnLog(ISeedReader reader, BlockPos pos, Direction logDirection, Random rand) {
        BlockPos up = pos.up();
        if (reader.isAirBlock(up) && rand.nextFloat() < 0.66f) {
            BlockState moss = ModBlocks.CARIBOU_MOSS.getBlock().getDefaultState();
            reader.setBlockState(up, moss, 2);
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos sidePos = pos.offset(direction);
            if (direction.getAxis() != logDirection.getAxis() && reader.isAirBlock(sidePos) && rand.nextFloat() < 0.66) {
                BlockState moss = ModBlocks.CARIBOU_MOSS_WALL.getDefaultState();
                reader.setBlockState(sidePos, moss.with(CaribouMossWallBlock.FACING, direction), 2);
            }
        }
    }
}
