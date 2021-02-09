package azmalent.terraincognita.common.data;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

@SuppressWarnings("unused")
public class ModBlockTags {
    private static ITag.INamedTag<Block> createTag(String name) {
        return BlockTags.makeWrapperTag(TerraIncognita.prefix(name).toString());
    }

    public static final ITag.INamedTag<Block> APPLE_LOGS = createTag("apple_logs");
    public static final ITag.INamedTag<Block> LOTUSES = createTag("lotuses");
    public static final ITag.INamedTag<Block> MOSS_PLACEABLE_ON = createTag("moss_placeable_on");
}
