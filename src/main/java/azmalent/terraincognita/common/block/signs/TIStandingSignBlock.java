package azmalent.terraincognita.common.block.signs;

import azmalent.terraincognita.common.init.blocksets.TIWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;

public class TIStandingSignBlock extends StandingSignBlock implements ITerraIncognitaSign {
    private final TIWoodType woodType;

    public TIStandingSignBlock(MaterialColor color, TIWoodType woodType) {
        super(Block.Properties.create(Material.WOOD, color).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodType.OAK);
        this.woodType = woodType;
    }

    @Override
    public ResourceLocation getTexture() {
        return woodType.SIGN_TEXTURE;
    }
}
