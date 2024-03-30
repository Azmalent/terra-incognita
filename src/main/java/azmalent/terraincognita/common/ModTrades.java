package azmalent.terraincognita.common;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.util.TradeBuilder;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.world.item.ItemStack;

import static azmalent.cuneiform.common.data.WanderingTraderHandler.*;

public class ModTrades {
    public static void initWandererTrades() {
        //Flowers
        if (TIConfig.Flora.dandelionPuff.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.DANDELION_PUFF.makeStack()).maxTrades(12).build());
        }

        if (TIConfig.Flora.fieldFlowers.get()) {
            addCommonTrades(
                TradeBuilder.sell(ModBlocks.CHICORY.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.YARROW.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.DAFFODIL.makeStack()).maxTrades(12).build()
            );
        }

        if (TIConfig.Flora.forestFlowers.get()) {
            addCommonTrades(
                TradeBuilder.sell(ModBlocks.WILD_GARLIC.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.FOXGLOVE.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.YELLOW_PRIMROSE.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.PINK_PRIMROSE.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.PURPLE_PRIMROSE.makeStack()).maxTrades(12).build()
            );
        }

        if (TIConfig.Flora.swampFlowers.get()) {
            addCommonTrades(
                TradeBuilder.sell(ModBlocks.FORGET_ME_NOT.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.GLOBEFLOWER.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.WATER_FLAG.makeStack()).maxTrades(12).build()
            );
        }

        if (TIConfig.Flora.alpineFlowers.get()) {
            addCommonTrades(
                TradeBuilder.sell(ModBlocks.ALPINE_PINK.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.GENTIAN.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.EDELWEISS.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.YELLOW_SAXIFRAGE.makeStack()).maxTrades(12).build()
            );
        }

        if (TIConfig.Flora.savannaFlowers.get()) {
            addCommonTrades(
                TradeBuilder.sell(ModBlocks.MARIGOLD.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.SNAPDRAGON.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.BLUE_IRIS.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.PURPLE_IRIS.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.BLACK_IRIS.makeStack()).maxTrades(12).build()
            );
        }

        if (TIConfig.Flora.arcticFlowers.get()) {
            addCommonTrades(
                TradeBuilder.sell(ModBlocks.HEATHER.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.WHITE_DRYAD.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.FIREWEED.makeStack()).maxTrades(12).build(),
                TradeBuilder.sell(ModBlocks.WHITE_RHODODENDRON.makeStack()).maxTrades(12).build()
            );
        }

        //Saplings
        if (TIConfig.Trees.apple.get()) {
            addCommonTrade(TradeBuilder.sell(ModWoodTypes.APPLE.SAPLING.makeStack()).maxTrades(8).build());
        }

        if (TIConfig.Trees.hazel.get()) {
            addCommonTrade(TradeBuilder.sell(ModWoodTypes.HAZEL.SAPLING.makeStack()).maxTrades(8).build());
        }

        if (TIConfig.Trees.larch.get()) {
            addCommonTrade(TradeBuilder.sell(ModWoodTypes.LARCH.SAPLING.makeStack()).maxTrades(8).build());
        }

        if (TIConfig.Trees.ginkgo.get()) {
            addCommonTrade(TradeBuilder.sell(ModWoodTypes.GINKGO.SAPLING.makeStack()).maxTrades(8).build());
        }

        //Other stuff
        if (TIConfig.Flora.smallLilyPads.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.SMALL_LILY_PAD.makeStack(3)).maxTrades(5).build());
        }

        if (TIConfig.Flora.lotus.get()) {
            ModBlocks.LOTUSES.forEach(lotus -> {
                addCommonTrade(TradeBuilder.sell(lotus.makeStack()).maxTrades(5).build());
            });
        }

        if (TIConfig.Flora.caribouMoss.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.CARIBOU_MOSS.makeStack()).maxTrades(12).build());
        }

        if (TIConfig.Flora.swampReeds.get()) {
            addCommonTrade(TradeBuilder.sell(ModBlocks.SWAMP_REEDS.makeStack()).maxTrades(8).build());
        }

        if (TIConfig.Misc.peat.get()) {
            addCommonTrade(TradeBuilder.sell(2, ModBlocks.PEAT.makeStack(4)).maxTrades(3).build());
        }

        //Rares
        if (TIConfig.Food.taffy.get()) {
            addRareTrade(TradeBuilder.sell(2, ModItems.TAFFY.makeStack()).maxTrades(3).build());
        }

        if (TIConfig.Biomes.lushPlains.get()) {
            addRareTrade(TradeBuilder.sell(3, ModBlocks.FLOWERING_GRASS.makeStack(3)).maxTrades(6).build());
        }

        if (TIConfig.Flora.sweetPeas.get()) {
            var peas = ModBlocks.SWEET_PEAS.stream().map(BlockEntry::makeStack).toArray(ItemStack[]::new);
            addRareTrade(TradeBuilder.sellRandom(3, peas).maxTrades(5).build());
        }
    }
}
