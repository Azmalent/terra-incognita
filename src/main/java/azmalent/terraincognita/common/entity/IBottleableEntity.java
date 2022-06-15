package azmalent.terraincognita.common.entity;

import net.minecraft.world.item.Item;

public interface IBottleableEntity {
    Item getBottledItem();

    default void onReleasedFromBottle() {

    }

    boolean isFromBottle();

    void setFromBottle(boolean fromBottle);
}
