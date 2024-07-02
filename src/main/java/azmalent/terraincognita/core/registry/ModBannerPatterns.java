package azmalent.terraincognita.core.registry;

import azmalent.terraincognita.TerraIncognita;
import com.google.common.collect.Maps;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ModBannerPatterns {
    private static final Item.Properties PATTERN_PROPS = new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC);

    public static final Map<BannerPattern, RegistryObject<BannerPatternItem>> PATTERN_ITEMS = Maps.newHashMap();
    public static BannerPattern FLEUR_DE_LIS;
    public static BannerPattern FERN;
    public static BannerPattern BUTTERFLY;

    public static void register() {
        FLEUR_DE_LIS = registerBanner("fleur_de_lis", "fdl");
        FERN = registerBanner("fern", "fern");
        BUTTERFLY = registerBanner("butterfly", "btrfly");
    }

    private static BannerPattern registerBanner(String name, String hashName) {
        String enumName = TerraIncognita.prefix(name).toString().replace(':', '_').toUpperCase();
        BannerPattern pattern = BannerPattern.create(enumName, "ti_" + name, "ti_" + hashName, true);

        String itemName = name + "_banner_pattern";
        RegistryObject<BannerPatternItem> item = ModItems.ITEMS.register(itemName, () -> new BannerPatternItem(pattern, PATTERN_PROPS));
        PATTERN_ITEMS.put(pattern, item);

        return pattern;
    }
}
