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

    public static final TagKey<Item> LOTUSES = createTag("lotuses");
    public static final TagKey<Item> SWEET_PEAS = createTag("sweet_peas");

    public static final TagKey<Item> BASKET_STORABLE = createTag("basket_storable");

    public static TagKey<Item> createTag(String name) {
        return ItemTags.create(TerraIncognita.prefix(name));
    }
}
