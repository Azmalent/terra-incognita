package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIMixinConfig;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    private boolean botaniaLoaded = false;

    private String getRelativeClassName(String className) {
        String packageName = getClass().getPackageName();
        return className.substring(packageName.length() + 1);
    }

    @Override
    public void onLoad(String mixinPackage) {
        TIMixinConfig.INSTANCE.buildSpec();
        TIMixinConfig.INSTANCE.sync();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        botaniaLoaded = classLoader.getResource("vazkii/botania/forge/ForgeCommonInitializer.class") != null;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String key = getRelativeClassName(mixinClassName).replace('.', '/');
        if (key.startsWith("accessor")) {
            return true;
        }

        //Ignore compat mixins if the corresponding mod is not loaded
        if (!botaniaLoaded && key.startsWith("compat/botania")) {
            return false;
        }

        return TIMixinConfig.INSTANCE.mixins.getMap().getOrDefault(key, true);
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
