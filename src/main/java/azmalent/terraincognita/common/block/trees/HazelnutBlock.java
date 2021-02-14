package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.init.ModWoodTypes;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class HazelnutBlock extends AbstractFruitBlock {
    public HazelnutBlock() {
        super(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.2F).sound(SoundType.WOOD), ModItems.HAZELNUT::get);
    }
}
