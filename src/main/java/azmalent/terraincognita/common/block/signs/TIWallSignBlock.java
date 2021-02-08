package azmalent.terraincognita.common.block.signs;

import azmalent.terraincognita.common.init.blocksets.TIWoodType;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class TIWallSignBlock extends WallSignBlock implements ITerraIncognitaSign {
    private final TIWoodType woodType;

    public TIWallSignBlock(MaterialColor color, TIWoodType woodType) {
        super(Block.Properties.create(Material.WOOD, color).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodType.OAK);
        this.woodType = woodType;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return woodType.STANDING_SIGN.getBlock().getDrops(state, builder);
    }

    @Override
    public ResourceLocation getTexture() {
        return woodType.SIGN_TEXTURE;
    }
}
