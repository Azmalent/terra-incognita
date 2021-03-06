package azmalent.terraincognita.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("azmalent.terraincognita.mixin.compat.quark")) {
            try {
                Class.forName("vazkii.quark.base.Quark");
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        if (mixinClassName.equals("azmalent.terraincognita.mixin.compat.botania.HornHarvestablesMixin")) {
            try {
                Class.forName("vazkii.botania.api.item.IHornHarvestable");
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
