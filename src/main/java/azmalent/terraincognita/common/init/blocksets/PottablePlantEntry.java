package azmalent.terraincognita.common.init.blocksets;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public final class PottablePlantEntry {
    public final BlockEntry plant;
    public final BlockEntry potted;

    public PottablePlantEntry(String id, BlockEntry plant) {
        this.plant = plant;
        potted = ModBlocks.HELPER.newBuilder("potted_" + id, () -> new FlowerPotBlock(plant.getBlock(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid())).withoutItemForm().build();
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
}
