package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;

@ModProxyImpl("quark")
public class QuarkIntegration implements IQuarkIntegration {
    @Override
    public void sayHi() {
        TerraIncognita.LOGGER.info("Hello from Quark!");
    }
}
