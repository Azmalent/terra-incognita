package azmalent.terraincognita.common;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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

    public static final TagKey<Block> ALPINE_FLOWERS_PLANTABLE_ON = createTag("alpine_flowers_plantable_on");
    public static final TagKey<Block> SAXIFRAGE_PLANTABLE_ON = createTag("saxifrage_plantable_on");
    public static final TagKey<Block> WATER_FLAG_PLANTABLE_ON = createTag("water_flag_plantable_on");

    public static final TagKey<Block> MULTIBLOCK_PLANTS = createTag("multiblock_plants");

    //Compat tags
    public static final TagKey<Block> HEDGES = createTag("quark", "hedges");

    private static TagKey<Block> createTag(String name) {
        return createTag(TerraIncognita.MODID, name);
    }

    private static TagKey<Block> createTag(String modid, String name) {
        return BlockTags.create(new ResourceLocation(modid, name));
    }
}
