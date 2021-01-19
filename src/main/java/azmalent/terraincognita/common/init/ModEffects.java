package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.effect.StickyMouthEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, TerraIncognita.MODID);

    public static RegistryObject<Effect> STICKY_MOUTH;

    static {
        if (TIConfig.Food.taffy.get()) {
            STICKY_MOUTH = EFFECTS.register("sticky_mouth", StickyMouthEffect::new);
        }
    }
}
