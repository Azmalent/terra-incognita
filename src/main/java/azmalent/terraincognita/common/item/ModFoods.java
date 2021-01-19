package azmalent.terraincognita.common.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods {
    public static Food TAFFY = new Food.Builder().hunger(3).saturation(3f).setAlwaysEdible().build();

    public static Food NOTCH_CARROT = new Food.Builder().hunger(6).saturation(14.4f)
        .effect(() -> new EffectInstance(Effects.NIGHT_VISION, 600 * 20), 1.0f)
        .effect(() -> new EffectInstance(Effects.ABSORPTION, 120 * 20, 3), 1.0f)
        .effect(() -> new EffectInstance(Effects.SPEED, 120 * 20, 1), 1.0f)
        .setAlwaysEdible()
        .build();

    public static Food FIDDLEHEAD = new Food.Builder().hunger(3).saturation(0.5f).fastToEat().build();

    public static Food BERRY_SORBET = new Food.Builder().hunger(5).saturation(3f).build();
}
