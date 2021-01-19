package azmalent.terraincognita.client.event;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class ColorHandler {
    public static void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        if (TIConfig.Flora.smallLilypad.get()) {
            colors.register((state, reader, pos, color) -> reader != null && pos != null ? 2129968 : 7455580, ModBlocks.SMALL_LILYPAD.getBlock());
        }

        if (TIConfig.Flora.lotus.get()) {
            for (BlockEntry lotus : ModBlocks.LOTUSES) {
                colors.register((state, reader, pos, color) -> reader != null && pos != null ? 2129968 : 7455580, lotus.getBlock());
            }
        }

        if (TIConfig.Flora.reeds.get()) {
            colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : -1, ModBlocks.REEDS.getBlock(), ModBlocks.REEDS.getPotted());
        }
    }

    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();

        if (TIConfig.Flora.smallLilypad.get()) {
            colors.register((stack, index) -> {
                BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
                return blockColors.getColor(blockstate, null, null, index);
            }, ModBlocks.SMALL_LILYPAD.getItem());
        }

        if (TIConfig.Flora.flowerBand.get()) {
            colors.register((stack, index) -> index > 0 ? -1 : ModItems.FLOWER_BAND.get().getColor(stack), ModItems.FLOWER_BAND.get());
        }
    }
}
