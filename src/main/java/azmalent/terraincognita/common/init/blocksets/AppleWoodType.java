package azmalent.terraincognita.common.init.blocksets;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.block.trees.AppleFruitBlock;
import azmalent.terraincognita.common.block.trees.AppleLeavesBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public final class AppleWoodType extends TIWoodType {
    public final BlockEntry BLOSSOMING_LEAVES;
    public final BlockEntry FRUIT;

    public AppleWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor, BooleanOption condition) {
        super(id, tree, woodColor, barkColor, condition);

        BLOSSOMING_LEAVES = ModBlocks.HELPER.newBuilder("blossoming_" + id + "_leaves", this::createLeaves).buildIf(condition);
        FRUIT = ModBlocks.HELPER.newBuilder(id, AppleFruitBlock::new).withoutItemForm().buildIf(condition);
    }

    @Override
    protected Block createLeaves() {
        return new AppleLeavesBlock();
    }

    @Override
    public void initFlammability() {
        super.initFlammability();

        if (isEnabled()) {
            FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;
            fire.TI_SetFireInfo(BLOSSOMING_LEAVES.getBlock(), 30, 60);
        }
    }

    @Override
    public void initRenderLayers() {
        super.initRenderLayers();

        if (isEnabled()) {
            RenderTypeLookup.setRenderLayer(BLOSSOMING_LEAVES.getBlock(), RenderType.getCutoutMipped());
        }
    }
}
