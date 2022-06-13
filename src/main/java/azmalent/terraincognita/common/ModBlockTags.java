package azmalent.terraincognita.common;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
public class ModBlockTags {
    public static void init() {
        //Called to force static constructor
    }

    public static final TagKey<Block> APPLE_LOGS = createTag("apple_logs");
    public static final TagKey<Block> GINKGO_LOGS = createTag("ginkgo_logs");
    public static final TagKey<Block> HAZEL_LOGS = createTag("hazel_logs");
    public static final TagKey<Block> LARCH_LOGS = createTag("larch_logs");

    public static final TagKey<Block> LOTUSES = createTag("lotuses");
    public static final TagKey<Block> SWEET_PEAS = createTag("sweet_peas");

    public static final TagKey<Block> HANGING_MOSS_PLANTABLE_ON = createTag("hanging_moss_plantable_on");
    public static final TagKey<Block> PEAT_MULTIBLOCK_PLANTS = createTag("peat_multiblock_plants");

    private static TagKey<Block> createTag(String name) {
        return BlockTags.create(TerraIncognita.prefix(name));
    }
}
