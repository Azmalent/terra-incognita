package azmalent.terraincognita.mixin.accessor;

import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Boat.class)
public interface BoatEntityAccessor {
    @Accessor("status")
    Boat.Status ti_getStatus();

    @Accessor("lastYd")
    void ti_setLastYd(double value);
}
