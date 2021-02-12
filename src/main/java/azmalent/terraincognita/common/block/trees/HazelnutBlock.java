package azmalent.terraincognita.common.block.trees;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class HazelnutBlock extends Block {
    public HazelnutBlock() {
        super(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.2F).sound(SoundType.WOOD));
    }
}
