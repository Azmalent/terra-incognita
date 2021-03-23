package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.integration.quark.block.TIWoodPostBlock;
import azmalent.terraincognita.common.integration.quark.block.TIHedgeBlock;
import azmalent.terraincognita.common.integration.quark.block.TILeafCarpetBlock;
import azmalent.terraincognita.common.integration.quark.block.TIVerticalSlabBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class QuarkWoodBlockSet {
    public final BlockEntry VERTICAL_PLANKS;
    public final BlockEntry VERTICAL_SLAB;
    public final BlockEntry BOOKSHELF;
    public final BlockEntry LADDER;
    public final BlockEntry POST;
    public final BlockEntry STRIPPED_POST;
    public final BlockEntry HEDGE;
    public final BlockEntry LEAF_CARPET;

    public QuarkWoodBlockSet(ModWoodType woodType) {
        String name = woodType.name;
        MaterialColor barkColor = woodType.barkColor;
        MaterialColor woodColor = woodType.woodColor;

        VERTICAL_PLANKS = ModBlocks.HELPER.newBuilder("vertical_" + name + "_planks", Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        VERTICAL_SLAB   = ModBlocks.HELPER.newBuilder(name + "_vertical_slab", TIVerticalSlabBlock::new, Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        BOOKSHELF       = ModBlocks.HELPER.newBuilder(name + "_bookshelf", Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(1.5F).sound(SoundType.WOOD)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        LADDER          = ModBlocks.HELPER.newBuilder(name + "_ladder", LadderBlock::new, Block.Properties.from(Blocks.LADDER)).cutoutRender().build();
        POST            = ModBlocks.HELPER.newBuilder(name + "_post", () -> new TIWoodPostBlock(barkColor)).cutoutRender().build();
        STRIPPED_POST   = ModBlocks.HELPER.newBuilder("stripped_" + name + "_post", () -> new TIWoodPostBlock(woodColor)).cutoutRender().build();
        HEDGE           = ModBlocks.HELPER.newBuilder(name + "_hedge", () -> new TIHedgeBlock(woodColor)).cutoutMippedRender().build();
        LEAF_CARPET     = ModBlocks.HELPER.newBuilder(name + "_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
    }
}

