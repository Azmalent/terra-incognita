package azmalent.terraincognita.common.block.woodtypes;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.common.block.trees.FruitLeavesBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;

public final class AppleWoodType extends ModWoodType {
    public final BlockEntry BLOSSOMING_LEAVES;

    public AppleWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(id, tree, woodColor, barkColor);
        BLOSSOMING_LEAVES = ModBlocks.HELPER.newBuilder("blossoming_" + id + "_leaves", this::createLeaves).cutoutMippedRender().build();
    }

    @Override
    protected Block createLeaves() {
        return new FruitLeavesBlock(ModBlocks.APPLE.getDefaultState(), 100);
    }

    @Override
    public void initFlammability() {
        super.initFlammability();
        DataUtil.registerFlammable(BLOSSOMING_LEAVES, 30, 60);
    }
}
