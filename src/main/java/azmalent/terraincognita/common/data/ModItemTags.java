package azmalent.terraincognita.common.data;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ModItemTags {
    private static ITag.INamedTag<Item> createTag(String name) {
        return ItemTags.makeWrapperTag(TerraIncognita.prefix(name).toString());
    }

    public static final ITag.INamedTag<Item> BASKET_STORABLE = createTag("basket_storable");
}
