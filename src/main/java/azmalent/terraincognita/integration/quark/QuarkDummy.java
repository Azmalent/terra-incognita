package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.integration.IntegrationDummy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;

@IntegrationDummy("quark")
public final class QuarkDummy implements IQuarkProxy {
    @Override
    public void register(IEventBus iEventBus) {
        //NO-OP
    }

    @Override
    public boolean namesMatch(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canCutVines() {
        return true;
    }
}
