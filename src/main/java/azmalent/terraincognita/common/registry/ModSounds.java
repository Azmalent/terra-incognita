package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.SOUND_EVENTS);

    public static final RegistryObject<SoundEvent> DANDELION_BLOW = makeSoundEvent("item.dandelion_puff.blow");
    public static final RegistryObject<SoundEvent> CALTROPS_THROWN  = makeSoundEvent("block.caltrops.throw");

    private static RegistryObject<SoundEvent> makeSoundEvent(String id) {
        return SOUNDS.register(id, () -> new SoundEvent(TerraIncognita.prefix(id)));
    }
}
