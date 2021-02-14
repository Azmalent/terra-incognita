package azmalent.terraincognita.common.integration.simplytea;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import net.minecraftforge.eventbus.api.IEventBus;

@ModProxyDummy(ISimplyTeaIntegration.MODID)
public class SimplyTeaDummy implements ISimplyTeaIntegration {
    @Override
    public void register(IEventBus bus) { }
}
