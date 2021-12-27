package azmalent.terraincognita.common.entity;

import net.minecraft.item.Item;

public interface IBottleableEntity {
    Item getBottledItem();

    default void onUnbottled() {

    }
}
