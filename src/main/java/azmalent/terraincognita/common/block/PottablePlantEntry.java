package azmalent.terraincognita.common.block;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;

public final class PottablePlantEntry implements ItemLike {
    private static final Block.Properties FLOWER_POT_PROPS = Block.Properties.copy(Blocks.FLOWER_POT);

    public final BlockEntry plant;
    public final BlockEntry potted;

    @SuppressWarnings("deprecation")
    public PottablePlantEntry(String id, BlockEntry plant) {
        this.plant = plant;
        potted = ModBlocks.HELPER.newBuilder("potted_" + id, () -> new FlowerPotBlock(plant.getBlock(), FLOWER_POT_PROPS)).withoutItemForm().cutoutRender().build();
    }

    public Block getBlock() {
        return plant.getBlock();
    }

    @Nonnull
    @Override
    public Item asItem() {
        return plant.getItem();
    }

    public Block getPotted() {
        return potted.getBlock();
    }
}
