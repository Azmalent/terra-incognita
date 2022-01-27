package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.TradeBuilder;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.WandererTradesEvent;

import java.util.List;

import static azmalent.cuneiform.common.data.WanderingTraderHandler.addCommonTrade;
import static azmalent.cuneiform.common.data.WanderingTraderHandler.addRareTrade;

public class TradeHandler {
    public static void setupWandererTrades(WandererTradesEvent event) {
        //Flowers
        if (TIConfig.Flora.dandelionPuff.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.DANDELION_PUFF).maxTrades(12).build());
        }

        if (TIConfig.Flora.fieldFlowers.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.CHICORY).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.YARROW).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.DAFFODIL).maxTrades(12).build());
        }

        if (TIConfig.Flora.forestFlowers.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.WILD_GARLIC).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.FOXGLOVE).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.YELLOW_PRIMROSE).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.PINK_PRIMROSE).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.PURPLE_PRIMROSE).maxTrades(12).build());
        }

        if (TIConfig.Flora.swampFlowers.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.FORGET_ME_NOT).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.GLOBEFLOWER).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.WATER_FLAG).maxTrades(12).build());
        }

        if (TIConfig.Flora.alpineFlowers.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.ALPINE_PINK).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.GENTIAN).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.EDELWEISS).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.YELLOW_SAXIFRAGE).maxTrades(12).build());
        }

        if (TIConfig.Flora.savannaFlowers.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.MARIGOLD).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.SNAPDRAGON).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.GLADIOLUS).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.BLUE_IRIS).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.PURPLE_IRIS).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.BLACK_IRIS).maxTrades(12).build());
        }

        if (TIConfig.Flora.arcticFlowers.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.ARCTIC_POPPY).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.DWARF_FIREWEED).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.WHITE_DRYAD).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.FIREWEED).maxTrades(12).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.WHITE_RHODODENDRON).maxTrades(12).build());
        }

        //Saplings
        if (TIConfig.Trees.apple.get()) {
            addCommonTrade(TradeBuilder.sell(ModWoodTypes.APPLE.SAPLING).maxTrades(8).build());
        }

        if (TIConfig.Trees.hazel.get()) {
            addCommonTrade(TradeBuilder.sell(ModWoodTypes.HAZEL.SAPLING).maxTrades(8).build());
        }

        //Other stuff
        if (TIConfig.Flora.smallLilypad.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.SMALL_LILY_PAD.makeStack(3)).maxTrades(5).build());
        }

        if (TIConfig.Flora.lotus.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.PINK_LOTUS).maxTrades(5).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.WHITE_LOTUS).maxTrades(5).build());
            addCommonTrade(TradeBuilder.sell(ModBlocks.YELLOW_LOTUS).maxTrades(5).build());
        }

        if (TIConfig.Flora.caribouMoss.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.CARIBOU_MOSS).maxTrades(12).build());
        }

        if (TIConfig.Flora.reeds.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.SEDGE).maxTrades(8).build());
        }

        if (TIConfig.Misc.peat.get()) {
            addCommonTrade(TradeBuilder.sell(2, ModBlocks.PEAT.makeStack(4)).maxTrades(3).build());
        }

        //Rares
        if (TIConfig.Food.taffy.get()) {
            addRareTrade(TradeBuilder.sell(2, ModItems.TAFFY).maxTrades(3).build());
        }

        if (TIConfig.Flora.sweetPeas.get()) {
            addRareTrade(TradeBuilder.sellRandom(3, ModBlocks.SWEET_PEAS).maxTrades(3).build());
        }

        if (TIConfig.Biomes.lushPlainsWeight.get() > 0) {
            addRareTrade(TradeBuilder.sell(3, ModBlocks.FLOWERING_GRASS.makeStack(3)).maxTrades(6).build());
        }
    }
}
