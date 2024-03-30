package azmalent.terraincognita.common.block.plant;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class TIFlowerBlock extends FlowerBlock {
    public TIFlowerBlock(StewEffect stewEffect) {
        super(() -> stewEffect.effect, stewEffect.duration, Block.Properties.copy(Blocks.POPPY));
    }

    public enum StewEffect {
        HUNGER(MobEffects.HUNGER, 10 * 20),
        WEAKNESS(MobEffects.WEAKNESS, 9 * 20),
        SLOWNESS(MobEffects.MOVEMENT_SLOWDOWN, 8 * 20),
        BLINDNESS(MobEffects.BLINDNESS, 8 * 20),
        POISON(MobEffects.POISON, 12 * 20),
        SATURATION(MobEffects.SATURATION, 6),
        SPEED(MobEffects.MOVEMENT_SPEED, 8 * 20),
        NIGHT_VISION(MobEffects.NIGHT_VISION, 5 * 20),
        REGENERATION(MobEffects.REGENERATION, 6 * 20),
        STRENGTH(MobEffects.DAMAGE_BOOST, 6 * 20),
        RESISTANCE(MobEffects.DAMAGE_RESISTANCE, 6 * 20),
        HASTE(MobEffects.DIG_SPEED, 10 * 20),
        JUMP_BOOST(MobEffects.JUMP, 6 * 20),
        INVISIBILITY(MobEffects.INVISIBILITY, 8 * 20);

        public final MobEffect effect;
        public final int duration;

        StewEffect(MobEffect effect, int duration) {
            this.effect = effect;
            this.duration = effect.isInstantenous() ? duration : duration / 20;
        }
    }
}
