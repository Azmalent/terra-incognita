package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.integration.IModProxy;
import azmalent.cuneiform.lib.integration.ModProxy;
import azmalent.terraincognita.common.integration.quark.IQuarkProxy;

public class ModIntegration {
    @ModProxy("quark")
    public static IQuarkProxy QUARK;

    @ModProxy("farmersdelight")
    public static IModProxy FARMERS_DELIGHT;
}
