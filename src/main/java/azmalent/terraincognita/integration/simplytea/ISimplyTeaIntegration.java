package azmalent.terraincognita.integration.simplytea;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import net.minecraftforge.eventbus.api.IEventBus;

public interface ISimplyTeaIntegration {
    void register(IEventBus bus);

    @ModProxyDummy("simplytea")
    class Dummy implements ISimplyTeaIntegration {
        @Override
        public void register(IEventBus bus) { }
    }
}
