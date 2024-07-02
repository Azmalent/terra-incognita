package azmalent.terraincognita.core.datagen.server;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.ModBlockTags;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("DataFlowIssue")
public class TIBlockTagsProvider extends BlockTagsProvider {
    public TIBlockTagsProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void addTags() {
        //Quark
        TagKey<Block> ladders = BlockTags.create(new ResourceLocation("quark", "ladders"));
        TagKey<Block> verticalSlabs = BlockTags.create(new ResourceLocation("quark", "vertical_slab"));
        TagKey<Block> woodenVerticalSlab = BlockTags.create(new ResourceLocation("quark", "wooden_vertical_slabs"));

        //Abnormals' Mods
        TagKey<Block> leafPiles = BlockTags.create(new ResourceLocation("blueprint", "leaf_piles"));

        //Farmer's Delight
        TagKey<Block> cabinets = BlockTags.create(new ResourceLocation("farmersdelight", "cabinets/wooden"));

        //Wood types
        for (TIWoodType type : ModWoodTypes.VALUES) {
            TagKey<Block> logs = ModBlockTags.createTag(type.name + "_logs");
            tag(logs).add(type.LOG.get())
                .add(type.STRIPPED_LOG.get())
                .add(type.WOOD.get())
                .add(type.STRIPPED_WOOD.get());

            tag(BlockTags.LOGS_THAT_BURN).addTag(logs);

            tag(BlockTags.BEEHIVES).add(type.BEEHIVE.get());
            tag(BlockTags.CLIMBABLE).add(type.LADDER.get());
            tag(BlockTags.FENCE_GATES).add(type.FENCE_GATE.get());
            tag(BlockTags.LEAVES).add(type.LEAVES.get());
            tag(BlockTags.PLANKS).add(type.PLANKS.get()).add(type.VERTICAL_PLANKS.get());
            tag(BlockTags.SAPLINGS).add(type.SAPLING.get());
            tag(BlockTags.STANDING_SIGNS).add(type.SIGN.get());
            tag(BlockTags.WALL_SIGNS).add(type.WALL_SIGN.get());
            tag(BlockTags.WOODEN_BUTTONS).add(type.BUTTON.get());
            tag(BlockTags.WOODEN_DOORS).add(type.DOOR.get());
            tag(BlockTags.WOODEN_FENCES).add(type.FENCE.get());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(type.PRESSURE_PLATE.get());
            tag(BlockTags.WOODEN_SLABS).add(type.SLAB.get());
            tag(BlockTags.WOODEN_STAIRS).add(type.STAIRS.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(type.TRAPDOOR.get());

            tag(BlockTags.GUARDED_BY_PIGLINS).add(type.CHEST.get()).add(type.TRAPPED_CHEST.get());

            tag(BlockTags.MINEABLE_WITH_AXE)
                .add(type.BOOKSHELF.get())
                .add(type.CHEST.get())
                .add(type.HEDGE.get())
                .add(type.LADDER.get())
                .add(type.POST.get())
                .add(type.STRIPPED_POST.get())
                .add(type.TRAPPED_CHEST.get())
                .add(type.BOARDS.get());

            tag(BlockTags.MINEABLE_WITH_HOE)
                .add(type.LEAVES.get())
                .add(type.LEAF_CARPET.get())
                .add(type.LEAF_PILE.get());

            tag(Tags.Blocks.CHESTS_WOODEN).add(type.CHEST.get());
            tag(Tags.Blocks.CHESTS_TRAPPED).add(type.TRAPPED_CHEST.get());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(type.FENCE_GATE.get());
            tag(Tags.Blocks.FENCES_WOODEN).add(type.FENCE.get());

            tag(ModBlockTags.HEDGES).add(type.HEDGE.get());
            tag(ladders).add(type.LADDER.get());
            tag(verticalSlabs).add(type.VERTICAL_SLAB.get());
            tag(woodenVerticalSlab).add(type.VERTICAL_SLAB.get());

            tag(leafPiles).add(type.LEAF_PILE.get());

            tag(cabinets).add(type.CABINET.get());
        }

        tag(BlockTags.LEAVES).add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get());
        tag(ModBlockTags.HEDGES).add(ModWoodTypes.APPLE.BLOSSOMING_HEDGE.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
            .add(ModWoodTypes.APPLE.BLOSSOMING_HEDGE.get())
            .add(ModBlocks.WICKER_LANTERN.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
            .add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get())
            .add(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET.get())
            .add(ModBlocks.HAZELNUT_SACK.get())
            .add(ModBlocks.SOUR_BERRY_SACK.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .add(ModBlocks.FLOWERING_GRASS.get())
            .add(ModBlocks.PEAT.get())
            .add(ModBlocks.TILLED_PEAT.get());

        //Flowers
        ModBlocks.FLOWERS.stream().filter(flower -> flower != ModBlocks.CACTUS_FLOWER).map(BlockEntry::get).forEach(flower -> {
            TagKey<Block> flowerTag = flower instanceof DoublePlantBlock ? BlockTags.TALL_FLOWERS : BlockTags.SMALL_FLOWERS;
            tag(flowerTag).add(flower);
        });

        TagKey<Block> lotuses = ModBlockTags.createTag("lotuses");
        ModBlocks.LOTUSES.forEach(lotus -> tag(lotuses).add(lotus.get()));

        TagKey<Block> sweetPeas = ModBlockTags.createTag("sweet_peas");
        ModBlocks.SWEET_PEAS.forEach(sweetPea -> tag(sweetPeas).add(sweetPea.get()));

        tag(BlockTags.FLOWERS).addTag(lotuses).addTag(sweetPeas).add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get());
        tag(BlockTags.CLIMBABLE).addTag(sweetPeas);

        //Flower pots
        ForgeRegistries.BLOCKS.getKeys().stream()
            .filter(key -> key.getNamespace().equals(TerraIncognita.MODID) && key.getPath().startsWith("potted_"))
            .map(ForgeRegistries.BLOCKS::getValue)
            .forEach(block -> tag(BlockTags.FLOWER_POTS).add(block));

        //Misc
        tag(Tags.Blocks.GRAVEL).add(ModBlocks.MOSSY_GRAVEL.get());
    }
}
