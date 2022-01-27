package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.effect.StickyMouthEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = TerraIncognita.REG_HELPER.getOrCreateRegistry(ForgeRegistries.MOB_EFFECTS);

    public static final RegistryObject<MobEffect> STICKY_MOUTH = EFFECTS.register("sticky_mouth", StickyMouthEffect::new);
}
