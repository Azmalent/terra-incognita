package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.block.PottablePlantEntry;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;

import java.util.List;

public class TradeHandler {
    public static void setupWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ITrade> generic = event.getGenericTrades();
        List<VillagerTrades.ITrade> rare = event.getRareTrades();

        if (TIConfig.Flora.dandelionPuff.get()) {
            sell(generic, ModBlocks.DANDELION_PUFF, 12);
        }

        if (TIConfig.Flora.fieldFlowers.get()) {
            sell(generic, ModBlocks.CHICORY, 12);
            sell(generic, ModBlocks.YARROW, 12);
            sell(generic, ModBlocks.DAFFODIL, 12);
        }

        if (TIConfig.Flora.forestFlowers.get()) {
            sell(generic, ModBlocks.WILD_GARLIC, 12);
            sell(generic, ModBlocks.FOXGLOVE, 12);
            sell(generic, ModBlocks.YELLOW_PRIMROSE, 12);
            sell(generic, ModBlocks.PINK_PRIMROSE, 12);
            sell(generic, ModBlocks.PURPLE_PRIMROSE, 12);
        }

        if (TIConfig.Flora.swampFlowers.get()) {
            sell(generic, ModBlocks.FORGET_ME_NOT, 12);
            sell(generic, ModBlocks.GLOBEFLOWER, 12);
        }

        if (TIConfig.Flora.alpineFlowers.get()) {
            sell(generic, ModBlocks.SAXIFRAGE, 12);
            sell(generic, ModBlocks.ALPINE_PINK, 12);
            sell(generic, ModBlocks.GENTIAN, 12);
            sell(generic, ModBlocks.EDELWEISS, 7);
        }

        if (TIConfig.Flora.savannaFlowers.get()) {
            sell(generic, ModBlocks.MARIGOLD, 12);
            sell(generic, ModBlocks.BLUE_LUPINE, 12);
            sell(generic, ModBlocks.YELLOW_SNAPDRAGON, 12);
            sell(generic, ModBlocks.RED_SNAPDRAGON, 12);
            sell(generic, ModBlocks.MAGENTA_SNAPDRAGON, 12);
        }

        if (TIConfig.Flora.jungleFlowers.get()) {
            sell(generic, ModBlocks.BLUE_IRIS, 12);
            sell(generic, ModBlocks.BLACK_IRIS, 12);
            sell(generic, ModBlocks.PURPLE_IRIS, 12);
        }

        if (TIConfig.Flora.arcticFlowers.get()) {
            sell(generic, ModBlocks.ARCTIC_POPPY, 12);
            sell(generic, ModBlocks.DWARF_FIREWEED, 12);
            sell(generic, ModBlocks.WHITE_DRYAD, 12);
        }

        if (TIConfig.Flora.smallLilypad.get()) {
            sell(generic, ModBlocks.SMALL_LILY_PAD.makeStack(3), 5);
        }

        if (TIConfig.Flora.lotus.get()) {
            sell(generic, ModBlocks.WHITE_LOTUS.makeStack(), 5);
            sell(generic, ModBlocks.PINK_LOTUS.makeStack(), 5);
            sell(generic, ModBlocks.YELLOW_LOTUS.makeStack(), 5);
        }

        if (TIConfig.Flora.reeds.get()) {
            sell(generic, ModBlocks.REEDS, 8);
        }

        if (TIConfig.Trees.apple.get()) {
            sell(generic, 5, ModWoodTypes.APPLE.SAPLING, 8);
        }

        if (TIConfig.Trees.hazel.get()) {
            sell(generic, 5, ModWoodTypes.HAZEL.SAPLING, 8);
        }

        if (TIConfig.Food.taffy.get()) {
            sell(rare, 2, new ItemStack(ModItems.TAFFY.get()), 3);
        }

        if (TIConfig.Misc.peat.get()) {
            sell(generic, 2, ModBlocks.PEAT.makeStack(4), 3);
        }
    }

    private static void sell(List<VillagerTrades.ITrade> trades, int price, ItemStack stack, int maxTrades) {
        trades.add(new BasicTrade(price, stack, maxTrades, 1));
    }

    private static void sell(List<VillagerTrades.ITrade> trades, ItemStack stack, int maxTrades) {
        sell(trades, 1, stack, maxTrades);
    }

    private static void sell(List<VillagerTrades.ITrade> trades, IItemProvider itemProvider, int maxTrades) {
        sell(trades, 1, itemProvider, maxTrades);
    }

    private static void sell(List<VillagerTrades.ITrade> trades, int price, IItemProvider itemProvider, int maxTrades) {
        sell(trades, price, new ItemStack(itemProvider.asItem()), maxTrades);
    }
}
