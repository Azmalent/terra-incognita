package azmalent.terraincognita.integration.waila;

import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import mcp.mobius.waila.api.EntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;

public class ButterflyWailaProvider implements IEntityComponentProvider {
    public static ButterflyWailaProvider INSTANCE = new ButterflyWailaProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (accessor.getEntity() instanceof Butterfly butterfly) {
            tooltip.add(butterfly.getTypeDisplayName());
        }
    }
}
