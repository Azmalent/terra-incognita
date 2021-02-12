package azmalent.terraincognita.common.block.blocksets;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.trees.AppleFruitBlock;
import azmalent.terraincognita.common.block.trees.AppleLeavesBlock;
import azmalent.terraincognita.common.block.trees.HazelnutBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.item.ModFoods;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public final class HazelWoodType extends ModWoodType {
    public final BlockEntry HAZELNUT;
    public final RegistryObject<Item> HAZELNUT_ITEM;

    public HazelWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor, BooleanOption condition) {
        super(id, tree, woodColor, barkColor, condition);

        HAZELNUT = ModBlocks.HELPER.newBuilder("hazelnut", HazelnutBlock::new).withoutItemForm().withRenderType(BlockRenderType.CUTOUT).build();
        HAZELNUT_ITEM = ModItems.ITEMS.register("hazelnut", () -> new Item(new Item.Properties().group(TerraIncognita.TAB).food(ModFoods.HAZELNUT)));
    }
}
