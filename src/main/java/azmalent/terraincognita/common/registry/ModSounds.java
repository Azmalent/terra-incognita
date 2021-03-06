package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TerraIncognita.MODID);

    public static final RegistryObject<SoundEvent> DANDELION_BLOW = makeSoundEvent("item.dandelion_puff.blow");
    public static final RegistryObject<SoundEvent> CALTROPS_THROWN  = makeSoundEvent("block.caltrops.throw");
    public static final RegistryObject<SoundEvent> CALTROPS_PLACE   = makeSoundEvent("block.caltrops.place");

    private static RegistryObject<SoundEvent> makeSoundEvent(String id) {
        return SOUNDS.register(id, () -> new SoundEvent(TerraIncognita.prefix(id)));
    }
}
