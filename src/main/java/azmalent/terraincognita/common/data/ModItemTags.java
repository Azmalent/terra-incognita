package azmalent.terraincognita.common.data;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

@SuppressWarnings("unused")
public class ModItemTags {
    private static ITag.INamedTag<Item> createTag(String name) {
        return ItemTags.makeWrapperTag(TerraIncognita.prefix(name).toString());
    }

    public static final ITag.INamedTag<Item> APPLE_LOGS = createTag("apple_logs");
    public static final ITag.INamedTag<Item> HAZEL_LOGS = createTag("hazel_logs");

    public static final ITag.INamedTag<Item> LOTUSES = createTag("lotuses");
    public static final ITag.INamedTag<Item> SWEET_PEAS = createTag("sweet_peas");

    public static final ITag.INamedTag<Item> BASKET_STORABLE = createTag("basket_storable");
}
