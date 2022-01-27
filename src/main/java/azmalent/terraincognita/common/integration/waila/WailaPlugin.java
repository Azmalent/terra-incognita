package azmalent.terraincognita.common.integration.waila;

import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.config.IPluginConfig;

@mcp.mobius.waila.api.WailaPlugin
public class WailaPlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(new ButterflyProvider(), TooltipPosition.BODY, Butterfly.class);
    }

    private static class ButterflyProvider implements IEntityComponentProvider {
        @Override
        public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
            Butterfly butterfly = (Butterfly) accessor.getEntity();
            tooltip.add(butterfly.getTypeDisplayName());
        }
    }
}
