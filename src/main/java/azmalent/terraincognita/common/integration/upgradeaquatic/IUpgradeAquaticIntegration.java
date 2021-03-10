package azmalent.terraincognita.common.integration.upgradeaquatic;

import net.minecraftforge.eventbus.api.IEventBus;

public interface IUpgradeAquaticIntegration {
    String MODID = "upgrade_aquatic";

    void register(IEventBus bus);
}
