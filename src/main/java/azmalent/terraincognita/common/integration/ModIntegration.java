package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.ModProxy;
import azmalent.terraincognita.common.integration.quark.IQuarkIntegration;
import azmalent.terraincognita.common.integration.simplytea.ISimplyTeaIntegration;

public class ModIntegration {
    @ModProxy("quark")
    public static IQuarkIntegration QUARK;

    @ModProxy("simplytea")
    public static ISimplyTeaIntegration SIMPLY_TEA;
}
