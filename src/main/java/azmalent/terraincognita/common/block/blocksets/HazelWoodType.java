package azmalent.terraincognita.common.block.blocksets;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.terraincognita.common.block.trees.FruitLeavesBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;

@SuppressWarnings("ConstantConditions")
public final class HazelWoodType extends ModWoodType {
    public HazelWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor, BooleanOption condition) {
        super(id, tree, woodColor, barkColor, condition);
    }

    @Override
    protected Block createLeaves() {
        return new FruitLeavesBlock(ModBlocks.HAZELNUT.getDefaultState(), 100);
    }
}
