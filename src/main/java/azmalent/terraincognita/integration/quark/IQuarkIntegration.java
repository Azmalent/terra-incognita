package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyDummy;

public interface IQuarkIntegration {
    void sayHi();

    @ModProxyDummy("quark")
    class Dummy implements IQuarkIntegration {
        @Override
        public void sayHi() {

        }
    }
}
