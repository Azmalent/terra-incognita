package azmalent.terraincognita.mixin.accessor;

import net.minecraft.entity.item.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BoatEntity.class)
public interface BoatEntityAccessor {
    @Accessor
    BoatEntity.Status getStatus();

    @Accessor
    void setLastYd(double value);
}
