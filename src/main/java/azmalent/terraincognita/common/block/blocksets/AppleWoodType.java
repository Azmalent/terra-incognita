package azmalent.terraincognita.common.block.blocksets;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.common.block.trees.FruitLeavesBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;

@SuppressWarnings("ConstantConditions")
public final class AppleWoodType extends ModWoodType {
    public final BlockEntry BLOSSOMING_LEAVES;

    public AppleWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(id, tree, woodColor, barkColor);

        BLOSSOMING_LEAVES = ModBlocks.HELPER.newBuilder("blossoming_" + id + "_leaves", this::createLeaves).withRenderType(BlockRenderType.CUTOUT_MIPPED).build();
    }

    @Override
    protected Block createLeaves() {
        return new FruitLeavesBlock(ModBlocks.APPLE.getDefaultState(), 100);
    }

    @Override
    public void initFlammability() {
        super.initFlammability();

        FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;
        fire.TI_setFireInfo(BLOSSOMING_LEAVES.getBlock(), 30, 60);
    }
}
