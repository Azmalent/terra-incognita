package azmalent.terraincognita.datagen.server;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.ModBlockTags;
import azmalent.terraincognita.common.ModItemTags;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.integration.ModIntegration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class TIItemTagProvider extends ItemTagsProvider {
    private final TagKey<Item> BERRIES = itemTag("forge", "berries");
    private final TagKey<Item> SOUR_BERRY_SEEDS = itemTag("forge", "seeds/sour_berries");

    private final TagKey<Item> BOATABLE_CHESTS = itemTag("quark", "boatable_chests");
    private final TagKey<Item> REVERTABLE_CHESTS = itemTag("quark", "revertable_chests");
    private final TagKey<Item> REVERTABLE_TRAPPED_CHESTS = itemTag("quark", "revertable_trapped_chests");
    private final TagKey<Item> SEED_POUCH_HOLDABLE = itemTag("quark", "seed_pouch_holdable");

    private final TagKey<Item> CABINETS = itemTag("farmersdelight", "cabinets/wooden");

    private final TagKey<Item> UPRIGHT_ON_BELT = itemTag("create", "upright_on_belt");

    private final TagKey<Item> SQUIRREL_TEMPT_ITEMS = itemTag("ecologics", "squirrel_tempt_items");

    public TIItemTagProvider(DataGenerator gen, BlockTagsProvider blockTags, @Nullable ExistingFileHelper exFileHelper) {
        super(gen, blockTags, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void addTags() {
        copyBlockTags();

        ModWoodTypes.VALUES.forEach(type -> {
            tag(ItemTags.BOATS).add(type.BOAT.get());
            tag(ItemTags.SIGNS).add(type.SIGN.asItem());

            tag(BOATABLE_CHESTS).add(type.CHEST.asItem());
            tag(REVERTABLE_CHESTS).add(type.CHEST.asItem());
            tag(REVERTABLE_TRAPPED_CHESTS).add(type.TRAPPED_CHEST.asItem());

            tag(CABINETS).add(type.CABINET.asItem());
        });

        //Not copying because blossoming apple leaves probably don't need an item tag
        tag(ItemTags.FLOWERS).addTag(ModItemTags.LOTUSES).addTag(ModItemTags.SWEET_PEAS).add(ModBlocks.CACTUS_FLOWER.asItem());
        tag(ItemTags.FOX_FOOD).add(ModItems.SOUR_BERRIES.get());
        tag(ItemTags.PIGLIN_LOVED).add(ModItems.NOTCH_CARROT.get());

        tag(BERRIES).add(ModItems.SOUR_BERRIES.get());
        tag(SOUR_BERRY_SEEDS).add(ModBlocks.SOUR_BERRY_SPROUTS.asItem());
        tag(Tags.Items.SEEDS).addTag(SOUR_BERRY_SEEDS);

        tag(ModItemTags.BASKET_STORABLE)
            .addTag(ItemTags.SMALL_FLOWERS)
            .addTag(ItemTags.SAPLINGS)
            .addTag(Tags.Items.EGGS)
            .addTag(Tags.Items.MUSHROOMS)
            .addTag(Tags.Items.SEEDS)
            .addTag(BERRIES)
            .add(Items.TURTLE_EGG)
            .add(Items.SWEET_BERRIES)
            .add(ModItems.HAZELNUT.get())
            .addOptional(new ResourceLocation("abundance", "lavender"))
            .addOptional(new ResourceLocation("abundance", "sunflower_seeds"))
            .addOptional(new ResourceLocation("autumnity", "foul_berries"))
            .addOptional(new ResourceLocation("bayou_blues", "gooseberries"))
            .addOptional(new ResourceLocation("ecologics", "walnut"))
            .addOptional(new ResourceLocation("environmental", "cattail_seeds"))
            .addOptional(new ResourceLocation("environmental", "cherries"))
            .addOptional(new ResourceLocation("upgrade_aquatic", "blue_pickerelweed"))
            .addOptional(new ResourceLocation("upgrade_aquatic", "purple_pickerelweed"))
            .addOptional(new ResourceLocation("upgrade_aquatic", "mulberry"))
            .addOptional(new ResourceLocation("windswept", "chestnuts"))
            .addOptional(new ResourceLocation("windswept", "holly_berries"));

        tag(ModItemTags.BUTTERFLY_BANNER_ITEM).add(ModItems.BOTTLED_BUTTERFLY.get());
        tag(ModItemTags.FERN_BANNER_ITEM).add(ModItems.FERN_FIDDLEHEAD.get());
        tag(ModItemTags.FLEUR_DE_LIS_BANNER_ITEM).add(ModBlocks.WATER_FLAG.asItem());

        tag(SEED_POUCH_HOLDABLE).add(ModBlocks.SWAMP_REEDS.asItem());
        tag(UPRIGHT_ON_BELT).add(ModItems.SOUR_BERRY_JAM.asItem());
        tag(SQUIRREL_TEMPT_ITEMS).add(ModItems.HAZELNUT.get());
    }

    private void copyBlockTags() {
        copy(BlockTags.DIRT, ItemTags.DIRT);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copyModTag("minecraft", "fence_gates");

        copy(Tags.Blocks.CHESTS_TRAPPED, Tags.Items.CHESTS_TRAPPED);
        copy(Tags.Blocks.CHESTS_WOODEN, Tags.Items.CHESTS_WOODEN);
        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        copy(Tags.Blocks.GRAVEL, Tags.Items.GRAVEL);
        copyModTag("forge", "bookshelves");

        copy(ModBlockTags.APPLE_LOGS, ModItemTags.APPLE_LOGS);
        copy(ModBlockTags.GINKGO_LOGS, ModItemTags.GINKGO_LOGS);
        copy(ModBlockTags.HAZEL_LOGS, ModItemTags.HAZEL_LOGS);
        copy(ModBlockTags.LARCH_LOGS, ModItemTags.LARCH_LOGS);
        copy(ModBlockTags.LOTUSES, ModItemTags.LOTUSES);
        copy(ModBlockTags.SWEET_PEAS, ModItemTags.SWEET_PEAS);

        copyModTag("quark", "hedges");
        copyModTag("quark", "ladders");
        copyModTag("quark", "vertical_slab");
        copyModTag("quark", "wooden_vertical_slab");
    }

    private void copyModTag(String modid, String id) {
        copy(blockTag(modid, id), itemTag(modid, id));
    }

    private TagKey<Block> blockTag(String modid, String id) {
        return BlockTags.create(new ResourceLocation(modid, id));
    }

    private TagKey<Item> itemTag(String modid, String id) {
        return ItemTags.create(new ResourceLocation(modid, id));
    }
}
