package azmalent.terraincognita.common.event;

import azmalent.cuneiform.common.trade.RandomItemForEmeraldsTrade;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;

import java.util.List;

public class TradeHandler {
    public static void setupWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> generic = event.getGenericTrades();

        //Flowers
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
            sell(generic, ModBlocks.WATER_FLAG, 12);
        }

        if (TIConfig.Flora.cattails.get()) {
            sell(generic, ModBlocks.CATTAIL, 12);
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
            sell(generic, ModBlocks.SNAPDRAGON, 12);
            sell(generic, ModBlocks.GLADIOLUS, 12);
            sell(generic, ModBlocks.GERANIUM, 12);
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
            sell(generic, ModBlocks.FIREWEED, 12);
            sell(generic, ModBlocks.WHITE_RHODODENDRON, 12);
        }

        //Saplings
        if (TIConfig.Trees.apple.get()) {
            sell(generic, 5, ModWoodTypes.APPLE.SAPLING, 8);
        }

        if (TIConfig.Trees.hazel.get()) {
            sell(generic, 5, ModWoodTypes.HAZEL.SAPLING, 8);
        }

        //Other stuff
        if (TIConfig.Flora.smallLilypad.get()) {
            sell(generic, ModBlocks.SMALL_LILY_PAD.makeStack(3), 5);
        }

        if (TIConfig.Flora.lotus.get()) {
            ModBlocks.LOTUSES.forEach(lotus -> sell(generic, lotus.makeStack(), 5));
        }

        if (TIConfig.Flora.caribouMoss.get()) {
            sell(generic, ModBlocks.CARIBOU_MOSS, 12);
        }

        if (TIConfig.Flora.reeds.get()) {
            sell(generic, ModBlocks.REEDS, 8);
        }

        if (TIConfig.Misc.peat.get()) {
            sell(generic, 2, ModBlocks.PEAT.makeStack(4), 3);
        }

        //Rares
        List<VillagerTrades.ItemListing> rare = event.getRareTrades();

        if (TIConfig.Food.taffy.get()) {
            sell(rare, 2, new ItemStack(ModItems.TAFFY.get()), 3);
        }

        if (TIConfig.Flora.sweetPeas.get()) {
            ItemLike[] items = ModBlocks.SWEET_PEAS.toArray(new ItemLike[0]);
            rare.add(new RandomItemForEmeraldsTrade(3, items, 5, 1));
        }

        if (TIConfig.Biomes.lushPlainsWeight.get() > 0) {
            sell(rare, 3, new ItemStack(ModBlocks.FLOWERING_GRASS, 3), 6);
        }
    }

    private static void sell(List<VillagerTrades.ItemListing> trades, int price, ItemStack stack, int maxTrades) {
        trades.add(new BasicTrade(price, stack, maxTrades, 1));
    }

    private static void sell(List<VillagerTrades.ItemListing> trades, ItemStack stack, int maxTrades) {
        sell(trades, 1, stack, maxTrades);
    }

    private static void sell(List<VillagerTrades.ItemListing> trades, ItemLike itemProvider, int maxTrades) {
        sell(trades, 1, itemProvider, maxTrades);
    }

    private static void sell(List<VillagerTrades.ItemListing> trades, int price, ItemLike itemProvider, int maxTrades) {
        sell(trades, price, new ItemStack(itemProvider.asItem()), maxTrades);
    }
}
