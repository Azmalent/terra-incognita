package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.LootUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;

import java.util.Map;

public class LootHandler {
    private static final Map<ResourceLocation, Integer> TAFFY_WEIGHTS = Maps.newHashMap();
    private static final Map<ResourceLocation, Integer> NOTCH_CARROT_WEIGHTS = Maps.newHashMap();

    static {
        TAFFY_WEIGHTS.put(LootTables.CHESTS_SIMPLE_DUNGEON, 20);
        TAFFY_WEIGHTS.put(LootTables.CHESTS_ABANDONED_MINESHAFT, 30);
        TAFFY_WEIGHTS.put(LootTables.BASTION_OTHER, 12);
        TAFFY_WEIGHTS.put(LootTables.CHESTS_DESERT_PYRAMID, 25);
        TAFFY_WEIGHTS.put(LootTables.CHESTS_JUNGLE_TEMPLE, 5);
        TAFFY_WEIGHTS.put(LootTables.RUINED_PORTAL, 15);
        TAFFY_WEIGHTS.put(LootTables.CHESTS_IGLOO_CHEST, 15);
        TAFFY_WEIGHTS.put(LootTables.CHESTS_STRONGHOLD_CORRIDOR, 2);
        TAFFY_WEIGHTS.put(LootTables.CHESTS_WOODLAND_MANSION, 20);

        NOTCH_CARROT_WEIGHTS.put(LootTables.CHESTS_SIMPLE_DUNGEON, 2);
        NOTCH_CARROT_WEIGHTS.put(LootTables.CHESTS_ABANDONED_MINESHAFT, 1);
        NOTCH_CARROT_WEIGHTS.put(LootTables.BASTION_TREASURE, 2);
        NOTCH_CARROT_WEIGHTS.put(LootTables.CHESTS_DESERT_PYRAMID, 2);
        NOTCH_CARROT_WEIGHTS.put(LootTables.RUINED_PORTAL, 1);
        NOTCH_CARROT_WEIGHTS.put(LootTables.CHESTS_WOODLAND_MANSION, 2);
    }

    public static void onLoadLootTable(LootTableLoadEvent event) {
        ResourceLocation tableName = event.getName();

        if (TIConfig.Food.taffy.get() && TAFFY_WEIGHTS.containsKey(tableName)) {
            int weight = TAFFY_WEIGHTS.get(tableName);
            LootPool pool = LootUtil.getPoolByIndex(event.getTable(), 0);
            LootUtil.addEntry(pool, ItemLootEntry.builder(ModItems.TAFFY.get())
                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F)))
                .weight(weight)
                .build()
            );
        }

        if (TIConfig.Food.notchCarrot.get() && NOTCH_CARROT_WEIGHTS.containsKey(tableName)) {
            int weight = NOTCH_CARROT_WEIGHTS.get(tableName);
            LootPool pool = LootUtil.getPoolByIndex(event.getTable(), 0);
            LootUtil.addEntry(pool, ItemLootEntry.builder(ModItems.NOTCH_CARROT.get()).weight(weight).build());
        }
    }
}
