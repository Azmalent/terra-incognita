package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.integration.quark.block.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;

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

        VERTICAL_PLANKS = REG_HELPER.createBlock("vertical_" + name + "_planks", Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        VERTICAL_SLAB   = REG_HELPER.createBlock(name + "_vertical_slab", TIVerticalSlabBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        BOOKSHELF       = REG_HELPER.createBlock(name + "_bookshelf", TIBookshelfBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(1.5F).sound(SoundType.WOOD)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        LADDER          = REG_HELPER.createBlock(name + "_ladder", LadderBlock::new, Block.Properties.copy(Blocks.LADDER)).cutoutRender().build();
        POST            = REG_HELPER.createBlock(name + "_post", () -> new TIWoodPostBlock(barkColor)).cutoutRender().build();
        STRIPPED_POST   = REG_HELPER.createBlock("stripped_" + name + "_post", () -> new TIWoodPostBlock(woodColor)).cutoutRender().build();
        HEDGE           = REG_HELPER.createBlock(name + "_hedge", () -> new TIHedgeBlock(woodColor)).cutoutMippedRender().build();
        LEAF_CARPET     = REG_HELPER.createBlock(name + "_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
    }
}

