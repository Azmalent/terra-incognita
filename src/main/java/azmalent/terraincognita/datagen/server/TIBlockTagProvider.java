package azmalent.terraincognita.datagen.server;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.ModBlockTags;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import static azmalent.terraincognita.common.registry.ModBlocks.*;

public class TIBlockTagProvider extends BlockTagsProvider {
    private final TagKey<Block> BOOKSHELVES = blockTag("forge", "bookshelves");

    private final TagKey<Block> HEDGES = blockTag("quark", "hedges");
    private final TagKey<Block> LADDERS = blockTag("quark", "ladders");
    private final TagKey<Block> VERTICAL_SLABS = blockTag("quark", "vertical_slab");
    private final TagKey<Block> WOODEN_VERTICAL_SLABS = blockTag("quark", "wooden_vertical_slabs");

    private final TagKey<Block> LEAF_PILES = blockTag("blueprint", "leaf_piles");

    private final TagKey<Block> SNOW_CONTAINABLE = blockTag("snowrealmagic", "containables");

    public TIBlockTagProvider(DataGenerator gen, @Nullable ExistingFileHelper exFileHelper) {
        super(gen, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void addTags() {
        addMineableTags();
        addFlowerTags();

        FLOWER_POTS.forEach((name, pot) -> tag(BlockTags.FLOWER_POTS).add(pot.get()));

        addLogTag(ModBlockTags.APPLE_LOGS, ModWoodTypes.APPLE);
        addLogTag(ModBlockTags.GINKGO_LOGS, ModWoodTypes.GINKGO);
        addLogTag(ModBlockTags.HAZEL_LOGS, ModWoodTypes.HAZEL);
        addLogTag(ModBlockTags.LARCH_LOGS, ModWoodTypes.LARCH);

        ModWoodTypes.VALUES.forEach(type -> {
            tag(BlockTags.BEEHIVES).add(type.BEEHIVE.get());
            tag(BlockTags.CLIMBABLE).add(type.LADDER.get());
            tag(BlockTags.FENCE_GATES).add(type.FENCE_GATE.get());
            tag(BlockTags.GUARDED_BY_PIGLINS).add(type.CHEST.get()).add(type.TRAPPED_CHEST.get());
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
            tag(BlockTags.WOODEN_TRAPDOORS).add(type.TRAPDOOR.get());

            //Forge
            tag(Tags.Blocks.CHESTS_WOODEN).add(type.CHEST.get());
            tag(Tags.Blocks.CHESTS_TRAPPED).add(type.TRAPPED_CHEST.get());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(type.FENCE_GATE.get());
            tag(Tags.Blocks.FENCES_WOODEN).add(type.FENCE.get());
            tag(BOOKSHELVES).add(type.BOOKSHELF.get());

            //Blueprint
            tag(LEAF_PILES).add(type.LEAF_PILE.get());

            //Quark
            tag(HEDGES).add(type.HEDGE.get());
            tag(LADDERS).add(type.LADDER.get());
            tag(VERTICAL_SLABS).add(type.VERTICAL_SLAB.get());
            tag(WOODEN_VERTICAL_SLABS).add(type.VERTICAL_SLAB.get());
        });

        tag(BlockTags.LEAVES).add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get());
        tag(HEDGES).add(ModWoodTypes.APPLE.BLOSSOMING_HEDGE.get());
        tag(LEAF_PILES).add(ModWoodTypes.APPLE.BLOSSOMING_LEAF_PILE.get());

        tag(BlockTags.ANIMALS_SPAWNABLE_ON).add(FLOWERING_GRASS.get());
        tag(BlockTags.BEE_GROWABLES).add(SOUR_BERRY_SPROUTS.get()).add(SOUR_BERRY_BUSH.get());
        tag(BlockTags.CLIMBABLE).addTag(ModBlockTags.SWEET_PEAS);
        tag(BlockTags.DIRT).add(FLOWERING_GRASS.get()).add(PEAT.get());
        tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(WICKER_MAT.get());
        tag(BlockTags.VALID_SPAWN).add(FLOWERING_GRASS.get());

        tag(Tags.Blocks.GRAVEL).add(MOSSY_GRAVEL.get());

        tag(ModBlockTags.HANGING_MOSS_PLANTABLE_ON).add(Blocks.MOSS_BLOCK).addTag(BlockTags.LOGS)
            .addTag(Tags.Blocks.STONE).addTag(Tags.Blocks.COBBLESTONE);

        tag(ModBlockTags.PEAT_MULTIBLOCK_PLANTS).add(Blocks.SUGAR_CANE).add(SWAMP_REEDS.get()).add(Blocks.BAMBOO)
            .add(Blocks.KELP).add(Blocks.KELP_PLANT);

        //Misc mods
        tag(SNOW_CONTAINABLE).add(CARIBOU_MOSS.get()).add(CARIBOU_MOSS_WALL.get());
    }

    private void addFlowerTags() {
        LOTUSES.forEach(lotus -> tag(ModBlockTags.LOTUSES).add(lotus.get()));
        SWEET_PEAS.forEach(sweetPea -> tag(ModBlockTags.SWEET_PEAS).add(sweetPea.get()));

        SMALL_FLOWERS.forEach(flower -> tag(BlockTags.SMALL_FLOWERS).add(flower.get()));
        TALL_FLOWERS.forEach(flower -> tag(BlockTags.TALL_FLOWERS).add(flower.get()));

        tag(BlockTags.FLOWERS)
            .addTag(ModBlockTags.LOTUSES)
            .addTag(ModBlockTags.SWEET_PEAS)
            .add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get())
            .add(CACTUS_FLOWER.get());
    }

    private void addLogTag(TagKey<Block> tag, TIWoodType woodType) {
        tag(tag).add(woodType.LOG.get()).add(woodType.STRIPPED_LOG.get())
            .add(woodType.WOOD.get()).add(woodType.STRIPPED_WOOD.get());

        tag(BlockTags.LOGS_THAT_BURN).addTag(tag);
    }

    private void addMineableTags() {
        ModWoodTypes.VALUES.forEach(type -> {
            tag(BlockTags.MINEABLE_WITH_AXE)
                .add(type.BOOKSHELF.get())
                .add(type.CHEST.get())
                .add(type.TRAPPED_CHEST.get())
                .add(type.LADDER.get())
                .add(type.POST.get())
                .add(type.STRIPPED_POST.get())
                .add(type.BEEHIVE.get())
                .add(type.HEDGE.get());

            tag(BlockTags.MINEABLE_WITH_HOE)
                .add(type.LEAVES.get())
                .add(type.LEAF_CARPET.get());
        });

        tag(BlockTags.MINEABLE_WITH_AXE)
            .add(ModWoodTypes.APPLE.BLOSSOMING_HEDGE.get())
            .add(WICKER_LANTERN.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
            .add(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET.get())
            .add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get())
            .add(SWAMP_REEDS_BUNDLE.get())
            .add(HAZELNUT_SACK.get())
            .add(SOUR_BERRY_SACK.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .add(FLOWERING_GRASS.get())
            .add(PEAT.get())
            .add(TILLED_PEAT.get());
    }

    private TagKey<Block> blockTag(String modid, String id) {
        return BlockTags.create(new ResourceLocation(modid, id));
    }
}