package azmalent.terraincognita.common.woodtype;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.fruit.FruitLeavesBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public final class AppleWoodType extends ModWoodType {
    public final BlockEntry<LeavesBlock> BLOSSOMING_LEAVES;

    public AppleWoodType(String id, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(id, tree, woodColor, barkColor);
        BLOSSOMING_LEAVES = TerraIncognita.REG_HELPER.createBlock("blossoming_" + id + "_leaves", this::createLeaves).cutoutMippedRender().build();
    }

    @Override
    protected LeavesBlock createLeaves() {
        return new FruitLeavesBlock(ModBlocks.APPLE.defaultBlockState(), 50);
    }

    @Override
    public void initFlammability() {
        super.initFlammability();
        DataUtil.registerFlammable(BLOSSOMING_LEAVES, 30, 60);
    }
}
