package azmalent.terraincognita.client.tooltip;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Function;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TooltipHandler {
	//Copied from Quark shulker_widget.png
    private static final ResourceLocation SLOT_WIDGET = TerraIncognita.prefix("textures/gui/slot_widget.png");

    //Remove "dyed" tooltip from wreaths
    @SubscribeEvent
    public static void onWreathTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() != ModItems.WREATH.get() || event.getFlags().isAdvanced()) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        for (Component line : tooltip) {
            if (line instanceof TranslatableComponent) {
                String key = ((TranslatableComponent) line).getKey();
                if (key.equals("item.dyed")) {
                    tooltip.remove(line);
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBasketTooltip(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() == ModBlocks.BASKET.asItem()) {
            var stackHandler = BasketItem.getStackHandler(stack);
            if (stackHandler != null && !stackHandler.isEmpty()) {
                var component = new BasketTooltipComponent(stack);
                event.getTooltipElements().add(1, Either.right(component));
            }
        }
    }

	//Copied from Quark ShulkerComponent#renderTooltipBackground() with minor edits
    public static void renderContainerSlots(PoseStack poseStack, int x, int y, int width, int height) {
        final int CORNER = 5;
        final int BUFFER = 1;
        final int EDGE = 18;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SLOT_WIDGET);

        GuiComponent.blit(poseStack, x, y, 0, 0, CORNER, CORNER, 256, 256);
        GuiComponent.blit(poseStack, x + CORNER + EDGE * width, y + CORNER + EDGE * height, CORNER + BUFFER + EDGE + BUFFER, CORNER + BUFFER + EDGE + BUFFER, CORNER, CORNER, 256, 256);
        GuiComponent.blit(poseStack, x + CORNER + EDGE * width, y, CORNER + BUFFER + EDGE + BUFFER, 0, CORNER, CORNER, 256, 256);
        GuiComponent.blit(poseStack, x, y + CORNER + EDGE * height, 0, CORNER + BUFFER + EDGE + BUFFER, CORNER, CORNER, 256, 256);

        for (int row = 0; row < height; row++) {
            GuiComponent.blit(poseStack, x, y + CORNER + EDGE * row, 0, CORNER + BUFFER, CORNER, EDGE, 256, 256);
            GuiComponent.blit(poseStack, x + CORNER + EDGE * height, y + CORNER + EDGE * row, CORNER + BUFFER + EDGE + BUFFER, CORNER + BUFFER, CORNER, EDGE, 256, 256);

            for (int col = 0; col < width; col++) {
                if (row == 0) {
                    GuiComponent.blit(poseStack, x + CORNER + EDGE * col, y, CORNER + BUFFER, 0, EDGE, CORNER, 256, 256);
                    GuiComponent.blit(poseStack, x + CORNER + EDGE * col, y + CORNER + EDGE * height, CORNER + BUFFER, CORNER + BUFFER + EDGE + BUFFER, EDGE, CORNER, 256, 256);
                }

                GuiComponent.blit(poseStack, x + CORNER + EDGE * col, y + CORNER + EDGE * row, CORNER + BUFFER, CORNER + BUFFER, EDGE, EDGE, 256, 256);
            }
        }
    }

    public static void registerComponentFactories() {
        MinecraftForgeClient.registerTooltipComponentFactory(BasketTooltipComponent.class, Function.identity());
    }
}
