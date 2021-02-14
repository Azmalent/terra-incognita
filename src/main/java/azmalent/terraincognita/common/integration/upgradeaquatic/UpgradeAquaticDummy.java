package azmalent.terraincognita.common.integration.upgradeaquatic;

import azmalent.cuneiform.lib.compat.ModProxyDummy;
import net.minecraftforge.eventbus.api.IEventBus;

@ModProxyDummy(IUpgradeAquaticIntegration.MODID)
public class UpgradeAquaticDummy implements IUpgradeAquaticIntegration {
    @Override
    public void register(IEventBus bus) {

    }
}
