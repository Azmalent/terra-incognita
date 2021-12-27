package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.PottablePlantEntry;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

public class IdRemappingHandler {
    private static Map<String, Block> BLOCK_ID_CHANGES = Maps.newHashMap();
    private static Map<String, Item> ITEM_ID_CHANGES = Maps.newHashMap();

    private static void remap(String oldId, PottablePlantEntry plant) {
        BLOCK_ID_CHANGES.put(oldId, plant.getBlock());
        BLOCK_ID_CHANGES.put("potted_" + oldId, plant.getPotted());
        ITEM_ID_CHANGES.put(oldId, plant.asItem());
    }

    private static <T extends Item> void remap(String oldId, RegistryObject<T> item) {
        ITEM_ID_CHANGES.put(oldId, item.get());
    }

    static {
        remap("yellow_snapdragon", ModBlocks.SNAPDRAGON);
        remap("red_snapdragon", ModBlocks.GLADIOLUS);
        remap("magenta_snapdragon", ModBlocks.GERANIUM);
        remap("blue_lupin", ModBlocks.BLUE_LUPINE);
        remap("rockfoil", ModBlocks.SAXIFRAGE);
        remap("fireweed", ModBlocks.DWARF_FIREWEED);

        remap("flower_band", ModItems.WREATH);
        remap("honey_hazelnut", ModItems.CANDIED_HAZELNUT);
    }

    public static void onMissingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
        event.getAllMappings().stream().filter(m -> m.key.getNamespace().equals(TerraIncognita.MODID)).forEach(mapping -> {
            Block block = BLOCK_ID_CHANGES.get(mapping.key.getPath());
            if (block != null) {
                TerraIncognita.LOGGER.info(String.format("Remapping block ID: %s -> %s", mapping.key, block.getRegistryName()));
                mapping.remap(block);
            }
        });
    }

    public static void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event) {
        event.getAllMappings().stream().filter(m -> m.key.getNamespace().equals(TerraIncognita.MODID)).forEach(mapping -> {
            Item item = ITEM_ID_CHANGES.get(mapping.key.getPath());
            if (item != null) {
                TerraIncognita.LOGGER.info(String.format("Remapping item ID: %s -> %s", mapping.key, item.getRegistryName()));
                mapping.remap(item);
            }
        });
    }
}
