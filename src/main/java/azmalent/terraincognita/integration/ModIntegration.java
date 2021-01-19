package azmalent.terraincognita.integration;

import azmalent.cuneiform.lib.compat.ModProxy;
import azmalent.terraincognita.integration.quark.IQuarkIntegration;
import azmalent.terraincognita.integration.simplytea.ISimplyTeaIntegration;

public class ModIntegration {
    @ModProxy("quark")
    public static IQuarkIntegration QUARK;

    @ModProxy("simplytea")
    public static ISimplyTeaIntegration SIMPLY_TEA;
}
