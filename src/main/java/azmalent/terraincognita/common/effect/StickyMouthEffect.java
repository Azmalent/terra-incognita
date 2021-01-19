package azmalent.terraincognita.common.effect;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class StickyMouthEffect extends Effect {
    public StickyMouthEffect() {
        super(EffectType.HARMFUL, 0xe868db);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
