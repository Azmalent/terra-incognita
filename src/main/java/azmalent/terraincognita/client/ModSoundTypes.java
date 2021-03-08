package azmalent.terraincognita.client;

import azmalent.terraincognita.common.registry.ModSounds;
import net.minecraft.block.SoundType;

public class ModSoundTypes {
    public static SoundType CALTROPS = new SoundType(0.8f, 1.2f,
        SoundType.METAL.getBreakSound(),
        SoundType.METAL.getStepSound(),
        ModSounds.CALTROPS_PLACE.get(),
        SoundType.METAL.getHitSound(),
        SoundType.METAL.getFallSound()
    );
}
