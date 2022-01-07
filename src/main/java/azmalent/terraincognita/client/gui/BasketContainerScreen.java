package azmalent.terraincognita.client.gui;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.inventory.BasketContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class BasketContainerScreen extends AbstractContainerScreen<BasketContainer> {
    private static final ResourceLocation TEXTURE = TerraIncognita.prefix("textures/gui/basket.png");

    public BasketContainerScreen(BasketContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack matrixStack, int mouseX, int mouseY) {
        final float PLAYER_LABEL_XPOS = 8;
        final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);

        final float LABEL_Y = 6;

        Component basketLabel = title;
        float BAG_LABEL_XPOS = (imageWidth / 2.0F) - this.font.width(basketLabel.getString()) / 2.0F;
        this.font.draw(matrixStack, basketLabel, BAG_LABEL_XPOS, LABEL_Y, Color.darkGray.getRGB());

        float PLAYER_LABEL_YPOS = imageHeight - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.draw(matrixStack, this.inventory.getDisplayName(), PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());
    }

    @Override
    protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int edgeSpacingX = (this.width - this.imageWidth) / 2;
        int edgeSpacingY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
