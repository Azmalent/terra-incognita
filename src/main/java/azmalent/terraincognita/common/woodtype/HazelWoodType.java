package azmalent.terraincognita.common.woodtype;

import azmalent.terraincognita.common.block.fruit.FruitLeavesBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public final class HazelWoodType extends TIWoodType {
    public HazelWoodType(String id, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(id, tree, woodColor, barkColor);
    }

    @Override
    protected LeavesBlock createLeaves() {
        return new FruitLeavesBlock(ModBlocks.HAZELNUT.defaultBlockState(), 20);
    }
}
