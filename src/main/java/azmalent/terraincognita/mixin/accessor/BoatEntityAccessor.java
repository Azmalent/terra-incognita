package azmalent.terraincognita.mixin.accessor;

import net.minecraft.entity.item.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BoatEntity.class)
public interface BoatEntityAccessor {
    @Accessor("status")
    BoatEntity.Status ti_getStatus();

    @Accessor("lastYd")
    void ti_setLastYd(double value);
}
