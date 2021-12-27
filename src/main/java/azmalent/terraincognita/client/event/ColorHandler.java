package azmalent.terraincognita.client.event;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;

@OnlyIn(Dist.CLIENT)
public class ColorHandler {
    public static final int LILYPAD_WORLD_COLOR = 2129968;
    public static final int LILYPAD_INVENTORY_COLOR = 2129968;
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

        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.get(0.5D, 1.0D),
            ModWoodTypes.APPLE.LEAVES.getBlock(), ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock(), ModWoodTypes.HAZEL.LEAVES.getBlock()
        );

        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D),
            ModBlocks.FLOWERING_GRASS.getBlock()
        );
    }

    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();

        registerDefaultItemColors(colors, blockColors,
            ModBlocks.FLOWERING_GRASS, ModBlocks.SMALL_LILY_PAD, ModWoodTypes.APPLE.LEAVES, ModWoodTypes.APPLE.BLOSSOMING_LEAVES,
            ModWoodTypes.HAZEL.LEAVES
        );

        colors.register((stack, index) -> index > 0 ? -1 : ModItems.WREATH.get().getColor(stack), ModItems.WREATH.get());
    }

    public static void registerDefaultItemColors(ItemColors colors, BlockColors blockColors, IItemProvider... items) {
        colors.register((stack, index) -> {
            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(blockstate, null, null, index);
        }, items );
    }
}
