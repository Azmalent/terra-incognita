package azmalent.terraincognita;

import azmalent.cuneiform.config.Comment;
import azmalent.cuneiform.config.ConfigFile;
import azmalent.cuneiform.config.Name;
import azmalent.cuneiform.config.options.MapOption;
import com.google.common.collect.Maps;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class TIMixinConfig extends ConfigFile {
    public static TIMixinConfig INSTANCE = new TIMixinConfig();

    private TIMixinConfig() {
        super(TerraIncognita.MODID, ModConfig.Type.COMMON);
    }

    //Spec is built when mixins are loading, so we just need to register the file
    //We also need to sync after potentially auto-adding mixins
    @Override
    public void register() {
        ModLoadingContext.get().registerConfig(configType, spec, this.getConfigFilename());
        sync();
    }

    @Override
    protected String getConfigFilename() {
        return modid + "-mixins.toml";
    }

    @Name("Terra Incognita Mixins")
    @Comment({"You can disable any mixin from Terra Incognita here.",
        "This WILL disable certain features of the mod, but can help if you are experiencing a mixin conflict with another mod.",
        "The key string is the mixin class name, relative to the base package, with slashes instead of dots.",
        "Example: azmalent.terraincognita.mixin.compat.quark.LanternBlockMixin -> compat/quark/LanternBlockMixin",
        "DON'T CHANGE ANYTHING IF YOU DON'T KNOW WHAT YOU ARE DOING!"
    })
    public MapOption<Boolean> mixins = MapOption.of(Maps.newTreeMap());
}
