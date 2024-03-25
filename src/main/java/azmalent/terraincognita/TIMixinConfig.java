package azmalent.terraincognita;

import azmalent.cuneiform.config.Comment;
import azmalent.cuneiform.config.ConfigFile;
import azmalent.cuneiform.config.Name;
import azmalent.cuneiform.config.options.MapOption;
import com.google.common.collect.Maps;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TIMixinConfig extends ConfigFile {
    public static TIMixinConfig INSTANCE = new TIMixinConfig();

    private TIMixinConfig() {
        super(TerraIncognita.MODID, ModConfig.Type.COMMON);
    }

    @Override
    protected String getConfigFilename() {
        return modid + "-mixins.toml";
    }

    @Name("Terra Incognita Mixins")
    @Comment({"You can disable any mixin from Terra Incognita here.",
        "This WILL disable certain features of the mod, but can help if you are experiencing a mixin conflict with another mod.",
        "The key string is the mixin class name, relative to the base package, with slashes instead of dots.",
        "Example: azmalent.terraincognita.mixin.LanternBlockMixin -> compat/quark/LanternBlockMixin",
        "DON'T CHANGE ANYTHING IF YOU DON'T KNOW WHAT YOU ARE DOING!"
    })
    public MapOption<Boolean> mixins = MapOption.of(Maps.newLinkedHashMap(), map -> {
        map.put("compat/botania/HornHarvestablesMixin", true);
        map.put("CactusBlockMixin", true);
        map.put("EatGrassBlockMixin", true);
        map.put("LanternBlockMixin", true);
        map.put("NoiseProviderMixin", true);
        map.put("NoiseThresholdProviderMixin", true);
        map.put("SimpleBlockFeatureMixin", true);
        map.put("SugarCaneBlockMixin", true);
        map.put("VineBlockMixin", true);
    });
}
