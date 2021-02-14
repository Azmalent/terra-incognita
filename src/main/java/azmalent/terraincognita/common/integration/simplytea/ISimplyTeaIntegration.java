package azmalent.terraincognita.common.integration.simplytea;

import net.minecraftforge.eventbus.api.IEventBus;

public interface ISimplyTeaIntegration {
    String MODID = "simplytea";

    void register(IEventBus bus);
}
