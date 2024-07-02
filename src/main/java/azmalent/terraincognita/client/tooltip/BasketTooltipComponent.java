package azmalent.terraincognita.client.tooltip;

import azmalent.terraincognita.common.menu.BasketMenu;
import azmalent.terraincognita.common.menu.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.integration.ModIntegration;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public record BasketTooltipComponent(ItemStack basket) implements TooltipComponent, ClientTooltipComponent {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void renderImage(@Nonnull Font font, int tooltipX, int tooltipY, @Nonnull PoseStack pose, @Nonnull ItemRenderer itemRenderer, int blitOffset) {
        int currentX = tooltipX;
        int currentY = tooltipY - 1;

        final int CORNER = 5;
        final int EDGE = 18;

        int texWidth = CORNER * 2 + EDGE * BasketMenu.WIDTH;
        int right = currentX + texWidth;

        Minecraft mc = Minecraft.getInstance();

        Window window = mc.getWindow();
        if (right > window.getGuiScaledWidth()) {
            currentX -= (right - window.getGuiScaledWidth());
        }

        pose.pushPose();
        pose.translate(0, 0, 700);

        TooltipHandler.renderContainerSlots(pose, currentX, currentY, BasketMenu.WIDTH, BasketMenu.HEIGHT);

        ItemRenderer renderer = mc.getItemRenderer();
        BasketStackHandler stackHandler = BasketItem.getStackHandler(basket);
        for (int i = 0; i < 9; i++) {
            ItemStack stack = stackHandler.getStackInSlot(i);
            int xp = currentX + 6 + (i % BasketMenu.WIDTH) * 18;
            int yp = currentY + 6 + (i / BasketMenu.WIDTH) * 18;

            if (!stack.isEmpty()) {
                renderer.renderAndDecorateItem(stack, xp, yp);
                renderer.renderGuiItemDecorations(mc.font, stack, xp, yp);
            }

            //Dark overlay if item doesn't match search
            if (!ModIntegration.QUARK.namesMatch(stack)) {
                RenderSystem.disableDepthTest();
                GuiComponent.fill(pose, xp, yp, xp + 16, yp + 16, 0xAA000000);
            }
        }

        pose.popPose();
    }

    @Override
    public int getHeight() {
        return 65;
    }

    @Override
    public int getWidth(@NotNull Font pFont) {
        return 63;
    }
}
