package azmalent.terraincognita.client;

import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import com.google.common.collect.Lists;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {
    public static final int LILYPAD_WORLD_COLOR = 2129968;
    public static final int LILYPAD_INVENTORY_COLOR = 2129968;
    public static final int MOSS_COLOR = 0x5AAD41;

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        ModWoodTypes.VALUES.forEach(type -> type.registerBlockColors(colors));

        var pottedSedge = ForgeRegistries.BLOCKS.getValue(TerraIncognita.prefix("potted_sedge"));
        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColor.get(0.5D, 1.0D),
            ModBlocks.FLOWERING_GRASS.get(), ModBlocks.SEDGE.get(), pottedSedge
        );

        colors.register((state, reader, pos, color) -> reader != null && pos != null ? LILYPAD_WORLD_COLOR : LILYPAD_INVENTORY_COLOR,
            ModBlocks.SMALL_LILY_PAD.get(), ModBlocks.PINK_LOTUS.get(), ModBlocks.WHITE_LOTUS.get(), ModBlocks.YELLOW_LOTUS.get()
        );

        colors.register((state, reader, pos, color) -> MOSS_COLOR, ModBlocks.HANGING_MOSS.get());
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();

        ModWoodTypes.VALUES.forEach(type -> type.registerItemColors(colors, blockColors));

        colors.register((stack, index) -> blockColors.getColor(ItemUtil.getBlockFromItem(stack).defaultBlockState(), null, null, index),
            ModBlocks.FLOWERING_GRASS, ModBlocks.SMALL_LILY_PAD
        );

        colors.register((stack, index) -> index > 0 ? -1 : ModItems.WREATH.get().getColor(stack), ModItems.WREATH.get());
    }
}