package azmalent.terraincognita.common.block.blocksets;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.plants.WaterloggableTallFlowerBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.effect.ModStewEffect;
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
        potted = ModBlocks.HELPER.newBuilder("potted_" + id, () -> new FlowerPotBlock(plant.getBlock(), FLOWER_POT_PROPS)).withoutItemForm().withRenderType(BlockRenderType.CUTOUT).build();
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

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor) {
        return createPlant(id, constructor, null);
    }

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor, Function<Block, BlockItem> blockItemConstructor) {
        BlockEntry.Builder builder = ModBlocks.HELPER.newBuilder(id, constructor).withBlockItem(blockItemConstructor).withRenderType(BlockRenderType.CUTOUT);
        return new PottablePlantEntry(id, builder.build());
    }

    public static PottablePlantEntry createFlower(String id, ModStewEffect stewEffect) {
        return createPlant(id, () -> new FlowerBlock(stewEffect.effect, stewEffect.duration, Block.Properties.from(Blocks.POPPY)));
    }

    public static PottablePlantEntry createTallPlant(String id) {
        return createPlant(id, () -> new TallFlowerBlock(Block.Properties.from(Blocks.ROSE_BUSH)), block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB)));
    }

    public static PottablePlantEntry createTallWaterloggablePlant(String id) {
        return createPlant(id, WaterloggableTallFlowerBlock::new, block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB)));
    }
}
