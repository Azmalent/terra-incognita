package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.lib.integration.IModProxy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public interface IQuarkProxy extends IModProxy {
    boolean canLanternConnect(BlockState state, LevelReader level, BlockPos pos);

    boolean canCutVines();
}
