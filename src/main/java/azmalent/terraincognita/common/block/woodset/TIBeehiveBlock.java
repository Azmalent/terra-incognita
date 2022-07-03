package azmalent.terraincognita.common.block.woodset;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class TIBeehiveBlock extends BeehiveBlock {
    public TIBeehiveBlock(Properties properties) {
        super(properties);

        Set<Block> validBlocks = Sets.newHashSet(this);
        validBlocks.addAll(BlockEntityType.BEEHIVE.validBlocks);
        BlockEntityType.BEEHIVE.validBlocks = ImmutableSet.copyOf(validBlocks);
    }
}
