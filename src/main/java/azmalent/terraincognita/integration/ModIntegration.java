package azmalent.terraincognita.integration;

import azmalent.cuneiform.integration.IModProxy;
import azmalent.cuneiform.integration.ModProxy;
import azmalent.terraincognita.integration.farmersdelight.FarmersDelightProxy;
import azmalent.terraincognita.integration.quark.IQuarkProxy;

public class ModIntegration {
    @ModProxy("quark")
    public static IQuarkProxy QUARK;

    @ModProxy("farmersdelight")
    public static FarmersDelightProxy FARMERS_DELIGHT;
}
