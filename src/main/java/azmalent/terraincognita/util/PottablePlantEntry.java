package azmalent.terraincognita.util;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public final class PottablePlantEntry implements ItemLike {
    private static final Block.Properties FLOWER_POT_PROPS = Block.Properties.copy(Blocks.FLOWER_POT);

    public final BlockEntry<?> plant;
    public final BlockEntry<FlowerPotBlock> potted;

    @SuppressWarnings("deprecation")
    public PottablePlantEntry(String id, BlockEntry<?> plant) {
        this.plant = plant;
        potted = TerraIncognita.REG_HELPER.createBlock("potted_" + id, () -> new FlowerPotBlock(plant.get(), FLOWER_POT_PROPS)).noItemForm().cutoutRender().build();
    }

    public Block getBlock() {
        return plant.get();
    }

    public BlockState defaultBlockState() {
        return plant.defaultBlockState();
    }

    @Nonnull
    @Override
    public Item asItem() {
        return plant.asItem();
    }

    public ItemStack makeStack() {
        return new ItemStack(this.asItem());
    }

    public Block getPotted() {
        return potted.get();
    }
}