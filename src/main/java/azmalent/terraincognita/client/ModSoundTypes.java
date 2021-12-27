package azmalent.terraincognita.client;

import net.minecraft.block.SoundType;

public class ModSoundTypes {
    public static final SoundType CALTROPS = new SoundType(0.8f, 1.4f,
        SoundType.CHAIN.getBreakSound(),
        SoundType.CHAIN.getStepSound(),
        SoundType.CHAIN.getPlaceSound(),
        SoundType.CHAIN.getHitSound(),
        SoundType.CHAIN.getFallSound()
    );
}
