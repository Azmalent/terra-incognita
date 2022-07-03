package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.integration.IModProxy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public sealed interface IQuarkProxy extends IModProxy permits QuarkDummy, QuarkIntegration {
    boolean namesMatch(ItemStack stack);

    boolean canCutVines();
}
