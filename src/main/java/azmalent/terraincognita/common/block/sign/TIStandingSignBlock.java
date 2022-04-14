package azmalent.terraincognita.common.block.sign;

import azmalent.terraincognita.common.woodtype.TIWoodType;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;

import java.util.Set;

public class TIStandingSignBlock extends StandingSignBlock {
    public TIStandingSignBlock(TIWoodType woodType) {
        super(Block.Properties.of(Material.WOOD, woodType.woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType);

        Set<Block> validBlocks = Sets.newHashSet(this);
        validBlocks.addAll(BlockEntityType.SIGN.validBlocks);
        BlockEntityType.SIGN.validBlocks = ImmutableSet.copyOf(validBlocks);
    }

}
