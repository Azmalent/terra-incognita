package azmalent.terraincognita.common.effect;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.ArrayList;
import java.util.List;

public class StickyMouthEffect extends MobEffect {
    public StickyMouthEffect() {
        super(MobEffectCategory.HARMFUL, 0xe868db);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
