package azmalent.terraincognita.client.event;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
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
    public static final int APPLE_LEAVES_COLOR = 0x73CD14;
    public static final int HAZEL_LEAVES_COLOR = 0x36A11C;
    public static final int MOSS_COLOR = 0x5AAD41;

    public static void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        colors.register((state, reader, pos, color) ->
            reader != null && pos != null ? LILYPAD_WORLD_COLOR : LILYPAD_INVENTORY_COLOR,
            ModBlocks.SMALL_LILY_PAD.getBlock()
        );

        colors.register((state, reader, pos, color) ->
            reader != null && pos != null ? LILYPAD_WORLD_COLOR : LILYPAD_INVENTORY_COLOR,
            ModBlocks.PINK_LOTUS.getBlock(), ModBlocks.WHITE_LOTUS.getBlock(), ModBlocks.YELLOW_LOTUS.getBlock()
        );

        colors.register((state, reader, pos, color) ->
            reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : -1,
            ModBlocks.REEDS.getBlock(), ModBlocks.REEDS.getPotted()
        );

        colors.register((state, reader, pos, color) -> MOSS_COLOR, ModBlocks.HANGING_MOSS.getBlock());

        colors.register((state, reader, pos, color) -> APPLE_LEAVES_COLOR,
            ModWoodTypes.APPLE.LEAVES.getBlock(), ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock()
        );

        colors.register((state, reader, pos, color) -> HAZEL_LEAVES_COLOR, ModWoodTypes.HAZEL.LEAVES.getBlock());
    }

    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();

        colors.register((stack, index) -> {
            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(blockstate, null, null, index);
        }, ModBlocks.SMALL_LILY_PAD);

        colors.register((stack, index) -> index > 0 ? -1 : APPLE_LEAVES_COLOR,
            ModWoodTypes.APPLE.LEAVES, ModWoodTypes.APPLE.BLOSSOMING_LEAVES
        );

        colors.register((stack, index) -> index > 0 ? -1 : HAZEL_LEAVES_COLOR, ModWoodTypes.HAZEL.LEAVES);

        colors.register((stack, index) -> index > 0 ? -1 : ModItems.WREATH.get().getColor(stack), ModItems.WREATH.get());
    }
}
