package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.ModProxy;
import azmalent.terraincognita.common.integration.quark.IQuarkIntegration;
import azmalent.terraincognita.common.integration.simplytea.ISimplyTeaIntegration;
import azmalent.terraincognita.common.integration.upgradeaquatic.IUpgradeAquaticIntegration;

public class ModIntegration {
    @ModProxy("quark")
    public static IQuarkIntegration QUARK;

    @ModProxy("simplytea")
    public static ISimplyTeaIntegration SIMPLY_TEA;

    @ModProxy("upgrade_aquatic")
    public static IUpgradeAquaticIntegration UPGRADE_AQUATIC;

    public static final EnvironmentalIntegration ENVIRONMENTAL = new EnvironmentalIntegration();
}
