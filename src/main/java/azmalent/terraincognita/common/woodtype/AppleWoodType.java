package azmalent.terraincognita.common.woodtype;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.util.DataUtil;
import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.fruit.FruitLeavesBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public final class AppleWoodType extends TIWoodType {
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

    @Override
    public void registerBlockColors(BlockColors colors) {
        super.registerBlockColors(colors);
        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.get(0.5D, 1.0D),
            BLOSSOMING_LEAVES.get()
        );
    }

    @Override
    public void registerItemColors(ItemColors itemColors, BlockColors blockColors) {
        super.registerItemColors(itemColors, blockColors);
        itemColors.register((stack, index) -> blockColors.getColor(ItemUtil.getBlockFromItem(stack).defaultBlockState(), null, null, index),
            BLOSSOMING_LEAVES
        );
    }
}
