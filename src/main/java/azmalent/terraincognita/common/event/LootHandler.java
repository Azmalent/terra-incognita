package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootHandler {
    private static final Map<ResourceLocation, Integer> TAFFY_WEIGHTS = new ImmutableMap.Builder<ResourceLocation, Integer>()
        .put(BuiltInLootTables.SIMPLE_DUNGEON, 20)
        .put(BuiltInLootTables.ABANDONED_MINESHAFT, 30)
        .put(BuiltInLootTables.BASTION_OTHER, 12)
        .put(BuiltInLootTables.DESERT_PYRAMID, 25)
        .put(BuiltInLootTables.JUNGLE_TEMPLE, 5)
        .put(BuiltInLootTables.RUINED_PORTAL, 15)
        .put(BuiltInLootTables.IGLOO_CHEST, 15)
        .put(BuiltInLootTables.STRONGHOLD_CORRIDOR, 2)
        .put(BuiltInLootTables.WOODLAND_MANSION, 20)
        .build();

    private static final Map<ResourceLocation, Integer> NOTCH_CARROT_WEIGHTS = new ImmutableMap.Builder<ResourceLocation, Integer>()
        .put(BuiltInLootTables.SIMPLE_DUNGEON, 2)
        .put(BuiltInLootTables.ABANDONED_MINESHAFT, 1)
        .put(BuiltInLootTables.BASTION_TREASURE, 2)
        .put(BuiltInLootTables.DESERT_PYRAMID, 2)
        .put(BuiltInLootTables.RUINED_PORTAL, 1)
        .put(BuiltInLootTables.WOODLAND_MANSION, 2)
        .build();

    @SubscribeEvent
    public static void onLoadLootTable(LootTableLoadEvent event) {
        ResourceLocation tableName = event.getName();

        if (TIConfig.Food.taffy.get() && TAFFY_WEIGHTS.containsKey(tableName)) {
            int weight = TAFFY_WEIGHTS.get(tableName);
            DataUtil.addLoot(event.getTable(), LootItem.lootTableItem(ModItems.TAFFY.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                .setWeight(weight)
                .build()
            );
        }

        if (TIConfig.Food.notchCarrot.get() && NOTCH_CARROT_WEIGHTS.containsKey(tableName)) {
            int weight = NOTCH_CARROT_WEIGHTS.get(tableName);
            DataUtil.addLoot(event.getTable(), LootItem.lootTableItem(ModItems.NOTCH_CARROT.get()).setWeight(weight).build());
        }
    }
}
