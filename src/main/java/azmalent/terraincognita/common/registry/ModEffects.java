package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.effect.StickyMouthEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, TerraIncognita.MODID);

    public static final RegistryObject<MobEffect> STICKY_MOUTH = EFFECTS.register("sticky_mouth", StickyMouthEffect::new);
}
