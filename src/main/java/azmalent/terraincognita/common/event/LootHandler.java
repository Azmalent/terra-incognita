package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.LootUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.RandomValueBounds;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;

import java.util.Map;

public class LootHandler {
    private static final Map<ResourceLocation, Integer> TAFFY_WEIGHTS = Maps.newHashMap();
    private static final Map<ResourceLocation, Integer> NOTCH_CARROT_WEIGHTS = Maps.newHashMap();

    static {
        TAFFY_WEIGHTS.put(BuiltInLootTables.SIMPLE_DUNGEON, 20);
        TAFFY_WEIGHTS.put(BuiltInLootTables.ABANDONED_MINESHAFT, 30);
        TAFFY_WEIGHTS.put(BuiltInLootTables.BASTION_OTHER, 12);
        TAFFY_WEIGHTS.put(BuiltInLootTables.DESERT_PYRAMID, 25);
        TAFFY_WEIGHTS.put(BuiltInLootTables.JUNGLE_TEMPLE, 5);
        TAFFY_WEIGHTS.put(BuiltInLootTables.RUINED_PORTAL, 15);
        TAFFY_WEIGHTS.put(BuiltInLootTables.IGLOO_CHEST, 15);
        TAFFY_WEIGHTS.put(BuiltInLootTables.STRONGHOLD_CORRIDOR, 2);
        TAFFY_WEIGHTS.put(BuiltInLootTables.WOODLAND_MANSION, 20);

        NOTCH_CARROT_WEIGHTS.put(BuiltInLootTables.SIMPLE_DUNGEON, 2);
        NOTCH_CARROT_WEIGHTS.put(BuiltInLootTables.ABANDONED_MINESHAFT, 1);
        NOTCH_CARROT_WEIGHTS.put(BuiltInLootTables.BASTION_TREASURE, 2);
        NOTCH_CARROT_WEIGHTS.put(BuiltInLootTables.DESERT_PYRAMID, 2);
        NOTCH_CARROT_WEIGHTS.put(BuiltInLootTables.RUINED_PORTAL, 1);
        NOTCH_CARROT_WEIGHTS.put(BuiltInLootTables.WOODLAND_MANSION, 2);
    }

    public static void onLoadLootTable(LootTableLoadEvent event) {
        ResourceLocation tableName = event.getName();

        if (TIConfig.Food.taffy.get() && TAFFY_WEIGHTS.containsKey(tableName)) {
            int weight = TAFFY_WEIGHTS.get(tableName);
            LootPool pool = LootUtil.getPoolByIndex(event.getTable(), 0);
            LootUtil.addEntry(pool, LootItem.lootTableItem(ModItems.TAFFY.get())
                .apply(SetItemCountFunction.setCount(RandomValueBounds.between(1.0F, 4.0F)))
                .setWeight(weight)
                .build()
            );
        }

        if (TIConfig.Food.notchCarrot.get() && NOTCH_CARROT_WEIGHTS.containsKey(tableName)) {
            int weight = NOTCH_CARROT_WEIGHTS.get(tableName);
            LootPool pool = LootUtil.getPoolByIndex(event.getTable(), 0);
            LootUtil.addEntry(pool, LootItem.lootTableItem(ModItems.NOTCH_CARROT.get()).setWeight(weight).build());
        }
    }
}
