package azmalent.terraincognita.common.block.woodtypes;

import azmalent.terraincognita.common.block.trees.FruitLeavesBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public final class HazelWoodType extends ModWoodType {
    public HazelWoodType(String id, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(id, tree, woodColor, barkColor);
    }

    @Override
    protected Block createLeaves() {
        return new FruitLeavesBlock(ModBlocks.HAZELNUT.defaultBlockState(), 50);
    }
}
