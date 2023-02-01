package azmalent.terraincognita.common;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class ModItemTags {
    public static void init() {
        //Called to force static constructor
    }

    public static final TagKey<Item> APPLE_LOGS  = createTag("apple_logs");
    public static final TagKey<Item> GINKGO_LOGS = createTag("ginkgo_logs");
    public static final TagKey<Item> HAZEL_LOGS  = createTag("hazel_logs");
    public static final TagKey<Item> LARCH_LOGS  = createTag("larch_logs");

    public static final TagKey<Item> LOTUSES    = createTag("lotuses");
    public static final TagKey<Item> SWEET_PEAS = createTag("sweet_peas");

    public static final TagKey<Item> BASKET_STORABLE = createTag("basket_storable");

    public static final TagKey<Item> BUTTERFLY_BANNER_ITEM    = createTag("banner_pattern/banner_item/butterfly");
    public static final TagKey<Item> FERN_BANNER_ITEM         = createTag("banner_pattern/banner_item/fern");
    public static final TagKey<Item> FLEUR_DE_LIS_BANNER_ITEM = createTag("banner_pattern/banner_item/fleur_de_lis");

    private static TagKey<Item> createTag(String name) {
        return ItemTags.create(TerraIncognita.prefix(name));
    }
}
