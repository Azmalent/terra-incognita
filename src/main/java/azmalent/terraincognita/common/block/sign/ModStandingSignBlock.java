package azmalent.terraincognita.common.block.sign;

import azmalent.terraincognita.common.registry.ModBlockEntities;
import azmalent.terraincognita.common.woodtype.ModWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
public class ModStandingSignBlock extends StandingSignBlock {
    public ModStandingSignBlock(ModWoodType woodType) {
        super(Block.Properties.of(Material.WOOD, woodType.woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodType.OAK);
    }

    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.SIGN.get().create(pos, state);
    }
}
