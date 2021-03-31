package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxy;
import azmalent.terraincognita.common.integration.quark.IQuarkIntegration;

public class ModIntegration {
    @ModProxy("quark")
    public static IQuarkIntegration QUARK;

    @ModProxy("buzzier_bees")
    public static IModIntegration BUZZIER_BEES;

    @ModProxy("upgrade_aquatic")
    public static IModIntegration UPGRADE_AQUATIC;

    @ModProxy("farmersdelight")
    public static IModIntegration FARMERS_DELIGHT;

    @ModProxy("simplytea")
    public static IModIntegration SIMPLY_TEA;
}
