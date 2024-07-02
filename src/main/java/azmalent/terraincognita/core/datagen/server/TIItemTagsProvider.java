package azmalent.terraincognita.core.datagen.server;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.ModBlockTags;
import azmalent.terraincognita.core.ModItemTags;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModItems;
import azmalent.terraincognita.core.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TIItemTagsProvider extends ItemTagsProvider {
    public TIItemTagsProvider(DataGenerator gen, BlockTagsProvider blockTags, ExistingFileHelper exFileHelper) {
        super(gen, blockTags, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void addTags() {
        //Quark tags
        TagKey<Item> boatableChests = ItemTags.create(new ResourceLocation("boatable_chests"));
        TagKey<Item> revertableChests = ItemTags.create(new ResourceLocation("revertable_chests"));

        for (TIWoodType type : ModWoodTypes.VALUES) {
            copy(type.name + "_logs");

            tag(ItemTags.BOATS).add(type.BOAT.asItem());

            tag(boatableChests).add(type.CHEST.asItem());
            tag(revertableChests).add(type.CHEST.asItem());
        }

        copy(ModBlockTags.LOTUSES, ModItemTags.LOTUSES);
        copy(ModBlockTags.SWEET_PEAS, ModItemTags.SWEET_PEAS);
        tag(ItemTags.FLOWERS).addTag(ModItemTags.LOTUSES).addTag(ModItemTags.SWEET_PEAS);

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

        tag(ItemTags.FOX_FOOD).add(ModItems.SOUR_BERRIES.asItem());
        tag(ItemTags.PIGLIN_LOVED).add(ModItems.NOTCH_CARROT.asItem());

        copy(Tags.Blocks.CHESTS_WOODEN, Tags.Items.CHESTS_WOODEN);
        copy(Tags.Blocks.CHESTS_TRAPPED, Tags.Items.CHESTS_TRAPPED);
        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        copy(Tags.Blocks.GRAVEL, Tags.Items.GRAVEL);

        TagKey<Item> sourBerrySeeds = ItemTags.create(new ResourceLocation("forge", "seeds/sour_berries"));
        tag(sourBerrySeeds).add(ModBlocks.SOUR_BERRY_SPROUTS.asItem());
        tag(Tags.Items.SEEDS).addTag(sourBerrySeeds);

        TagKey<Item> berries = ItemTags.create(new ResourceLocation("forge", "berries"));
        tag(berries).add(ModBlocks.SOUR_BERRY_SPROUTS.asItem());

        copy(new ResourceLocation("quark", "hedges"));
        copy(new ResourceLocation("quark", "ladders"));
        copy(new ResourceLocation("quark", "vertical_slab"));
        copy(new ResourceLocation("quark", "wooden_vertical_slabs"));

        copy(new ResourceLocation("farmersdelight", "cabinets/wooden"));

        tag(ItemTags.create(new ResourceLocation("quark", "seed_pouch_holdable"))).add(ModBlocks.SOUR_BERRY_SPROUTS.asItem());
    }

    private void copy(String tagName) {
        copy(TerraIncognita.prefix(tagName));
    }

    private void copy(ResourceLocation tagName) {
        TagKey<Block> blockTag = BlockTags.create(tagName);
        TagKey<Item> itemTag = ItemTags.create(tagName);
        copy(blockTag, itemTag);
    }
}
