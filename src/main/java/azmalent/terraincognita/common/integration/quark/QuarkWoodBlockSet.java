package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.cuneiform.lib.util.DataUtil;
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

import java.util.Map;

public class QuarkWoodBlockSet {
    public final BlockEntry VERTICAL_PLANKS;
    public final BlockEntry VERTICAL_SLAB;
    public final BlockEntry BOOKSHELF;
    public final BlockEntry LADDER;
    public final BlockEntry POST;
    public final BlockEntry STRIPPED_POST;
    public final BlockEntry HEDGE;
    public final BlockEntry LEAF_CARPET;

    public QuarkWoodBlockSet(String id, MaterialColor barkColor, MaterialColor woodColor) {
        VERTICAL_PLANKS = ModBlocks.HELPER.newBuilder("vertical_" + id + "_planks", Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).withItemProperties(new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).build();
        VERTICAL_SLAB   = ModBlocks.HELPER.newBuilder(id + "_vertical_slab", () -> new TIVerticalSlabBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD))).withItemProperties(new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).build();
        BOOKSHELF       = ModBlocks.HELPER.newBuilder(id + "_bookshelf", Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(1.5F).sound(SoundType.WOOD)).withItemProperties(new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).build();
        LADDER          = ModBlocks.HELPER.newBuilder(id + "_ladder", () -> new LadderBlock(Block.Properties.from(Blocks.LADDER))).cutoutRender().build();
        POST            = ModBlocks.HELPER.newBuilder(id + "_post", () -> new TIWoodPostBlock(barkColor)).cutoutRender().build();
        STRIPPED_POST   = ModBlocks.HELPER.newBuilder("stripped_" + id + "_post", () -> new TIWoodPostBlock(woodColor)).cutoutRender().build();
        HEDGE           = ModBlocks.HELPER.newBuilder(id + "_hedge", () -> new TIHedgeBlock(woodColor)).cutoutMippedRender().build();
        LEAF_CARPET     = ModBlocks.HELPER.newBuilder(id + "_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
    }

    public void initFuelValues(Map<Item, Integer> fuelValues) {
        fuelValues.put(VERTICAL_PLANKS.getItem(), 300);
        fuelValues.put(BOOKSHELF.getItem(), 300);
        fuelValues.put(LADDER.getItem(), 300);
        fuelValues.put(POST.getItem(), 300);
        fuelValues.put(STRIPPED_POST.getItem(), 300);
    }

    public void initFlammability() {
        DataUtil.registerFlammable(VERTICAL_PLANKS.getBlock(), 5, 20);
        DataUtil.registerFlammable(BOOKSHELF.getBlock(), 30, 20);
        DataUtil.registerFlammable(POST.getBlock(), 5, 20);
        DataUtil.registerFlammable(STRIPPED_POST.getBlock(), 5, 20);
        DataUtil.registerFlammable(HEDGE.getBlock(), 5, 20);
    }
}

