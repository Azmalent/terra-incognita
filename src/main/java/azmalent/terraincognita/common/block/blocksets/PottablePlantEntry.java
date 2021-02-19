package azmalent.terraincognita.common.block.blocksets;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.plants.WaterloggableTallFlowerBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModStewEffect;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;

import java.util.function.Function;
import java.util.function.Supplier;

public final class PottablePlantEntry {
    private static final Block.Properties FLOWER_POT_PROPS = Block.Properties.from(Blocks.FLOWER_POT);

    public final BlockEntry plant;
    public final BlockEntry potted;

    @SuppressWarnings("deprecation")
    public PottablePlantEntry(String id, BlockEntry plant) {
        this.plant = plant;
        potted = ModBlocks.HELPER.newBuilder("potted_" + id, () -> new FlowerPotBlock(plant.getBlock(), FLOWER_POT_PROPS)).withoutItemForm().withRenderType(BlockRenderType.CUTOUT).buildIf(plant != null);
    }

    public Block getBlock() {
        return plant.getBlock();
    }

    public Item getItem() {
        return plant.getItem();
    }

    public Block getPotted() {
        return potted.getBlock();
    }

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor, BooleanOption condition) {
        return createPlant(id, constructor, null, condition);
    }

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor, Function<Block, BlockItem> blockItemConstructor, BooleanOption condition) {
        if (!condition.get()) {
            return null;
        }

        BlockEntry.Builder builder = ModBlocks.HELPER.newBuilder(id, constructor).withBlockItem(blockItemConstructor).withRenderType(BlockRenderType.CUTOUT);
        return new PottablePlantEntry(id, builder.build());
    }

    public static PottablePlantEntry createFlower(String id, ModStewEffect stewEffect, BooleanOption condition) {
        return createPlant(id, () -> new FlowerBlock(stewEffect.effect, stewEffect.duration, Block.Properties.from(Blocks.POPPY)), condition);
    }

    public static PottablePlantEntry createTallPlant(String id, BooleanOption condition) {
        return createPlant(id, () -> new TallFlowerBlock(Block.Properties.from(Blocks.ROSE_BUSH)), block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB)), condition);
    }

    public static PottablePlantEntry createTallWaterloggablePlant(String id, BooleanOption condition) {
        return createPlant(id, WaterloggableTallFlowerBlock::new, block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB)), condition);
    }
}
