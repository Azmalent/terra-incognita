package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.integration.quark.block.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;

public class QuarkWoodBlockSet {
    public final BlockEntry<TIVerticalSlabBlock> VERTICAL_SLAB;
    public final BlockEntry<TIBookshelfBlock> BOOKSHELF;
    public final BlockEntry<LadderBlock> LADDER;
    public final BlockEntry<TIWoodPostBlock> POST;
    public final BlockEntry<TIWoodPostBlock> STRIPPED_POST;
    public final BlockEntry<TIHedgeBlock> HEDGE;
    public final BlockEntry<TILeafCarpetBlock> LEAF_CARPET;

    public QuarkWoodBlockSet(TIWoodType woodType) {
        String name = woodType.name;
        MaterialColor barkColor = woodType.barkColor;
        MaterialColor woodColor = woodType.woodColor;

        VERTICAL_SLAB   = REG_HELPER.createBlock(name + "_vertical_slab", TIVerticalSlabBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        BOOKSHELF       = REG_HELPER.createBlock(name + "_bookshelf", TIBookshelfBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(1.5F).sound(SoundType.WOOD)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        LADDER          = REG_HELPER.createBlock(name + "_ladder", LadderBlock::new, Block.Properties.copy(Blocks.LADDER)).cutoutRender().build();
        POST            = REG_HELPER.createBlock(name + "_post", () -> new TIWoodPostBlock(barkColor)).cutoutRender().build();
        STRIPPED_POST   = REG_HELPER.createBlock("stripped_" + name + "_post", () -> new TIWoodPostBlock(woodColor)).cutoutRender().build();
        HEDGE           = REG_HELPER.createBlock(name + "_hedge", () -> new TIHedgeBlock(woodColor)).cutoutMippedRender().build();
        LEAF_CARPET     = REG_HELPER.createBlock(name + "_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
    }
}

