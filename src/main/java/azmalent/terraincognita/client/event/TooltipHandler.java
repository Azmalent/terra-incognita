package azmalent.terraincognita.client.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.integration.ModIntegration;
import azmalent.terraincognita.common.inventory.BasketContainer;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TooltipHandler {
	//Copied from Quark shulker_widget.png
    private static final ResourceLocation SLOT_WIDGET = TerraIncognita.prefix("textures/gui/slot_widget.png");

    public static void registerListeners() {
        if (TIConfig.Flora.wreath.get()) {
            MinecraftForge.EVENT_BUS.addListener(TooltipHandler::removeDyedWreathTooltip);
        }

        if (TIConfig.Tools.basket.get()) {
            MinecraftForge.EVENT_BUS.addListener(TooltipHandler::renderBasketTooltip);
        }
    }

    public static void removeDyedWreathTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() != ModItems.WREATH.get() || event.getFlags().isAdvanced()) {
            return;
        }

        List<ITextComponent> tooltip = event.getToolTip();
        for (ITextComponent line : tooltip) {
            if (line instanceof TranslationTextComponent) {
                String key = ((TranslationTextComponent) line).getKey();
                if (key.equals("item.dyed")) {
                    tooltip.remove(line);
                    return;
                }
            }
        }
    }

	//Copied from Quark ShulkerBoxTooltips#renderTooltip() with edits
    public static void renderBasketTooltip(RenderTooltipEvent.PostText event) {
        assert ModBlocks.BASKET != null;
        if (event.getStack().getItem() != ModBlocks.BASKET.getItem()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        MatrixStack matrix = event.getMatrixStack();
        MainWindow window = mc.getMainWindow();

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
        if (right > window.getScaledWidth()) {
            currentX -= (right - window.getScaledWidth());
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
                render.renderItemAndEffectIntoGUI(stack, x, y);
                render.renderItemOverlays(mc.fontRenderer, stack, x, y);
            }

            if (!ModIntegration.QUARK.matchesItemSearch(stack)) {
                RenderSystem.disableDepthTest();
                AbstractGui.fill(matrix, x, y, x + 16, y + 16, 0xAA000000);
            }
        }

        RenderSystem.popMatrix();
    }

	//Copied from Quark ShulkerBoxTooltips#renderTooltipBackground() with minor edits
    private static void renderTooltipBackground(MatrixStack matrix, int x, int y) {
        Minecraft.getInstance().getTextureManager().bindTexture(SLOT_WIDGET);

        final int CORNER = 5;
        final int BUFFER = 1;
        final int EDGE = 18;

        AbstractGui.blit(matrix, x, y, 0, 0, CORNER, CORNER, 256, 256);
        AbstractGui.blit(matrix, x + CORNER + EDGE * BasketContainer.WIDTH, y + CORNER + EDGE * BasketContainer.HEIGHT, CORNER + BUFFER + EDGE + BUFFER, CORNER + BUFFER + EDGE + BUFFER, CORNER, CORNER, 256, 256);
        AbstractGui.blit(matrix, x + CORNER + EDGE * BasketContainer.WIDTH, y, CORNER + BUFFER + EDGE + BUFFER, 0, CORNER, CORNER, 256, 256);
        AbstractGui.blit(matrix, x, y + CORNER + EDGE * BasketContainer.HEIGHT, 0, CORNER + BUFFER + EDGE + BUFFER, CORNER, CORNER, 256, 256);

        for (int row = 0; row < BasketContainer.HEIGHT; row++) {
            AbstractGui.blit(matrix, x, y + CORNER + EDGE * row, 0, CORNER + BUFFER, CORNER, EDGE, 256, 256);
            AbstractGui.blit(matrix, x + CORNER + EDGE * BasketContainer.HEIGHT, y + CORNER + EDGE * row, CORNER + BUFFER + EDGE + BUFFER, CORNER + BUFFER, CORNER, EDGE, 256, 256);

            for (int col = 0; col < BasketContainer.WIDTH; col++) {
                if (row == 0) {
                    AbstractGui.blit(matrix, x + CORNER + EDGE * col, y, CORNER + BUFFER, 0, EDGE, CORNER, 256, 256);
                    AbstractGui.blit(matrix, x + CORNER + EDGE * col, y + CORNER + EDGE * BasketContainer.HEIGHT, CORNER + BUFFER, CORNER + BUFFER + EDGE + BUFFER, EDGE, CORNER, 256, 256);
                }

                AbstractGui.blit(matrix, x + CORNER + EDGE * col, y + CORNER + EDGE * row, CORNER + BUFFER, CORNER + BUFFER, EDGE, EDGE, 256, 256);
            }
        }

        RenderSystem.color3f(1F, 1F, 1F);
    }
}
