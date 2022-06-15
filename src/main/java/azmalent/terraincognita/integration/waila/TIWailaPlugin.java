package azmalent.terraincognita.integration.waila;

import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
@SuppressWarnings("UnstableApiUsage")
public class TIWailaPlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerComponentProvider(ButterflyWailaProvider.INSTANCE, TooltipPosition.BODY, Butterfly.class);
    }
}
