package azmalent.terraincognita.client.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;

@SuppressWarnings("ConstantConditions")
@OnlyIn(Dist.CLIENT)
public class ColorHandler {
    public static final int LILYPAD_WORLD_COLOR = 2129968;
    public static final int LILYPAD_INVENTORY_COLOR = 2129968;
    public static final int APPLE_LEAVES_COLOR = 0x739D14;

    public static void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        if (TIConfig.Flora.smallLilypad.get()) {
            colors.register((state, reader, pos, color) ->
                reader != null && pos != null ? LILYPAD_WORLD_COLOR : LILYPAD_INVENTORY_COLOR,
                ModBlocks.SMALL_LILY_PAD.getBlock()
            );
        }

        if (TIConfig.Flora.lotus.get()) {
            colors.register((state, reader, pos, color) ->
                reader != null && pos != null ? LILYPAD_WORLD_COLOR : LILYPAD_INVENTORY_COLOR,
                ModBlocks.PINK_LOTUS.getBlock(), ModBlocks.WHITE_LOTUS.getBlock(), ModBlocks.YELLOW_LOTUS.getBlock()
            );
        }

        if (TIConfig.Flora.reeds.get()) {
            colors.register((state, reader, pos, color) ->
                reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : -1,
                ModBlocks.REEDS.getBlock(), ModBlocks.REEDS.getPotted()
            );
        }

        if (TIConfig.Trees.apple.get()) {
            colors.register((state, reader, pos, color) -> APPLE_LEAVES_COLOR,
                ModBlocks.WoodTypes.APPLE.LEAVES.getBlock(), ModBlocks.WoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock()
            );
        }

        ModIntegration.QUARK.registerBlockColorHandlers(event);
    }

    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();

        if (TIConfig.Flora.smallLilypad.get()) {
            colors.register((stack, index) -> {
                BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
                return blockColors.getColor(blockstate, null, null, index);
            }, ModBlocks.SMALL_LILY_PAD.getItem());
        }

        if (TIConfig.Trees.apple.get()) {
            colors.register((stack, index) -> index > 0 ? -1 : APPLE_LEAVES_COLOR,
                ModBlocks.WoodTypes.APPLE.LEAVES.getItem(), ModBlocks.WoodTypes.APPLE.BLOSSOMING_LEAVES.getItem()
            );
        }

        if (TIConfig.Flora.wreath.get()) {
            colors.register((stack, index) -> index > 0 ? -1 : ModItems.WREATH.get().getColor(stack), ModItems.WREATH.get());
        }

        ModIntegration.QUARK.registerItemColorHandlers(event);
    }
}
