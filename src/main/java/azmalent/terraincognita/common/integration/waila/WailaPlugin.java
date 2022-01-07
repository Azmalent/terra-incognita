package azmalent.terraincognita.common.integration.waila;

import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import mcp.mobius.waila.api.*;
import net.minecraft.network.chat.Component;

import java.util.List;

@mcp.mobius.waila.api.WailaPlugin
public class WailaPlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(new ButterflyProvider(), TooltipPosition.BODY, ButterflyEntity.class);
    }

    private static class ButterflyProvider implements IEntityComponentProvider {
        @Override
        public void appendBody(List<Component> tooltip, IEntityAccessor accessor, IPluginConfig config) {
            ButterflyEntity butterfly = (ButterflyEntity) accessor.getEntity();
            tooltip.add(butterfly.getTypeDisplayName());
        }
    }
}
