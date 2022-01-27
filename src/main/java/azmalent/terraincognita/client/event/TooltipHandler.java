package azmalent.terraincognita.client.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class TooltipHandler {
	//Copied from Quark shulker_widget.png
    private static final ResourceLocation SLOT_WIDGET = TerraIncognita.prefix("textures/gui/slot_widget.png");

    public static void registerListeners() {
        MinecraftForge.EVENT_BUS.addListener(TooltipHandler::removeDyedWreathTooltip);
        //MinecraftForge.EVENT_BUS.addListener(TooltipHandler::renderBasketTooltip);
    }

    public static void removeDyedWreathTooltip(ItemTooltipEvent event) {
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

    //TODO: basket tooltip
    /*
	//Copied from Quark ShulkerBoxTooltips#renderTooltip() with edits
    public static void renderBasketTooltip(RenderTooltipEvent.PostText event) {
        if (event.getStack().getItem() != ModBlocks.BASKET.getItem()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        PoseStack matrix = event.getMatrixStack();
        Window window = mc.getWindow();

        BasketStackHandler stackHandler = BasketItem.getStackHandler(event.getStack());
        if (stackHandler.isEmpty()) {
            return;
        }

        int currentX = event.getX() - 5;
        int currentY = event.getY() - 70;

        int texWidth = 64;
        if (currentY < 0) {
            currentY = event.getY() + event.getLines().size() * 10 + 5;
        }

        int right = currentX + texWidth;
        if (right > window.getGuiScaledWidth()) {
            currentX -= (right - window.getGuiScaledWidth());
        }

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, 0, 700);

        renderTooltipBackground(matrix, currentX, currentY);

        ItemRenderer render = mc.getItemRenderer();
        for (int i = 0; i < BasketContainer.SIZE; i++) {
            ItemStack stack = stackHandler.getStackInSlot(i);
            int x = currentX + 6 + (i % BasketContainer.WIDTH) * 18;
            int y = currentY + 6 + (i / BasketContainer.WIDTH) * 18;

            if (!stack.isEmpty()) {
                render.renderAndDecorateItem(stack, x, y);
                render.renderGuiItemDecorations(mc.font, stack, x, y);
            }

            if (!ModIntegration.QUARK.matchesItemSearch(stack)) {
                RenderSystem.disableDepthTest();
                GuiComponent.fill(matrix, x, y, x + 16, y + 16, 0xAA000000);
            }
        }

        RenderSystem.popMatrix();
    }

	//Copied from Quark ShulkerBoxTooltips#renderTooltipBackground() with minor edits
    private static void renderTooltipBackground(PoseStack matrix, int x, int y) {
        Minecraft.getInstance().getTextureManager().bind(SLOT_WIDGET);

        final int CORNER = 5;
        final int BUFFER = 1;
        final int EDGE = 18;

        GuiComponent.blit(matrix, x, y, 0, 0, CORNER, CORNER, 256, 256);
        GuiComponent.blit(matrix, x + CORNER + EDGE * BasketContainer.WIDTH, y + CORNER + EDGE * BasketContainer.HEIGHT, CORNER + BUFFER + EDGE + BUFFER, CORNER + BUFFER + EDGE + BUFFER, CORNER, CORNER, 256, 256);
        GuiComponent.blit(matrix, x + CORNER + EDGE * BasketContainer.WIDTH, y, CORNER + BUFFER + EDGE + BUFFER, 0, CORNER, CORNER, 256, 256);
        GuiComponent.blit(matrix, x, y + CORNER + EDGE * BasketContainer.HEIGHT, 0, CORNER + BUFFER + EDGE + BUFFER, CORNER, CORNER, 256, 256);

        for (int row = 0; row < BasketContainer.HEIGHT; row++) {
            GuiComponent.blit(matrix, x, y + CORNER + EDGE * row, 0, CORNER + BUFFER, CORNER, EDGE, 256, 256);
            GuiComponent.blit(matrix, x + CORNER + EDGE * BasketContainer.HEIGHT, y + CORNER + EDGE * row, CORNER + BUFFER + EDGE + BUFFER, CORNER + BUFFER, CORNER, EDGE, 256, 256);

            for (int col = 0; col < BasketContainer.WIDTH; col++) {
                if (row == 0) {
                    GuiComponent.blit(matrix, x + CORNER + EDGE * col, y, CORNER + BUFFER, 0, EDGE, CORNER, 256, 256);
                    GuiComponent.blit(matrix, x + CORNER + EDGE * col, y + CORNER + EDGE * BasketContainer.HEIGHT, CORNER + BUFFER, CORNER + BUFFER + EDGE + BUFFER, EDGE, CORNER, 256, 256);
                }

                GuiComponent.blit(matrix, x + CORNER + EDGE * col, y + CORNER + EDGE * row, CORNER + BUFFER, CORNER + BUFFER, EDGE, EDGE, 256, 256);
            }
        }

        RenderSystem.color3f(1F, 1F, 1F);
    }*/
}
