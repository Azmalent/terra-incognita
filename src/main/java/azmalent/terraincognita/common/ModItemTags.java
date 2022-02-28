package azmalent.terraincognita.common;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;

@SuppressWarnings("unused")
public class ModItemTags {
    public static void init() {
        //Called to force static constructor
    }

    public static final Tag.Named<Item> APPLE_LOGS = createTag("apple_logs");
    public static final Tag.Named<Item> HAZEL_LOGS = createTag("hazel_logs");

    public static final Tag.Named<Item> LOTUSES = createTag("lotuses");
    public static final Tag.Named<Item> SWEET_PEAS = createTag("sweet_peas");

    public static final Tag.Named<Item> BASKET_STORABLE = createTag("basket_storable");

    private static Tag.Named<Item> createTag(String name) {
        return ItemTags.bind(TerraIncognita.prefix(name).toString());
    }
}
