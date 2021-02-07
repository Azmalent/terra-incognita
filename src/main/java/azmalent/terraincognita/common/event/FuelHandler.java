package azmalent.terraincognita.common.event;

import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.blocksets.TIWoodType;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;

import java.util.Map;

public class FuelHandler {
    public static Map<Item, Integer> fuelValues = Maps.newHashMap();

    public static void initFuelValues() {
        for (TIWoodType woodType : ModBlocks.WoodTypes.VALUES) {
            initWoodFuelValues(woodType);
        }

        if (ModBlocks.PEAT != null) {
            fuelValues.put(ModBlocks.PEAT.getItem(), 3200);
        }
    }

    private static void initWoodFuelValues(TIWoodType woodType) {
        if (woodType.isEnabled()) {
            fuelValues.put(woodType.LOG.getItem(), 300);
            fuelValues.put(woodType.STRIPPED_LOG.getItem(), 300);
            fuelValues.put(woodType.WOOD.getItem(), 300);
            fuelValues.put(woodType.STRIPPED_WOOD.getItem(), 300);
            fuelValues.put(woodType.PLANKS.getItem(), 300);
            fuelValues.put(woodType.SLAB.getItem(), 150);
            fuelValues.put(woodType.STAIRS.getItem(), 300);
            fuelValues.put(woodType.FENCE.getItem(), 300);
            fuelValues.put(woodType.FENCE_GATE.getItem(), 300);
            //fuelValues.put(tree.SIGN.getItem(), 200);
            fuelValues.put(woodType.DOOR.getItem(), 200);
            fuelValues.put(woodType.TRAPDOOR.getItem(), 300);
        }
    }

    public static void getBurnTime(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();
        if (fuelValues.containsKey(item)) {
            event.setBurnTime(fuelValues.get(item));
        }
    }
}
