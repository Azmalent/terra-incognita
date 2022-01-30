package azmalent.terraincognita.common.block.woodtypes;

import azmalent.terraincognita.common.block.trees.FruitLeavesBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;

public final class HazelWoodType extends ModWoodType {
    public HazelWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(id, tree, woodColor, barkColor);
    }

    @Override
    protected Block createLeaves() {
        return new FruitLeavesBlock(ModBlocks.HAZELNUT.getDefaultState(), 20);
    }
}
