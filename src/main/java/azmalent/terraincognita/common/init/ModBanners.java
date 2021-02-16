package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import com.google.common.collect.Maps;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

public class ModBanners {
    private static final Item.Properties PATTERN_PROPS = new Item.Properties().maxStackSize(1).group(ItemGroup.MISC);

    public static final Map<BannerPattern, RegistryObject<BannerPatternItem>> PATTERN_ITEMS = Maps.newHashMap();
    public static BannerPattern FLEUR_DE_LIS;

    public static void init() {
        if (!TIConfig.Misc.bannerPatterns.get()) return;

        FLEUR_DE_LIS = register("fleur_de_lis", "fdl", true);
    }

    private static BannerPattern register(String name, String hashName, boolean hasItem) {
        String enumName = TerraIncognita.prefix(name).toString().replace(':', '_').toUpperCase();
        BannerPattern pattern = BannerPattern.create(enumName, "ti_" + name, "ti_" + hashName, hasItem);

        if (hasItem) {
            String itemName = name + "_banner_pattern";
            RegistryObject<BannerPatternItem> item = ModItems.ITEMS.register(itemName, () -> new BannerPatternItem(pattern, PATTERN_PROPS));
            PATTERN_ITEMS.put(pattern, item);
        }

        return pattern;
    }
}
