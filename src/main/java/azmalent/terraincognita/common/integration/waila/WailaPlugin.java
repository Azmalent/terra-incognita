package azmalent.terraincognita.common.integration.waila;

import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import mcp.mobius.waila.api.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

@mcp.mobius.waila.api.WailaPlugin
public class WailaPlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(new ButterflyProvider(), TooltipPosition.BODY, ButterflyEntity.class);
    }

    private static class ButterflyProvider implements IEntityComponentProvider {
        @Override
        public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
            ButterflyEntity butterfly = (ButterflyEntity) accessor.getEntity();

            String key = butterfly.getButterflyType().getTranslationKey();
            tooltip.add(new TranslationTextComponent(key));
        }
    }
}
