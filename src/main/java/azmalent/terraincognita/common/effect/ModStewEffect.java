package azmalent.terraincognita.common.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

public enum ModStewEffect {
    SATURATION(Effects.SATURATION, 6),
    SPEED(Effects.SPEED, 8 * 20),
    NIGHT_VISION(Effects.NIGHT_VISION, 5 * 20),
    BLINDNESS(Effects.BLINDNESS, 8 * 20),
    REGENERATION(Effects.REGENERATION, 6 * 20),
    STRENGTH(Effects.STRENGTH, 6 * 20),
    POISON(Effects.POISON, 12 * 20),
    RESISTANCE(Effects.RESISTANCE, 6 * 20),
    HASTE(Effects.HASTE, 10 * 20),
    JUMP_BOOST(Effects.JUMP_BOOST, 6 * 20),
    SLOWNESS(Effects.SLOWNESS, 8 * 20),
    WEAKNESS(Effects.WEAKNESS, 9 * 20),
    INVISIBILITY(Effects.INVISIBILITY, 8 * 20);

    public final Effect effect;
    public final int duration;

    ModStewEffect(Effect effect, int duration) {
        this.effect = effect;
        this.duration = effect.isInstant() ? duration : duration / 20;
    }
}
