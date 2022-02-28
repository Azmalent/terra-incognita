package azmalent.terraincognita.common;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

@SuppressWarnings("unused")
public class ModBlockTags {
    public static void init() {
        //Called to force static constructor
    }

    public static final Tag.Named<Block> APPLE_LOGS = createTag("apple_logs");
    public static final Tag.Named<Block> HAZEL_LOGS = createTag("hazel_logs");

    public static final Tag.Named<Block> LOTUSES = createTag("lotuses");
    public static final Tag.Named<Block> SWEET_PEAS = createTag("sweet_peas");

    public static final Tag.Named<Block> HANGING_MOSS_PLANTABLE_ON = createTag("hanging_moss_plantable_on");
    public static final Tag.Named<Block> PEAT_MULTIBLOCK_PLANTS = createTag("peat_multiblock_plants");

    private static Tag.Named<Block> createTag(String name) {
        return BlockTags.bind(TerraIncognita.prefix(name).toString());
    }
}
