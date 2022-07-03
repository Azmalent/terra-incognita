package azmalent.terraincognita.common.woodtype;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.util.DataUtil;
import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.fruit.FruitLeavesBlock;
import azmalent.terraincognita.common.block.woodset.TIHedgeBlock;
import azmalent.terraincognita.common.block.woodset.TILeafCarpetBlock;
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
    public final BlockEntry<TILeafCarpetBlock> BLOSSOMING_LEAF_CARPET;
    public final BlockEntry<TIHedgeBlock> BLOSSOMING_HEDGE;

    public AppleWoodType(String id, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(id, tree, woodColor, barkColor);
        BLOSSOMING_LEAVES = TerraIncognita.REGISTRY_HELPER.createBlock("blossoming_" + id + "_leaves", this::createLeaves).cutoutMippedRender().build();
        BLOSSOMING_LEAF_CARPET = TerraIncognita.REGISTRY_HELPER.createBlock("blossoming_apple_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
        BLOSSOMING_HEDGE = TerraIncognita.REGISTRY_HELPER.createBlock("blossoming_apple_hedge", () -> new TIHedgeBlock(MaterialColor.TERRACOTTA_ORANGE)).cutoutMippedRender().build();
    }

    @Override
    protected LeavesBlock createLeaves() {
        return new FruitLeavesBlock(ModBlocks.APPLE.defaultBlockState(), 50);
    }

    @Override
    public void initFlammability() {
        super.initFlammability();
        DataUtil.registerFlammable(BLOSSOMING_LEAVES, 30, 60);
        DataUtil.registerFlammable(BLOSSOMING_LEAF_CARPET, 30, 60);
        DataUtil.registerFlammable(BLOSSOMING_HEDGE, 5, 20);
    }

    @Override
    public void registerBlockColors(BlockColors colors) {
        super.registerBlockColors(colors);
        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.get(0.5D, 1.0D),
            BLOSSOMING_LEAVES.get(), BLOSSOMING_LEAF_CARPET.get(), BLOSSOMING_HEDGE.get()
        );
    }

    @Override
    public void registerItemColors(ItemColors itemColors, BlockColors blockColors) {
        super.registerItemColors(itemColors, blockColors);
        itemColors.register((stack, index) -> blockColors.getColor(ItemUtil.getBlockFromItem(stack).defaultBlockState(), null, null, index),
            BLOSSOMING_LEAVES, BLOSSOMING_LEAF_CARPET, BLOSSOMING_HEDGE
        );
    }
}
